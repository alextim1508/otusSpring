package com.alextim.service;

import com.alextim.domain.Book;

import java.util.List;

public interface BookService {
    Book add(String title, long authorId, long genreId);
    Book add(String authorTitle, String authorFirsname, String authorLastname, String genreTitle);

    long getCount();
    List<Book> getAll(int page, int amountByOnePage);

    Book findById(long id);

    Book update(long id, String title, long authorId, long genreId);

    void delete(long id);
}