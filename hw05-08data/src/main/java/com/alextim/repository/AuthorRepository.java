package com.alextim.repository;

import com.alextim.domain.Author;
import com.alextim.domain.Book;

import java.util.List;

public interface AuthorRepository {

    void insert(Author author);

    long getCount();
    List<Author> getAll(int page, int amountByOnePage);

    Author findById(long id);

    void update(long id, Author author);

    void delete(long id);
}
