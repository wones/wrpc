package com.wones.client;

import com.wones.client.handler.NettyClientInitializer;
import com.wones.common.RPCRequest;
import com.wones.common.RPCResponse;
import com.wones.register.ServiceRegister;
import com.wones.register.ZkServiceRegister;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class NettyRPCClient implements RPCClient{
    private static final Bootstrap bootstrap;
    private static final EventLoopGroup group;
    private String host;
    private int port;
    private ServiceRegister serviceRegister ;
    public NettyRPCClient(String host,int port){
        this.host = host;
        this.port = port;
    }
    public NettyRPCClient(){
        serviceRegister = new ZkServiceRegister();
    }
    //netty客户端初始化，重复使用
    static {
        bootstrap = new Bootstrap();
        group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());
    }
    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        InetSocketAddress address = serviceRegister.serviceDiscovery(request.getInterfaceName());
        try{
            ChannelFuture channelFuture = bootstrap.connect(address).sync();
            Channel channel = channelFuture.channel();
            channel.writeAndFlush(request);
            channel.closeFuture().sync();

            AttributeKey<RPCResponse> key = AttributeKey.valueOf("RPCResponse");
            RPCResponse response = channel.attr(key).get();
            return response;

        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }
}
