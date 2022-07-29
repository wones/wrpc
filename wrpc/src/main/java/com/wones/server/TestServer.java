package com.wones.server;

import com.wones.config.Config;
import com.wones.service.BlogService;
import com.wones.service.UserService;
import com.wones.service.impl.BlogServiceImpl;
import com.wones.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();
//        log.debug("UserService:{}",userService);
//        log.debug("BlogService:{}",blogService);

        String host = Config.getServerHost();
        int port = Config.getServerPort();

        ServiceProvider serviceProvider = new ServiceProvider(host,port);

//        log.debug("ServiceProvider:{}",serviceProvider);
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);
//        log.debug("userService接口:{}",userService.getClass().getInterfaces());
//        log.debug("blogService接口:{}",blogService.getClass().getInterfaces());
        RPCServer rpcServer = new NettyRPCServer(serviceProvider);
        rpcServer.start(port);
    }
}
