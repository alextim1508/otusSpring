package com.alextim.controller;


import com.alextim.domain.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class PersonDto {

    private String id;
    private String name;

    public static Person toDomainObject(PersonDto dto) {
        return new Person(dto.getName());
    }

    public static PersonDto toDataTransferObject(Person dao) {
        return new PersonDto(Long.toString(dao.getId()), dao.getName());
    }
}