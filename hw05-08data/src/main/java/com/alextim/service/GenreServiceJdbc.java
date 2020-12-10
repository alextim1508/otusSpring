package com.alextim.service;


import com.alextim.domain.Genre;
import com.alextim.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.alextim.service.Helper.*;

@Service
@RequiredArgsConstructor
public class GenreServiceJdbc implements GenreService{

    private final GenreRepository genreRepository;

    @Override
    public Genre add(String title) {
        Genre genre = new Genre(title);
        try{
            genreRepository.insert(genre);
        } catch (DuplicateKeyException exception){
            throw new RuntimeException(String.format(DUPLICATE_ERROR_STRING, genre));
        } catch(DataAccessException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, genre));
        }
        return genre;
    }

    @Override
    public long getCount() {
        return genreRepository.getCount();
    }

    @Override
    public List<Genre> getAll(int page, int amountByOnePage) {
        return genreRepository.getAll(page, amountByOnePage);
    }

    @Override
    public Genre findById(long id) {
        Genre genre;
        try {
            genre = genreRepository.findById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new RuntimeException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Genre.class.getSimpleName(), id));
        } catch(DataAccessException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }
        return genre;
    }

    @Override
    public Genre update(long id, String title) {
        Genre genre = new Genre(title);
        try {
            genreRepository.update(id, genre);
        } catch(DataAccessException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }
        return genre;
    }

    @Override
    public void delete(long id) {
        try {
            genreRepository.delete(id);
        } catch(DataAccessException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }
    }
}
