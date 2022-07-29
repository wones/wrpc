package com.wones.common.protocol;

import com.wones.common.RPCRequest;
import com.wones.common.RPCResponse;
import com.wones.config.Config;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class MessageEncode extends MessageToByteEncoder {
    //序列化：将对象编码成字节数组
    private Serializer serializer;
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        byteBuf.writeBytes(new byte[]{'w','r','p','c'});    //魔数
//        log.debug("魔数：{}",new byte[]{'w','r','p','c'});
        byteBuf.writeByte(1);   //版本号
        byteBuf.writeByte(serializer.getType());   //序列化方式
//        log.debug("序列化方式:{}",serializer.getType());
        //消息类型
        if (o instanceof RPCRequest) {
            byteBuf.writeShort(Config.getMessageTypeRequest());
        }else if (o instanceof RPCResponse){
            byteBuf.writeShort(Config.getMessageTypeResponse());
        }
//        log.debug("编码消息类型{}",o.getClass());
//        log.debug("写入消息类型:{}",Config.getMessageTypeRequest());
        //获得序列化数组
        byte[] serialize = serializer.serialize(o);
        byteBuf.writeInt(serialize.length); //消息长度
//        log.debug("消息长度：{}",serialize.length);
        byteBuf.writeBytes(serialize);  //消息正文
    }
}
