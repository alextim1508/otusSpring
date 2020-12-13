package com.alextim.service;

import com.alextim.domain.Comment;
import com.alextim.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceJpa implements CommentService{

    private final String ERROR_STRING = "Операция с объектом %s не выполнена";
    private final String DUPLICATE_ERROR_STRING = "Запись %s существует";

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookService bookService;

    @Override
    public void add(String comment, int bookId) {
        Comment com = new Comment(comment, bookService.findById(bookId));
        try {
            commentRepository.insert(com);
        }
        catch (DataIntegrityViolationException exception) {
            if(exception.getCause().getCause().getMessage().contains("Нарушение уникального индекса или первичного ключ"))
                throw new RuntimeException(String.format(DUPLICATE_ERROR_STRING, com));
            else
                throw new RuntimeException(String.format(ERROR_STRING, com));
        }
    }

    @Override
    public long getCount() {
        return commentRepository.getCount();
    }

    @Override
    public List<Comment> getAll(int page, int amountByOnePage) {
        return commentRepository.getAll(page, amountByOnePage);
    }

    @Override
    public Comment findById(long id) {
        Comment com;
        try {
            com = commentRepository.findById(id);
        }
        catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Comment.class.getSimpleName()));
        }
        return com;
    }

    @Override
    public Comment update(long id, String comment, int bookId) {
        Comment com = new Comment(comment, bookService.findById(bookId));
        try {
            commentRepository.update(id, com);
        }
        catch (DataIntegrityViolationException exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(String.format(ERROR_STRING, Comment.class.getSimpleName()));
        }
        return com;
    }

    @Override
    public void delete(long id) {
        try {
            commentRepository.delete(id);
        }
        catch (DataIntegrityViolationException exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(String.format(ERROR_STRING, Comment.class.getSimpleName()));
        }
    }
}