package com.wones.common.protocol;

import com.wones.config.Config;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@AllArgsConstructor
@Slf4j
public class MessageDecode extends ByteToMessageDecoder {
    //反序列，将字节数组解码成对象
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int magicNum = byteBuf.readInt();   //魔数
//        log.debug("魔数：{}",magicNum);
        byte version = byteBuf.readByte();  //版本号
//        log.debug("版本号：{}",version);
        byte serializerType = byteBuf.readByte();   //序列化方式
//        log.debug("序列化方式：{}",serializerType);
        short messageType = byteBuf.readShort();    //消息类型
//        log.debug("消息类型：{}",messageType);
        int length = byteBuf.readInt(); //消息长度
//        log.debug("消息长度：{}",length);

//        log.debug("Config.getMessageTypeRequest:{}",Config.getMessageTypeRequest());
        if(messageType != Config.getMessageTypeRequest() &&
        messageType != Config.getMessageTypeResponse()){
            System.out.println("暂不支持此种数据");
            return;
        }

        //根据序列化类型得到相应的序列化器
        Serializer serializer = Serializer.getSerializerByCode(serializerType);
        if(serializer == null) throw new RuntimeException("不存在对应的序列化器");

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes,0,length);
        Object deserialize =serializer.deserialize(bytes,messageType);  //消息正文
        list.add(deserialize);
    }
}
