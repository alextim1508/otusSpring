package com.alextim.service;

import com.alextim.domain.Comment;
import com.alextim.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.alextim.service.Helper.*;

@Service
public class CommentServiceJpa implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookService bookService;

    @Transactional
    @Override
    public void add(String content, int bookId) {
        Comment comment = new Comment(content, bookService.findById(bookId));
        try {
            commentRepository.save(comment);
        } catch (DataIntegrityViolationException exception) {
            if(exception.getCause().getCause().getMessage().contains("Нарушение уникального индекса или первичного ключ"))
                throw new RuntimeException(String.format(DUPLICATE_ERROR_STRING, comment));
            else
                throw new RuntimeException(String.format(ERROR_STRING, comment));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public long getCount() {
        return commentRepository.count();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getAll(int page, int amountByOnePage) {
        return commentRepository.findAll(PageRequest.of(page,amountByOnePage)).getContent();
    }

    @Transactional(readOnly = true)
    @Override
    public Comment findById(long id) {
        Comment comment;
        try {
            comment = commentRepository.findById(id).orElseThrow(()->
                    new RuntimeException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Comment.class.getSimpleName(), id)));
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Comment.class.getSimpleName()));
        }
        return comment;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> find(String subTitle) {
        List<Comment> comments;
        try {
            comments = commentRepository.findByContentContaining(subTitle);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Comment.class.getSimpleName()));
        }
        return comments;
    }

    @Transactional
    @Override
    public Comment update(long id, String content) {
        Comment comment = findById(id);

        if(content != null)
            comment.setContent(content);
        try {
            commentRepository.save(comment);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Comment.class.getSimpleName()));
        }
        return comment;
    }

    @Transactional
    @Override
    public void delete(long id) {
        try {
            commentRepository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Comment.class.getSimpleName()));
        }
    }
}