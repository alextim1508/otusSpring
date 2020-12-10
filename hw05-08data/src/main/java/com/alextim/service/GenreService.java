package com.alextim.service;

import com.alextim.domain.Book;
import com.alextim.domain.Genre;

import java.util.List;

public interface GenreService {
    Genre add(String title);

    long getCount();
    List<Genre> getAll(int page, int amountByOnePage);

    Genre findById(long id);

    Genre update(long id, String title);

    void delete(long id);
}