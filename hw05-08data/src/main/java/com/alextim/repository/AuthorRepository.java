package com.alextim.repository;

import com.alextim.domain.Author;
import com.alextim.domain.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {

    @Query("select b from Book b left join b.author a where a.id = ?1")
    List<Book> getBooks(long id);

    List<Author> findByFirstnameOrLastname(String firstname, String lastname);
}