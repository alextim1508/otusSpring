package com.alextim.service;

import com.alextim.domain.Person;

import java.util.List;

public interface PersonService {
    Person add(String name);

    long getCount();
    List<Person> getAll(int page, int amountByOnePage);

    Person findById(long id);
    List<Person> find(String name);

    Person update(long id, String name);

    void delete(long id);
}