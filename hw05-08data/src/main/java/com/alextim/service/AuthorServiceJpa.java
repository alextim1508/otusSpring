package com.alextim.service;


import com.alextim.domain.Author;
import com.alextim.domain.Book;
import com.alextim.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.alextim.service.Helper.*;

@Service
public class AuthorServiceJpa implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Transactional
    @Override
    public Author add(String firstname, String lastname) {
        Author author = new Author(firstname, lastname);
        try {
            authorRepository.save(author);
        } catch(DataIntegrityViolationException exception) {
            if(exception.getCause().getCause().getMessage().contains("Нарушение уникального индекса или первичного ключ"))
                throw new RuntimeException(String.format(DUPLICATE_ERROR_STRING, author));
            else
                throw new RuntimeException(String.format(ERROR_STRING, author));
        }
        return author;
    }

    @Transactional(readOnly = true)
    @Override
    public long getCount() {
        return authorRepository.count();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAll(int page, int amountByOnePage) {
        return authorRepository.findAll(PageRequest.of(page,amountByOnePage)).getContent();
    }

    @Transactional(readOnly = true)
    @Override
    public Author findById(long id) {
        Author author;
        try {
            author = authorRepository.findById(id).orElseThrow(()->
                    new RuntimeException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING,  Author.class.getSimpleName(), id)));
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }
        return author;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> find(String frstname, String lastname) {
        List<Author> authors;
        try {
            authors = authorRepository.findByFirstnameOrLastname(frstname, lastname);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }
        return authors;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getBooks(long id){
        return findById(id).getBooks();
    }

    @Transactional
    @Override
    public Author update(long id, String firstname, String lastname)  {
        Author author = findById(id);

        if(firstname != null)
            author.setFirstname(firstname);
        if(lastname != null)
            author.setLastname(lastname);

        try {
            authorRepository.save(author);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }
        return author;
    }

    @Transactional
    @Override
    public void delete(long id) {
        try {
            authorRepository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }
    }
}