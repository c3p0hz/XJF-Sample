import com.xenojoshua.xjf.netty.client.XjfNettyClient;
import com.xenojoshua.xjf.netty.client.XjfNettyClientImpl;
import com.xenojoshua.xjf.netty.protobuf.ProtoRunner;
import com.xenojoshua.xjf.netty.protobuf.protos.Communication;
import com.xenojoshua.xjf.netty.server.XjfNettyServerImpl;
import com.xenojoshua.xjf.netty.template.TplRunner;
import com.xenojoshua.xjf.system.XjfSystem;
import com.xenojoshua.xjf.util.XjfUtil;
import com.xenojoshua.xjf.util.XjfValidator;

public class Runner {

    public static void main(String[] args) throws Exception {

        //-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
        // START FIELD FOR TEST
        //-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

//        ProtoRunner.run();
//        TplRunner.run();

        //-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
        // END FIELD FOR TEST
        //-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

        if (args.length != 4) {
            Runner.printUsage();return;
        }

        // initialize params
        boolean isIDE = false;
        String  mode = "server";
        String  host = "127.0.0.1";
        int     port = 10000;

        // isIDE: validation args[0]
        if (!XjfValidator.isNumeric(args[0])) {
            Runner.printUsage();return;
        }
        int ideParam = Integer.parseInt(args[0]);
        if (ideParam != 0 && ideParam != 1) {
            Runner.printUsage();return;
        } else if (ideParam == 1) {
            isIDE = true;
        }
        // mode: validation args[1]
        if (!args[1].equals("client") && !args[1].equals("server")) {
            Runner.printUsage();return;
        } else {
            mode = args[1];
        }
        // host: validation args[2]
        if (!XjfValidator.isIP(args[2])) {
            Runner.printUsage();return;
        } else {
            host = args[2];
        }
        // port: validation args[3]
        if (!XjfValidator.isNumeric(args[3])) {
            Runner.printUsage();return;
        }
        int portParam = Integer.parseInt(args[3]);
        if (!XjfValidator.isPort(portParam)) {
            Runner.printUsage();return;
        } else {
            port = portParam;
        }

        System.out.println(
            String.format(
                "[xjf-netty] start with: %s %s %s %s ...",
                    args[0], args[1], args[2], args[3]
            )
        );

        String jarFilePath = Runner.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        jarFilePath = jarFilePath.substring(0, jarFilePath.lastIndexOf("/") + 1);

        if (isIDE && jarFilePath.contains("classes")) { // means run in IDE("IntelliJ IDEA"), remove the tailing "classes"
            jarFilePath = jarFilePath.substring(0, jarFilePath.lastIndexOf("classes"));
        }

        XjfSystem.init(jarFilePath);

        if (mode.equals("server")) {

            new XjfNettyServerImpl(host, port).run();

        } else if (mode.equals("client")) {

            XjfNettyClient client = new XjfNettyClientImpl(host, port);

            // build message
            Communication.Player.Builder playerBuilder = Communication.Player.newBuilder();
            playerBuilder.setId(292514701).setName("jonathan").setPassword(XjfUtil.md5("mypassword"));
            Communication.Player jonathan = playerBuilder.build();

            Communication.I1001.Builder I1001Builder = Communication.I1001.newBuilder();
            I1001Builder.setPlayer(jonathan);
            Communication.I1001 i1001 = I1001Builder.build();

            Communication.XjfMessages.Builder messagesBuilder = Communication.XjfMessages.newBuilder();
            messagesBuilder.setI1001(i1001);
            Communication.XjfMessages message = messagesBuilder.build();

            client.send(message);

            client.run();

        }
    }

    /**
     * Print the usage information.
     */
    private static void printUsage() {
        System.err.println(
            String.format(
                "Usage: %s <ide:1|0> <mode:server|client> <host> <port>",
                Runner.class.getSimpleName()
            )
        );
    }
}
