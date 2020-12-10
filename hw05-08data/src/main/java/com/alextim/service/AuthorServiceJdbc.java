package com.alextim.service;


import com.alextim.domain.Author;
import com.alextim.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.alextim.service.Helper.*;

@Service
@RequiredArgsConstructor
public class AuthorServiceJdbc implements AuthorService{

    private final AuthorRepository authorRepository;

    @Override
    public Author add(String firstname, String lastname) {
        Author author = new Author(firstname, lastname);
        try {
            authorRepository.insert(author);
        } catch (DuplicateKeyException exception){
            throw new RuntimeException(String.format(DUPLICATE_ERROR_STRING, author));
        } catch(DataAccessException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, author));
        }
        return author;
    }

    @Override
    public long getCount() {
        return authorRepository.getCount();
    }

    @Override
    public List<Author> getAll(int page, int amountByOnePage) {
        return authorRepository.getAll(page, amountByOnePage);
    }

    @Override
    public Author findById(long id) {
        Author author;
        try {
            author = authorRepository.findById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new RuntimeException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Author.class.getSimpleName(), id));
        } catch(DataAccessException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }
        return author;
    }

    @Override
    public Author update(long id, String firstname, String lastname)  {
        Author author = new Author(firstname, lastname);
        try {
            authorRepository.update(id, author);
        } catch(DataAccessException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }
        return author;
    }

    @Override
    public void delete(long id) {
        try {
            authorRepository.delete(id);
        } catch(DataAccessException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }
    }
}
