package com.alextim.service.working;


import com.alextim.domain.Author;
import com.alextim.domain.Book;
import com.alextim.repository.AuthorRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.alextim.service.working.Helper.*;

@Service
public class AuthorServiceImpl extends AuthorServiceStub implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @HystrixCommand(fallbackMethod = "addStub")
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


    @HystrixCommand(fallbackMethod = "getCountStub")
    @Transactional(readOnly = true)
    @Override
    public long getCount() {
        return authorRepository.count();
    }

    @HystrixCommand(fallbackMethod = "getAllStub")
    @Transactional(readOnly = true)
    @Override
    public List<Author> getAll(int page, int amountByOnePage) {
        return new ArrayList<>(authorRepository.findAll(PageRequest.of(page, amountByOnePage)).getContent());
    }

    @HystrixCommand(fallbackMethod = "findByIdStub")
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

    @HystrixCommand(fallbackMethod = "findStub")
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

    @HystrixCommand(fallbackMethod = "getBooksStub")
    @Transactional(readOnly = true)
    @Override
    public List<Book> getBooks(long idAuthor){
        return findById(idAuthor).getBooks();
    }

    @HystrixCommand(fallbackMethod = "updateStub")
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

    @HystrixCommand(fallbackMethod = "deleteStub")
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