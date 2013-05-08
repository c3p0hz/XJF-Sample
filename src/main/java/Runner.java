import com.xenojoshua.xjf.netty.client.XjfNettyClient;
import com.xenojoshua.xjf.system.XjfSystem;

public class Runner {

    public static void main(String[] args) throws Exception {
        // Print usage if no argument is specified.
        if (args.length < 2 || args.length > 3) {
            System.err.println(
                "Usage: " + Runner.class.getSimpleName() + " <host> <port>"
            );
            return;
        }

        System.out.println("[xjf-netty-client] start...");

        String jarFilePath = Runner.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        jarFilePath = jarFilePath.substring(0, jarFilePath.lastIndexOf("/") + 1);

        if (args.length > 0 && args[0].equals("ide")
                && jarFilePath.contains("classes")) { // means run in IDE("IntelliJ IDEA"), remove the tailing "classes"
            jarFilePath = jarFilePath.substring(0, jarFilePath.lastIndexOf("classes"));
        }

        XjfSystem.init(jarFilePath);

        final String host = args[1];
        final int port = Integer.parseInt(args[2]);

        XjfNettyClient client = new XjfNettyClient(host, port);

        // push message before client start
        client.send("CLIENT_MSG_TST_001");
        client.send("CLIENT_MSG_TST_002");
        client.send("CLIENT_MSG_TST_003");
        client.send("CLIENT_MSG_TST_004");

        client.run();
    }
}
