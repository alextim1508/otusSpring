package com.alextim.service;


import com.alextim.domain.Book;
import com.alextim.domain.Genre;
import com.alextim.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceJpa implements GenreService{

    private final String ERROR_STRING = "Операция с объектом %s не выполнена";
    private final String DUPLICATE_ERROR_STRING = "Запись %s существует";

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public Genre add(String title) {
        Genre genre = new Genre(title);
        try{
            genreRepository.insert(genre);
        }
        catch (DataIntegrityViolationException exception) {
            String causeMsg= exception.getCause().getCause().getMessage();
            if(causeMsg.contains("Нарушение уникального индекса или первичного ключ"))
                throw new RuntimeException(String.format(DUPLICATE_ERROR_STRING, genre));
            else
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
        }
        catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }
        return genre;
    }

    @Override
    public List<Book> getBooks(long id) {
        return genreRepository.getBooks(id);
    }

    @Override
    public Genre update(long id, String title) {
        Genre genre = new Genre(title);
        try {
            genreRepository.update(id, genre);
        }
        catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }
        return genre;
    }

    @Override
    public void delete(long id) {
        try {
            genreRepository.delete(id);
        }
        catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }
    }
}