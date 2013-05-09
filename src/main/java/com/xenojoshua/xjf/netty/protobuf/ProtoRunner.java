package com.xenojoshua.xjf.netty.protobuf;

import com.xenojoshua.xjf.netty.protobuf.protos.IntProto;
import com.xenojoshua.xjf.netty.protobuf.protos.PlayerProto;
import com.xenojoshua.xjf.util.XjfUtil;

public class ProtoRunner {

    public static void run() {

        // PLAYER
        PlayerProto.Player.Builder builder = PlayerProto.Player.newBuilder();

        builder.setId(292514701).setName("jonathan").setPassword(XjfUtil.md5("mypassword"));
        PlayerProto.Player jonathan = builder.build();
        byte[] jonathanBytes = jonathan.toByteArray();

        builder.clear();
        builder.setId(2817).setName("david").setPassword("123");
        PlayerProto.Player david = builder.build();
        byte[] davidBytes = david.toByteArray();

        // INT
        IntProto.Int.Builder builderInt = IntProto.Int.newBuilder();
        builderInt.setId(123456789);
        IntProto.Int intInstance = builderInt.build();
        byte[] intBytes = intInstance.toByteArray();

        System.out.println("");

    }

}
