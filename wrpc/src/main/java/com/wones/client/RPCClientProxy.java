package com.wones.client;

import com.wones.common.RPCRequest;
import com.wones.common.RPCResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
@Slf4j
@AllArgsConstructor
public class RPCClientProxy implements InvocationHandler {
    private RPCClient client;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RPCRequest request = new RPCRequest(method.getDeclaringClass().getName(),
                method.getName(),
                args,
                method.getParameterTypes());
        //数据传输
        log.debug("客户端发送的请求：{}",request);
        RPCResponse response = client.sendRequest(request);
        log.debug("客户端收到的响应：{}",response);
        return response.getData();
    }
    <T>T getProxy(Class<T> clazz){
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz}, this);
        return (T)o;
    }
}
