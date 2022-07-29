package com.wones.register;

import com.wones.config.Config;
import com.wones.schedule.LoadBalance;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetSocketAddress;
import java.util.List;

public class ZkServiceRegister implements ServiceRegister{
    private CuratorFramework client;
    private static final String ROOT_PATH = "wrpc";
    private LoadBalance loadBalance = LoadBalance.getLoadBalanceByCode(Config.getSchedule());
    public ZkServiceRegister(){
        RetryPolicy policy = new ExponentialBackoffRetry(1000,3);
        this.client = CuratorFrameworkFactory.builder()
                .connectString(Config.getZkServerAddress())
                .sessionTimeoutMs(40000)
                .retryPolicy(policy)
                .namespace(ROOT_PATH).build();
        this.client.start();
        System.out.println("zookeeper 连接成功");
    }
    @Override
    public void register(String serviceName, InetSocketAddress serverAddress) {
        try{
            if (client.checkExists().forPath("/"+serviceName) == null) {
                client.create()
                        .creatingParentContainersIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .forPath("/" + serviceName);
            }
            String path = "/" + serviceName + "/" + getServiceAddress(serverAddress);
            client.create().creatingParentContainersIfNeeded()
                    .withMode(CreateMode.EPHEMERAL).forPath(path);
        }catch (Exception e){
            System.out.println("此服务已存在");
        }
    }

    @Override
    public InetSocketAddress serviceDiscovery(String serviceName) {
        try{
            List<String> strings = client.getChildren().forPath("/" + serviceName);
            String string = loadBalance.balance(strings);
            return parseAddress(string);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // 地址 -> XXX.XXX.XXX.XXX:port 字符串
    private String getServiceAddress(InetSocketAddress serverAddress){
        return serverAddress.getHostName()
                + ":" + serverAddress.getPort();
    }
    //字符串解析为地址
    private InetSocketAddress parseAddress(String address){
        String[] result = address.split(":");
        return new InetSocketAddress(result[0],Integer.parseInt(result[1]));
    }
}
