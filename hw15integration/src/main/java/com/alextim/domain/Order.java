package com.alextim.domain;

import lombok.Data;

@Data
public class Order {
    private final String title;
    private final boolean isIced;
    private final boolean isDrink;
}
