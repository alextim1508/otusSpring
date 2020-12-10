package com.alextim.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class Helper {

    static final String ERROR_STRING = "Операция с объектом %s не выполнена";
    static final String DUPLICATE_ERROR_STRING = "Запись %s существует";
    static final String EMPTY_RESULT_BY_ID_ERROR_STRING = "Объект %s c id %d не найден";
}
