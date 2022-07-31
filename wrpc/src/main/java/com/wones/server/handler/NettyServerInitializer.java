package com.wones.server.handler;

import com.wones.common.protocol.*;
import com.wones.config.Config;
import com.wones.server.ServiceProvider;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    private ServiceProvider serviceProvider;
    //定义序列化方式
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //消息格式 【长度】【消息体】,解决粘包问题
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,8,4,0,0));
        pipeline.addLast(new LoggingHandler());
        pipeline.addLast(new MessageDecode());
        pipeline.addLast(new MessageEncode(Serializer.getSerializerByCode(Config.getSerializerAlgorithm())));
        pipeline.addLast(new NettyRPCServerHandler(serviceProvider));
    }
}
