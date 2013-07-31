package com.xenojoshua.xjf.netty.server;

import com.xenojoshua.xjf.netty.protobuf.protos.Communication;
import com.xenojoshua.xjf.netty.server.handler.XjfNettyServerHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.protobuf.ProtobufDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufEncoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class XjfNettyServerImpl extends XjfNettyServer {

    public XjfNettyServerImpl(String host, int port) {
        super(host, port);
    }

    public XjfNettyServerImpl(String host, int port, int maxMsgSize) {
        super(host, port, maxMsgSize);
    }

    @Override
    ChannelPipelineFactory buildPiplineFactory() {
        return new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();

                pipeline.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
                pipeline.addLast("protobufDecoder", new ProtobufDecoder(Communication.XjfMessage.getDefaultInstance()));

                pipeline.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
                pipeline.addLast("protobufEncoder", new ProtobufEncoder());

                pipeline.addLast("handler", new XjfNettyServerHandler());

                return pipeline;
            }
        };
    }

}
