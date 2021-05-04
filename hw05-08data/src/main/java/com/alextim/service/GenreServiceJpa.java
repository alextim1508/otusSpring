package com.alextim.service;


import com.alextim.domain.Author;
import com.alextim.domain.Book;
import com.alextim.domain.Genre;
import com.alextim.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.alextim.service.HandlerException.EMPTY_RESULT_BY_ID_ERROR_STRING;
import static com.alextim.service.HandlerException.handlerException;

@Service
@RequiredArgsConstructor
@Transactional
public class GenreServiceJpa implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public Genre add(String title) throws Exception {
        Genre genre = new Genre(title);
        try{
            genreRepository.save(genre);
        } catch (DataIntegrityViolationException exception) {
            handlerException(exception, genre.toString());
        }
        return genre;
    }

    @Override
    public long getCount() {
        return genreRepository.count();
    }

    @Override
    public List<Genre> getAll(int page, int amountByOnePage) {
        return genreRepository.findAll(PageRequest.of(page,amountByOnePage)).getContent();
    }

    @Override
    public Genre findById(ObjectId id) throws Exception {
        Genre genre = genreRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Genre.class.getSimpleName(), id)));
        return genre;
    }

    @Override
    public List<Genre> find(String title) throws Exception {
        List<Genre> genres = null;
        try {
            genreRepository.findByTitle(title).forEach(genres::add);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, Genre.class.getSimpleName());
        }
        return genres;
    }

    @Override
    public Genre update(ObjectId id, String title) throws Exception {
        Genre genre = genreRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Genre.class.getSimpleName(), id)));
        if(title!= null) {
            genre.setTitle(title);
            try {
                genreRepository.save(genre);
            }
            catch (DataIntegrityViolationException exception) {
                handlerException(exception, Author.class.getSimpleName());
            }
        }
        return genre;
    }

    @Override
    public void delete(ObjectId id) throws Exception {
        try {
            genreRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, Genre.class.getSimpleName());
        }
    }

    @Override
    public void deleteAll() {
        genreRepository.deleteAll();
    }
}
