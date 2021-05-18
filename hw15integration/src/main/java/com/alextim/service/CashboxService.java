package com.alextim.service;


import com.alextim.domain.Check;
import com.alextim.domain.Food;
import org.springframework.stereotype.Service;

@Service
public class CashboxService {

    public Check getPleaseCheck(Food food) {
        if(food.getTitle().equals("whiskey"))
            return new Check(100);
        else
            return new Check(10);
    }
}