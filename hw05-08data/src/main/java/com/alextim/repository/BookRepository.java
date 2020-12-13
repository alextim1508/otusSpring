package com.alextim.repository;

import com.alextim.domain.Book;
import com.alextim.domain.Comment;

import java.util.List;

public interface BookRepository {

    void insert(Book book);

    long getCount();
    List<Book> getAll(int page, int amountByOnePage);

    Book findById(long id);
    List<Book> findByTitle(String title) ;

    List<Comment> getComments(long id);

    void update(long id, Book book);

    void delete(long id);
}