package com.alextim.service;


import com.alextim.domain.Author;
import com.alextim.domain.Book;
import com.alextim.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorServiceJpa implements AuthorService{

    private final String ERROR_STRING = "Операция с объектом %s не выполнена";
    private final String DUPLICATE_ERROR_STRING = "Запись %s существует";

    @Autowired
    private AuthorRepository authorRepository;


    @Override
    public Author add(String firstname, String lastname) {
        Author author = new Author(firstname, lastname);
        try {
            authorRepository.insert(author);
        }
        catch(DataIntegrityViolationException exception) {
            if(exception.getCause().getCause().getMessage().contains("Нарушение уникального индекса или первичного ключ"))
                throw new RuntimeException(String.format(DUPLICATE_ERROR_STRING, author));
            else
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
        }
        catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }
        return author;
    }

    @Override
    public List<Book> getBooks(long id){
        return authorRepository.getBooks(id);
    }

    @Override
    public Author update(long id, String firstname, String lastname)  {
        Author author = new Author(firstname, lastname);
        try {
            authorRepository.update(id, author);
        }
        catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }
        return author;
    }

    @Override
    public void delete(long id) {
        try {
            authorRepository.delete(id);
        }
        catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }
    }
}