package com.alextim.service.working;


import com.alextim.domain.Author;
import com.alextim.domain.Book;
import com.alextim.domain.Genre;
import com.alextim.repository.BookRepository;
import com.alextim.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.alextim.service.working.Helper.*;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private SecurityService securityService;

    @Transactional
    @Override
    public Book add(String title, long authorId, long genreId) {
        Book book = new Book(title, authorService.findById(authorId), genreService.findById(genreId));
        return add(book);
    }

    @Transactional
    @Override
    public Book add(String bookTitle, String authorFirstname, String authorLastname, String genreTitle) {
        Author addedAuthor = authorService.add(authorFirstname, authorLastname);
        Genre addedGenre = genreService.add(genreTitle);
        Book book = new Book(bookTitle, addedAuthor, addedGenre);
        return add(book);
    }

    private Book add(Book book) {
        try{
            bookRepository.save(book);
        } catch (DataIntegrityViolationException exception) {
            String causeMsg= exception.getCause().getCause().getMessage();
            if(causeMsg.contains("Нарушение уникального индекса или первичного ключ"))
                throw new RuntimeException(String.format(DUPLICATE_ERROR_STRING, book));
            else
                throw new RuntimeException(String.format(ERROR_STRING, book));
        }

        securityService.addSecurity(
                SecurityContextHolder.getContext().getAuthentication(),
                book.getId(),
                Book.class);

        return book;
    }

    @Transactional(readOnly = true)
    @Override
    public long getCount() {
        return bookRepository.count();
    }

    @PostFilter("hasPermission(filterObject, 'ADMINISTRATION') or hasRole('ROLE_ADMIN')")
    @Transactional(readOnly = true)
    @Override
    public List<Book> getAll(int page, int amountByOnePage) {
        return new ArrayList<>(bookRepository.findAll(PageRequest.of(page, amountByOnePage)).getContent());
    }

    @Transactional(readOnly = true)
    @Override
    public Book findById(long id) {
        Book book;
        try {
            book = bookRepository.findById(id).orElseThrow(()->
                    new RuntimeException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Book.class.getSimpleName(), id)));
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }
        return book;
    }

    @Transactional
    @Override
    public Book update(long id, String title, long authorId, long genreId) {
        Book book = findById(id);
        if(title != null)
            book.setTitle(title);
        if(authorId != -1)
            book.setAuthor(authorService.findById(authorId));
        if(genreId != -1)
            book.setGenre( genreService.findById(genreId));

        try {
            bookRepository.save(book);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }
        return book;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @Override
    public void delete(long id) {
        try {
            bookRepository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }
    }
}