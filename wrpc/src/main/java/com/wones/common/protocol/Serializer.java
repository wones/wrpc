package com.wones.common.protocol;

public interface Serializer {
    //把对象序列化成字节数组
    byte[] serialize(Object obj);
    //从字节数组反序列成消息
    Object deserialize(byte[] bytes,int messageType);
    //返回使用的序列化方式
    int getType();
    static Serializer getSerializerByCode(int code){
        switch (code){
            case 0:
                return new ObjectSerializer();
            case 1:
                return new JsonSerializer();
            case 2:
                return new ProtobufSerializer();
            case 3:
                return new KryoSerializer();
            default:
                return null;
        }
    }
}
