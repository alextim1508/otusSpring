package com.alextim.repository;

import com.alextim.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    void insert(Book book);

    long getCount();
    List<Book> getAll(int page, int amountByOnePage);

    Optional<Book> findById(long id);
    List<Book> findByTitle(String title);

    void update(Book book);

    void delete(Book book);
}