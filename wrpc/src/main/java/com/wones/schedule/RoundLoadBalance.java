package com.wones.schedule;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RoundLoadBalance implements LoadBalance{
    private int choose = -1;
    @Override
    public String balance(List<String> addressList) {
        choose++;
        choose = choose % addressList.size();
        return addressList.get(choose);
    }
}
