package com.alextim.repository;

import com.alextim.domain.Genre;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends PagingAndSortingRepository<Genre, Long> {

    List<Genre> findByTitle(String title);
}