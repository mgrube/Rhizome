package cc.telepath;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.net.InetAddress;
import java.io.IOException;
import net.tomp2p.connection.Bindings;
import net.tomp2p.futures.FutureDiscover;
import net.tomp2p.p2p.Peer;
import net.tomp2p.p2p.PeerMaker;
import net.tomp2p.peers.Number160;
import net.tomp2p.peers.PeerAddress;


/**
 *  Code modified from TomP2p Introduction.
 *  @author Mike
 */
public class DHT {

    // Bootstrap from p to the IPAddress and port specified
    public int peerBootstrap(String ipaddress, int Port, Peer p){
        try {
            p.getConfiguration().setBehindFirewall(true);
            PeerAddress pa = new PeerAddress(Number160.ZERO, InetAddress.getByName(ipaddress), Port, Port);
            FutureDiscover fd = p.discover().setPeerAddress(pa).start();
            fd.awaitUninterruptibly();
            if(fd.isSuccess()){
                System.out.println("My outside address is " + fd.getPeerAddress());
            }
            else{
                System.out.println("failed " + fd.getFailedReason());
            }
            p.bootstrap().start();

            return 0;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Spawn a peer.
     * Pass the port on which to start the peer.
     * @return a new peer
     */
    public Peer spawnPeer(int port){
        Peer p = null;
        try {
            KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
            KeyPair pair1 = gen.generateKeyPair();
            //Peer peer = new PeerMaker(new Number160(rnd)).setPorts(4001).makeAndListen();
            Random rnd = new Random();
            Bindings b = new Bindings();
            p = new PeerMaker(pair1).setPorts(port).setBindings(b).makeAndListen();
            p.getConfiguration().setBehindFirewall(true);

        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return p;
    }


    public static void main(String[] args) throws NumberFormatException, Exception {
        DHT d = new DHT();

        if (args.length > 0){
            if(args[0].equals("1")){
                Peer a = d.spawnPeer(Integer.parseInt(args[3]));
                d.peerBootstrap(args[1], Integer.parseInt(args[2]), a);
                a.shutdown();
            }
            if(args[0].equals("2")){
                Peer a = d.spawnPeer(Integer.parseInt(args[1]));
                Thread.sleep(10000);
                a.shutdown();
            }
        }
        System.exit(0);
    }

}
