package com.alextim.service;

import com.alextim.domain.Book;
import com.alextim.domain.Comment;

import java.util.List;

public interface BookService {
    Book add(String title, int authorId, int genreId);
    Book add(String authorTitle, String authorFirsname, String authorLastname, String genreTitle);

    long getCount();
    List<Book> getAll(int page, int amountByOnePage);

    Book findById(long id);

    List<Comment> getComments(long id);

    Book update(long id, String title, int authorId, int genreId);

    void delete(long id);
}