package com.alextim.service;

import com.alextim.domain.Food;
import com.alextim.domain.Order;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KitchenService {

    public Food cookHot(Order order)  {
        log.info("Cooking hot {}", order.getTitle());
        Food cook = cook(order);
        log.info("Cooking cold {} hot", order.getTitle());
        return cook;
    }

    public Food cookCold(Order order) {
        log.info("Cooking cold {}", order.getTitle());
        Food cook = cook(order);
        log.info("Cooking cold {} done", order.getTitle());
        return cook;
    }

    @SneakyThrows
    public Food cook(Order order) {
        Thread.sleep(1000);
        Food food = new Food(order.getTitle());
        food.setDone(true);
        return food;
    }
}