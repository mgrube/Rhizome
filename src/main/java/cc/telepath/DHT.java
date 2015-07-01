package cc.telepath;

import java.net.Inet4Address;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.net.InetAddress;
import java.io.IOException;

import net.tomp2p.connection.Bindings;
import net.tomp2p.futures.FutureDHT;
import net.tomp2p.futures.FutureBootstrap;
import net.tomp2p.futures.FutureDiscover;
import net.tomp2p.p2p.Peer;
import net.tomp2p.p2p.PeerMaker;
import net.tomp2p.peers.Number160;
import net.tomp2p.storage.Data;
import net.tomp2p.futures.BaseFutureImpl;

/**
 *  Code modified from TomP2p Introduction.
 *  @author Mike
 */
public class DHT {


    final private Peer peer;


    public DHT(int peerId) throws Exception {
        peer = new PeerMaker(Number160.createHash(peerId)).setPorts(4000 + peerId).makeAndListen();
        FutureBootstrap fb = peer.bootstrap().setBroadcast().setPorts(4001).start();
        fb.awaitUninterruptibly();
        if (fb.getBootstrapTo() != null) {
            peer.discover().setPeerAddress(fb.getBootstrapTo().iterator().next()).start().awaitUninterruptibly();
        }
    }

    /**
     * Spawn a peer.
     * Pass a bootstrap IP and port
     * @return a new peer
     */
    public Peer spawnPeer(String bootstrapnode, int port,  Boolean isBootstrapNode){
        Peer peer = null;
        try {
            KeyPairGenerator gen = KeyPairGenerator.getInstance("DSA");
            KeyPair pair1 = gen.generateKeyPair();
            //Peer peer = new PeerMaker(new Number160(rnd)).setPorts(4001).makeAndListen();
            Random rnd = new Random();
            Bindings b = new Bindings();
            peer = new PeerMaker(pair1).setPorts(port).setBindings(b).makeAndListen();
            peer.getConfiguration().setBehindFirewall(true);
            if(isBootstrapNode) {
                return peer;
            }
            else{
                InetAddress address = Inet4Address.getByName(bootstrapnode);
                FutureDiscover futureDiscover = peer.discover().setInetAddress( address ).setPorts( port ).start();
                futureDiscover.awaitUninterruptibly();
                FutureBootstrap futureBootstrap = peer.bootstrap().setInetAddress( address ).setPorts( port ).start();
                futureBootstrap.awaitUninterruptibly();
                return peer;

            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return peer;
    }


    public static void main(String[] args) throws NumberFormatException, Exception {
        DHT d = new DHT(1234);
        System.out.println(args[0]);
        System.out.println(args[1]);
        //Peer p = d.spawnPeer("", 4000, true);
        Peer a = d.spawnPeer(args[0], Integer.parseInt(args[1]), false);
        System.out.println(a);
        System.out.println("Successful?");
        //p.shutdown();
        a.shutdown();
        System.exit(0);
        //p.shutdown();
    }

    private String get(String name) throws ClassNotFoundException, IOException {
        FutureDHT futureDHT = peer.get(Number160.createHash(name)).start();
        futureDHT.awaitUninterruptibly();
        if (futureDHT.isSuccess()) {
            return futureDHT.getData().getObject().toString();
        }
        return "not found";
    }

    private void store(String name, String ip) throws IOException {
        peer.put(Number160.createHash(name)).setData(new Data(ip)).start().awaitUninterruptibly();
    }
}
