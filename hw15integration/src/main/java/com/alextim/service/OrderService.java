package com.alextim.service;

import com.alextim.domain.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public Order specify(String order) {

        if(order.equals("something satisfying"))
            return new Order("meat", false, false);
        if(order.equals("something strong"))
            return new Order("whiskey", true, true);
        else
            return new Order("tea", false, true);
    }
}
