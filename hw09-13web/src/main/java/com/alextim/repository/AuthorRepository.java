package com.alextim.repository;

import com.alextim.domain.Author;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {

    List<Author> findByFirstnameOrLastname(String firstname, String lastname);
}