package com.alextim.repository;

import com.alextim.domain.Book;

import java.util.List;

public interface BookRepository {

    void insert(Book book);

    long getCount();
    List<Book> getAll(int page, int amountByOnePage);

    Book findById(long id);

    void update(long id, Book book);

    void delete(long id);
}