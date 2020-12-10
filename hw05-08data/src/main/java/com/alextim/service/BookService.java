package com.alextim.service;

import com.alextim.domain.Book;

import java.util.List;

public interface BookService {
    Book add(String title, int authorId, int genreId);

    long getCount();
    List<Book> getAll(int page, int amountByOnePage);

    Book findById(long id);

    Book update(long id, String title);
    Book update(long id, String bookTitle, int authorId, int genreId);

    void delete(long id);
}
