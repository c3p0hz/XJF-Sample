import com.xenojoshua.xjf.netty.server.XjfNettyServer;
import com.xenojoshua.xjf.system.XjfSystem;

public class Runner1 {
    public static void main(String args[]) {
        System.out.println("[xjf-netty-server] start...");

        String jarFilePath = Runner.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        jarFilePath = jarFilePath.substring(0, jarFilePath.lastIndexOf("/") + 1);

        if (args.length > 0 && args[0].equals("ide")
                && jarFilePath.contains("classes")) { // means run in IDE("IntelliJ IDEA"), remove the tailing "classes"
            jarFilePath = jarFilePath.substring(0, jarFilePath.lastIndexOf("classes"));
        }

        XjfSystem.init(jarFilePath);

        new XjfNettyServer().run();
    }
}