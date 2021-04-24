package com.alextim.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class MessageDto implements Dto {
    private String message;
}
