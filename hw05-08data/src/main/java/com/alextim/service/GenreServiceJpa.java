package com.alextim.service;


import com.alextim.domain.Author;
import com.alextim.domain.Book;
import com.alextim.domain.Genre;
import com.alextim.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.alextim.service.Helper.*;

@Service
public class GenreServiceJpa implements GenreService{

    @Autowired
    private GenreRepository genreRepository;

    @Transactional
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

    @Transactional(readOnly = true)
    @Override
    public long getCount() {
        return genreRepository.getCount();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getAll(int page, int amountByOnePage) {
        return genreRepository.getAll(page, amountByOnePage);
    }

    @Transactional(readOnly = true)
    @Override
    public Genre findById(long id) {
        Genre genre;
        try {
            genre = genreRepository.findById(id).orElseThrow(()->
                    new RuntimeException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Genre.class.getSimpleName(), id)));
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }
        return genre;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getBooks(long genreId) {
        return findById(genreId).getBooks();
    }

    @Transactional
    @Override
    public Genre update(long id, String title) {
        Genre genre = findById(id);

        if(title != null)
            genre.setTitle(title);
        try {
            genreRepository.update(genre);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }
        return genre;
    }

    @Transactional
    @Override
    public void delete(long id) {
        Genre genre = findById(id);
        try {
            genreRepository.delete(genre);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }
    }
}