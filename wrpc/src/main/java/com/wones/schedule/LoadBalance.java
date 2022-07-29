package com.wones.schedule;

import java.util.List;

//给服务器地址列表，根据不同的负载均衡策略选择一个
public interface LoadBalance {
    String balance(List<String> addressList);
    static LoadBalance getLoadBalanceByCode(int code){
        switch (code){
            case 0:
                return new RandomLoadBalance();
            case 1:
                return new RoundLoadBalance();
            default:
                return null;
        }
    }
}
