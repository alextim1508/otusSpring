package com.alextim.repository;

import com.alextim.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    void insert(Genre genre);

    long getCount();
    List<Genre> getAll(int page, int amountByOnePage);

    Optional<Genre> findById(long id);
    List<Genre> findByTitle(String title);

    void update(Genre genre);

    void delete(Genre genre);
}