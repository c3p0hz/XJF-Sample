package com.xenojoshua.xjf.netty.protobuf;

import com.xenojoshua.xjf.netty.protobuf.protos.Communication;
import com.xenojoshua.xjf.util.XjfUtil;

public class ProtoRunner {

    public static void run() {

        // PLAYER
        Communication.Player.Builder builder = Communication.Player.newBuilder();

        builder.setId(292514701).setName("jonathan").setPassword(XjfUtil.md5("mypassword"));
        Communication.Player jonathan = builder.build();
        byte[] jonathanBytes = jonathan.toByteArray();

        builder.clear();

        builder.setId(2817).setName("david").setPassword("123");
        Communication.Player david = builder.build();
        byte[] davidBytes = david.toByteArray();

        System.out.println(jonathanBytes.length);
        System.out.println(davidBytes.length);

    }

}
