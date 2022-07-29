package com.wones.client.handler;

import com.wones.common.protocol.*;
import com.wones.config.Config;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

//同样的与服务器解码和编码格式
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,8,4,0,0));
//        pipeline.addLast(new LoggingHandler());
        pipeline.addLast(new MessageDecode());
        pipeline.addLast(new MessageEncode(Serializer.getSerializerByCode(Config.getSerializerAlgorithm())));
        pipeline.addLast(new NettyClientHandler());
    }
}
