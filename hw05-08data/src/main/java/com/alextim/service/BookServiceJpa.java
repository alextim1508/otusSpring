package com.alextim.service;


import com.alextim.domain.Author;
import com.alextim.domain.Book;
import com.alextim.domain.Comment;
import com.alextim.domain.Genre;
import com.alextim.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceJpa implements BookService {

    private final String ERROR_STRING = "Операция с объектом %s не выполнена";
    private final String DUPLICATE_ERROR_STRING = "Запись %s существует";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenreService genreService;


    @Override
    public Book add(String title, int authorId, int genreId) {
        Book book = new Book(title, authorService.findById(authorId), genreService.findById(genreId));
        book = add(book);
        return book;
    }

    @Override
    public Book add(String authorTitle, String authorFirsname, String authorLastname, String genreTitle) {
        Author addedAuthor = authorService.add(authorFirsname, authorLastname);
        Genre addedGenre = genreService.add((genreTitle));
        Book book = new Book(authorTitle, addedAuthor, addedGenre);
        book = add(book);
        return book;
    }

    private Book add(Book book) {
        try{
            bookRepository.insert(book);
        }
        catch (DataIntegrityViolationException exception) {
            String causeMsg= exception.getCause().getCause().getMessage();
            if(causeMsg.contains("Нарушение уникального индекса или первичного ключ"))
                throw new RuntimeException(String.format(DUPLICATE_ERROR_STRING, book));
            else
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
        }
        catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }
        return book;
    }

    @Override
    public List<Comment> getComments(long id) {
        return bookRepository.getComments(id);
    }

    @Override
    public Book update(long bookId, String title) {
        Book currentBook = findById(bookId);
        Book newBook = new Book(title, currentBook.getAuthor(), currentBook.getGenre());
        try {
            bookRepository.update(bookId, newBook);
        }
        catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }
        return null;
    }

    @Override
    public Book update(long id, String bookTitle, int authorId, int genreId) {
        Book newBook = new Book(bookTitle, authorService.findById(authorId), genreService.findById(genreId));
        try {
            bookRepository.update(id, newBook);
        }
        catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }
        return newBook;
    }

    @Override
    public void delete(long id) {
        try {
            bookRepository.delete(id);
        }
        catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }
    }
}