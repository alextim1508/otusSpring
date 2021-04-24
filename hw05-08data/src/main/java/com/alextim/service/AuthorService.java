package com.alextim.service;

import com.alextim.domain.Author;
import com.alextim.domain.Book;

import java.util.List;

public interface AuthorService {
    Author add(String firsname, String lastname);

    long getCount();
    List<Author> getAll(int page, int amountByOnePage);

    Author findById(long id);
    List<Author> find(String frstname, String lastname);

    List<Book> getBooks(long authorId);

    Author update(long id, String firstname, String lastname);

    void delete(long id);
}