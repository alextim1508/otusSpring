package com.alextim.service;

import com.alextim.domain.Author;
import com.alextim.domain.Book;
import com.alextim.domain.Comment;
import com.alextim.domain.Genre;
import com.alextim.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.alextim.service.HandlerException.EMPTY_RESULT_BY_ID_ERROR_STRING;
import static com.alextim.service.HandlerException.handlerException;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceJpa implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    @Override
    public Book add(String title, ObjectId authorId, ObjectId genreId) throws Exception {
        Book book = new Book(title, authorService.findById(authorId), genreService.findById(genreId));
        book = add(book);
        return book;
    }

    @Override
    public Book add(String authorTitle, String authorFirsname, String authorLastname, String genreTitle) throws Exception {
        Author addedAuthor = authorService.add(authorFirsname, authorLastname);
        Genre addedGenre = genreService.add(genreTitle);
        Book book = new Book(authorTitle, addedAuthor, addedGenre);
        book = add(book);
        return book;
    }

    private Book add(Book book) throws Exception {
        try{
            bookRepository.save(book);
        } catch (DataIntegrityViolationException exception) {
            handlerException(exception, book.toString());
        }
        return book;
    }

    @Override
    public long getCount() {
        return bookRepository.count();
    }

    @Override
    public List<Book> getAll(int page, int amountByOnePage) {
        return bookRepository.findAll(PageRequest.of(page,amountByOnePage)).getContent();
    }

    @Override
    public Book findById(ObjectId id) throws Exception {
        return bookRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Book.class.getSimpleName(), id)));
    }

    @Override
    public List<Book> findByTitle(String subTitle) throws Exception {
        List<Book> books = null;
        try {
            bookRepository.findByTitleContaining(subTitle).forEach(books::add);
        } catch (DataIntegrityViolationException exception) {
            handlerException(exception, Book.class.getSimpleName());
        }
        return books;
    }

    @Override
    public List<Book> findByAuthor(ObjectId authorId) throws Exception {
        List<Book> books = new ArrayList<>();
        bookRepository.findByAuthor(authorId).forEach(books::add);
        return books;
    }

    @Override
    public List<Book> findByGenre(ObjectId genreId) throws Exception {
        List<Book> books = new ArrayList<>();
        bookRepository.findByGenre(genreId).forEach(books::add);
        return books;
    }

    @Override
    public void addComments(ObjectId bookId, ObjectId commentId) throws Exception {
        Comment commentById = commentService.findById(commentId);
        bookRepository.addComment(bookId, commentById);
    }

    @Override
    public List<Comment> getComments(ObjectId id) throws Exception {
        Book byId = findById(id);
        return byId.getComments();
    }

    @Override
    public Book update(ObjectId id, String title) throws Exception {
        Book book = bookRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Book.class.getSimpleName(), id)));
        if(title!= null)
            book.setTitle(title);
        try {
            bookRepository.save(book);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, Book.class.getSimpleName());
        }
        return book;
    }

    @Override
    public Book update(ObjectId id, String bookTitle, ObjectId authorId, ObjectId genreId) throws Exception {
        Book book = bookRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Book.class.getSimpleName(), id)));
        if(bookTitle!= null)
            book.setTitle(bookTitle);
        if(authorId != null)
            book.setAuthor(authorService.findById(authorId));
        if(genreId != null)
            book.setGenre(genreService.findById(genreId));
        try{
            bookRepository.save(book);
        } catch (DataIntegrityViolationException exception) {
            handlerException(exception, Book.class.getSimpleName());
        }
        return book;
    }

    @Override
    public void delete(ObjectId id) throws Exception {
        try {
            bookRepository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            handlerException(exception, Book.class.getSimpleName());
        }
    }

    @Override
    public void deleteAll() {
        bookRepository.deleteAll();
    }
}