package com.wones.schedule;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

@Slf4j
public class RandomLoadBalance implements LoadBalance{
    @Override
    public String balance(List<String> addressList) {
        Random random = new Random();
        int num = addressList.size();
        int choose = random.nextInt(num);
//        log.debug("负载均衡选择了{}服务器",choose);
        return addressList.get(choose);
    }
}
