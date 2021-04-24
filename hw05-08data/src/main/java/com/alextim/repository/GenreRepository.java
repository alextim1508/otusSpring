package com.alextim.repository;

import com.alextim.domain.Book;
import com.alextim.domain.Genre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface GenreRepository extends PagingAndSortingRepository<Genre, Long> {

    List<Genre> findByTitle(String title);
}