package com.alextim.repository;

import com.alextim.domain.Person;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface AuthorRepository extends PagingAndSortingRepository<Person, Long> {
    List<Person> findByName(String name);
}