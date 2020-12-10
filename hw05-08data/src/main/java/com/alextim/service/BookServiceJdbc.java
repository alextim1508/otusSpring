package com.alextim.service;


import com.alextim.domain.Book;
import com.alextim.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.alextim.service.Helper.DUPLICATE_ERROR_STRING;
import static com.alextim.service.Helper.ERROR_STRING;

@Service
@RequiredArgsConstructor
public class BookServiceJdbc implements BookService {

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    private final GenreService genreService;

    @Override
    public Book add(String title, int authorId, int genreId) {
        Book book = new Book(title, authorService.findById(authorId), genreService.findById(genreId));
        try {
            bookRepository.insert(book);
        } catch (DuplicateKeyException exception){
            throw new RuntimeException(String.format(DUPLICATE_ERROR_STRING, book));
        } catch(DataAccessException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, book));
        }
        return book;
    }

    @Override
    public long getCount() {
        return bookRepository.getCount();
    }

    @Override
    public List<Book> getAll(int page, int amountByOnePage) {
        return bookRepository.getAll(page, amountByOnePage);
    }

    @Override
    public Book findById(long id) {
        Book book;
        try {
            book = bookRepository.findById(id);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }
        return book;
    }

    @Override
    public Book update(long bookId, String title) {
        Book currentBook = findById(bookId);
        Book newBook = new Book(title, currentBook.getAuthor(), currentBook.getGenre());
        try {
            bookRepository.update(bookId, newBook);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }
        return newBook;
    }

    @Override
    public Book update(long bookId, String bookTitle, int authorId, int genreId) {
        Book newBook = new Book(bookTitle, authorService.findById(authorId), genreService.findById(genreId));
        try{
            bookRepository.update(bookId, newBook);
        } catch(DataAccessException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }
        return newBook;
    }

    @Override
    public void delete(long id) {
        try {
            bookRepository.delete(id);
        } catch(DataAccessException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }
    }
}
