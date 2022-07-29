package com.wones.client;

import com.wones.common.Blog;
import com.wones.common.User;
import com.wones.service.BlogService;
import com.wones.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestClient {
    public static void main(String[] args) {
        RPCClient rpcClient = new NettyRPCClient();
//        log.debug("rpcclient:{}",rpcClient);
        RPCClientProxy rpcClientProxy = new RPCClientProxy(rpcClient);
//        log.debug("rpcclientProxy:{}",rpcClientProxy);
        UserService userService = rpcClientProxy.getProxy(UserService.class);
        User user = userService.getUserById(5);
//        log.debug("从服务端获取User:{}",user);
        System.out.println(user);
        BlogService blogService = rpcClientProxy.getProxy(BlogService.class);
        Blog blog = blogService.getBlogByBid(2);
//        log.debug("从服务端获取Blog:{}",blog);
        System.out.println(blog);
    }
}
