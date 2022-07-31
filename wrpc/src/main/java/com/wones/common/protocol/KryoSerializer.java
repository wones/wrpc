package com.wones.common.protocol;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.BeanSerializer;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.wones.common.RPCRequest;
import com.wones.common.RPCResponse;
import com.wones.config.Config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class KryoSerializer implements Serializer{
    private final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(()->{
        Kryo kryo = new Kryo();
        kryo.register(RPCRequest.class);
        kryo.register(RPCResponse.class);
        return kryo;
    });
    @Override
    public byte[] serialize(Object obj) {
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Output output = new Output(byteArrayOutputStream)){
            Kryo kryo = kryoThreadLocal.get();
            kryo.writeObject(output,obj);
            kryoThreadLocal.remove();
            return output.toBytes();

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        int code = messageType;
        Object obj;
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             Input input = new Input(byteArrayInputStream)){
            Kryo kryo = kryoThreadLocal.get();
            if(messageType == Config.getMessageTypeRequest()){
                code = 0;
            }else if(messageType == Config.getMessageTypeResponse()){
                code = 1;
            }

            switch (code){
                //0:请求消息 1：响应消息
                case 0:
                    obj = kryo.readObject(input,RPCRequest.class);
                    kryoThreadLocal.remove();
                    return obj;
                case 1:
                    obj = kryo.readObject(input,RPCResponse.class);
                    kryoThreadLocal.remove();
                    return obj;
                default:
                    System.out.println("暂时不支持此种消息");
                    throw new RuntimeException();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getType() {
        return 3;
    }
}
