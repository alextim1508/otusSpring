package com.alextim.repository;

import com.alextim.domain.Book;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    List<Book> findByTitleContaining(String sub);
}