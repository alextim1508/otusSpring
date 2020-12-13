package com.alextim.repository;

import com.alextim.domain.Book;
import com.alextim.domain.Genre;

import java.util.List;

public interface GenreRepository {

    void insert(Genre genre);

    long getCount();
    List<Genre> getAll(int page, int amountByOnePage);

    Genre findById(long id);
    List<Genre> findByTitle(String title);

    List<Book> getBooks(long id);

    void update(long id, Genre genre);

    void delete(long id);
}