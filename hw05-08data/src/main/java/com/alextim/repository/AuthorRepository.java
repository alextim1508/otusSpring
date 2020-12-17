package com.alextim.repository;

import com.alextim.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    void insert(Author author);

    long getCount();
    List<Author> getAll(int page, int amountByOnePage);

    Optional<Author> findById(long id);
    List<Author> findByLastname(String lastname);

    void update(Author author);

    void delete(Author author);
}