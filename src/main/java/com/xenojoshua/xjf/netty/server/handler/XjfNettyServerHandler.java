package com.xenojoshua.xjf.netty.server.handler;

import com.xenojoshua.xjf.log.XjfLogger;
import com.xenojoshua.xjf.netty.protobuf.protos.Communication;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jboss.netty.channel.*;

public class XjfNettyServerHandler extends SimpleChannelUpstreamHandler {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        Communication.XjfMessages messages = (Communication.XjfMessages) e.getMessage();

        Communication.Player player = messages.getI1001().getPlayer();
        XjfLogger.get().debug(
            String.format(
                "Player: id: %d, name: %s, password: %s",
                    player.getId(), player.getName(), player.getPassword()
            )
        );

        XjfLogger.get().debug("[xjf-netty-server] Message received!");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        XjfLogger.get().error("[xjf-netty-server] Unexpected exception: ");
        XjfLogger.get().error(ExceptionUtils.getStackTrace(e.getCause()));
        e.getChannel().close();
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        XjfLogger.get().debug("[xjf-netty-server] Channel disconnected!");
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        XjfLogger.get().debug("[xjf-netty-server] Channel connected!");
    }
}
