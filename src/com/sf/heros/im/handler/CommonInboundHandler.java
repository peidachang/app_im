package com.sf.heros.im.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import com.sf.heros.im.channel.TcpSocketChannel;
import com.sf.heros.im.channel.UdtSocketChannel;
import com.sf.heros.im.common.ImUtils;
import com.sf.heros.im.common.bean.msg.RespMsg;

public class CommonInboundHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(CommonInboundHandler.class);

    protected String getSessionId(ChannelHandlerContext ctx) {
        return getSessionId(ctx.channel());
    }

    protected String getSessionId(Channel channel) {

        if (channel instanceof TcpSocketChannel) {
            return ((TcpSocketChannel)channel).getId();
        }

        if (channel instanceof UdtSocketChannel) {
            return ((UdtSocketChannel)channel).getId();
        }

        return null;

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().flush();
        ctx.flush();
    }

    public void releaseObjs(Object... objs) {
        if (objs != null) {
            for (Object obj : objs) {
                if (obj != null) {
                    ReferenceCountUtil.release(obj);
                    obj = null;
                }

            }

        }
    }

    public void writeAndFlush(Channel channel, RespMsg respMsg) {
        try {
            channel.writeAndFlush(ImUtils.getBuf(channel.alloc(), respMsg));
            logger.info("write resp msg " + respMsg);
        } catch (UnsupportedEncodingException e) {
        }
    }

    public void writeAndFlush(ChannelHandlerContext ctx, RespMsg respMsg) {
        try {
            ctx.writeAndFlush(ImUtils.getBuf(ctx.alloc(), respMsg));
            logger.info("write resp msg " + respMsg);
        } catch (UnsupportedEncodingException e) {
        }
    }

}
