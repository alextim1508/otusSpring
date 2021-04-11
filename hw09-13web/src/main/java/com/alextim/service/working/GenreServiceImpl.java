package com.alextim.service.working;


import com.alextim.domain.Book;
import com.alextim.domain.Genre;
import com.alextim.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.alextim.service.working.Helper.*;

@Service
public class GenreServiceImpl implements GenreService{

    @Autowired
    private GenreRepository genreRepository;

    @Transactional
    @Override
    public Genre add(String title) {
        Genre genre = new Genre(title);
        try{
            genreRepository.save(genre);
        } catch (DataIntegrityViolationException exception) {
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
        return genreRepository.count();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getAll(int page, int amountByOnePage) {
        return genreRepository.findAll(PageRequest.of(page,amountByOnePage)).getContent();
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
    public List<Genre> find(String title) {
        List<Genre> genres;
        try {
            genres = genreRepository.findByTitle(title);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }
        return genres;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getBooks(long id) {
        return findById(id).getBooks();
    }

    @Transactional
    @Override
    public Genre update(long id, String title) {
        Genre genre = findById(id);

        if(title != null)
            genre.setTitle(title);
        try {
            genreRepository.save(genre);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }
        return genre;
    }

    @Transactional
    @Override
    public void delete(long id) {
        try {
            genreRepository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }
    }
}