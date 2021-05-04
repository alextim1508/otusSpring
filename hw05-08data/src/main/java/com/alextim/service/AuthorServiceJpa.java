package com.alextim.service;


import com.alextim.domain.Author;
import com.alextim.domain.Book;
import com.alextim.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


import static com.alextim.service.HandlerException.EMPTY_RESULT_BY_ID_ERROR_STRING;
import static com.alextim.service.HandlerException.handlerException;


@Service
@RequiredArgsConstructor
@Transactional
public class AuthorServiceJpa implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author add(String firsname, String lastname) throws Exception {
        Author author = new Author(firsname, lastname);
        try {
            authorRepository.save(author);
        } catch(DataAccessException exception) {
            handlerException(exception, author.toString());
        }
        return author;
    }

    @Override
    public long getCount() {
        return authorRepository.count();
    }

    @Override
    public List<Author> getAll(int page, int amountByOnePage) {
        return authorRepository.findAll(PageRequest.of(page,amountByOnePage)).getContent();
    }

    @Override
    public Author findById(ObjectId id) {
        return authorRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Author.class.getSimpleName(), id)));
    }

    @Override
    public List<Author> find(String frstname, String lastname) throws Exception {
        List<Author> authors = null;
        try {
            authorRepository.findByFirstnameOrLastname(frstname, lastname).forEach(authors::add);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, Author.class.getSimpleName());
        }
        return authors;
    }

    @Override
    public Author update(ObjectId id, String firstname, String lastname) throws Exception {
        Author author = authorRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Author.class.getSimpleName(), id)));
        if(firstname!= null)
            author.setFirstname(firstname);
        if(lastname!=null)
            author.setLastname(lastname);
        try {
            authorRepository.save(author);
        } catch (DataIntegrityViolationException exception) {
            handlerException(exception, Author.class.getSimpleName());
        }
        return author;
    }

    public void delete(ObjectId id) throws Exception {
        try {
            authorRepository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            handlerException(exception, Author.class.getSimpleName());
        }
    }

    @Override
    public void deleteAll() {
        authorRepository.deleteAll();
    }
}