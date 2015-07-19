package cc.telepath;



/**
 * Created by michael on 7/19/15.
 */
public class SystemProfiler {
    private String OSName;
    private String OSVersion;
    private String GPU;


    private int numcores;

    public SystemProfiler(){
        this.OSName = System.getProperty("os.name");
        this.OSVersion = System.getProperty("os.version");
        this.numcores = Runtime.getRuntime().availableProcessors();
    }

    public String getOSName(){
        return OSName;
    }

    public String getOSVersion(){
        return OSVersion;
    }

    public int getNumcores(){
        return numcores;
    }

    public static void main(String[] args){
        SystemProfiler sp = new SystemProfiler();
        System.out.println(sp.getNumcores());
        System.out.println(sp.getOSName());
        System.out.println(sp.getOSVersion());
    }

}
