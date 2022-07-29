package com.wones.common.protocol;


import com.wones.common.RPCRequest;
import com.wones.common.RPCResponse;
import com.wones.config.Config;
import io.protostuff.LinkBuffer;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;


public class ProtobufSerializer implements Serializer{
    private static final LinkedBuffer BUFFER = LinkedBuffer.allocate(LinkBuffer.DEFAULT_BUFFER_SIZE);
    @Override
    public byte[] serialize(Object obj) {
        Class<?> clazz = obj.getClass();
        Schema schema = RuntimeSchema.getSchema(clazz);
        byte[] bytes;
        try{
            bytes = ProtostuffIOUtil.toByteArray(obj,schema, BUFFER);
        }finally {
            BUFFER.clear();
        }
        return bytes;
    }

    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object obj = null;
        Schema schema = null;
        //code代表消息类型
        int code = messageType;
        if(messageType == Config.getMessageTypeRequest()){
            code = 0;
        }else if(messageType == Config.getMessageTypeResponse()){
            code = 1;
        }

        switch (code){
            //0:请求消息 1：响应消息
            case 0:
                schema = RuntimeSchema.getSchema(RPCRequest.class);
                obj = schema.newMessage();
                ProtostuffIOUtil.mergeFrom(bytes,obj,schema);
                break;
            case 1:
                schema = RuntimeSchema.getSchema(RPCResponse.class);
                obj = schema.newMessage();
                ProtostuffIOUtil.mergeFrom(bytes,obj,schema);
                break;
            default:
                System.out.println("暂时不支持此种消息");
                throw new RuntimeException();
        }
        return obj;
    }

    @Override
    public int getType() {
        return 2;
    }
}
