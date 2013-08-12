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
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;

public class XjfNettyServerImpl extends XjfNettyServer {

    private static ExecutionHandler executionHandler = new ExecutionHandler(
        new OrderedMemoryAwareThreadPoolExecutor(16, 1048576, 1048576)
    );

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

                pipeline.addLast("executor", executionHandler);
                pipeline.addLast("handler", new XjfNettyServerHandler());

                return pipeline;
            }
        };
    }

}
