package com.alextim.repository;

import com.alextim.domain.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    List<Book> findByTitleContaining(String sub);
}