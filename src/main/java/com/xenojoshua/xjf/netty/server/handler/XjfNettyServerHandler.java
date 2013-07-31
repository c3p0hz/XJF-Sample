package com.xenojoshua.xjf.netty.server.handler;

import com.xenojoshua.xjf.log.XjfLogger;
import com.xenojoshua.xjf.netty.protobuf.protos.Communication;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jboss.netty.channel.*;

import java.util.Iterator;
import java.util.List;

public class XjfNettyServerHandler extends SimpleChannelUpstreamHandler {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        Communication.XjfMessage messages = (Communication.XjfMessage) e.getMessage();

        // handle message type
        switch (messages.getType()) {
            case T1001:
                Communication.Player player = messages.getI1001().getPlayer();
                XjfLogger.get().debug(String.format("Player: id: %d, name: %s, password: %s", player.getId(), player.getName(), player.getPassword()));
                break;
            case T1002:
                Communication.PlayerGroup group = messages.getI1002().getGroup();
                XjfLogger.get().debug(String.format("Group: id: %d, name: %s", group.getId(), group.getName()));

                List<Communication.Player> playerList = group.getPlayersList();
                int listSize = playerList.size();
                XjfLogger.get().debug(String.format("Group member count: %d", listSize));
                if (listSize > 0) {
                    XjfLogger.get().debug("Group Players: ");
                    Iterator<Communication.Player> iterator = playerList.iterator();
                    while (iterator.hasNext()) {
                        Communication.Player groupPlayer = iterator.next();
                        XjfLogger.get().debug(String.format("Player: id: %d, name: %s, password: %s", groupPlayer.getId(), groupPlayer.getName(), groupPlayer.getPassword()));
                    }
                }
                break;
            default:
                break;
        }

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
