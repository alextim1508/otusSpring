package com.alextim.repository;

import com.alextim.domain.Book;
import com.alextim.domain.Genre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface GenreRepository extends PagingAndSortingRepository<Genre, Long> {

    @Query("select b from Book b left join b.genre g where g.id = ?1")
    List<Book> getBooks(long id);

    List<Genre> findByTitle(String title);
}