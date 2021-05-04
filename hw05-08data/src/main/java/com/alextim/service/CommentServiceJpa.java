package com.alextim.service;

import com.alextim.domain.Book;
import com.alextim.domain.Comment;
import com.alextim.domain.Genre;
import com.alextim.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.alextim.service.HandlerException.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceJpa implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment add(String com) throws Exception {
        Comment comment = new Comment(com);
        try{
            commentRepository.save(comment);
        } catch (DataIntegrityViolationException exception) {
            handlerException(exception, comment.toString());
        }
        return comment;
    }

    @Override
    public long getCount() {
        return commentRepository.count();
    }

    @Override
    public List<Comment> getAll(int page, int amountByOnePage) {
        return commentRepository.findAll(PageRequest.of(page,amountByOnePage)).getContent();
    }

    @Override
    public Comment findById(ObjectId id) throws Exception {
        return commentRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Comment.class.getSimpleName(), id)));
    }

    @Override
    public List<Comment> find(String subTitle) throws Exception {
        List<Comment> comments = null;
        try {
            commentRepository.findByCommentContaining(subTitle).forEach(comments::add);
        } catch (DataIntegrityViolationException exception) {
            handlerException(exception, Genre.class.getSimpleName());
        }
        return comments;
    }

    @Override
    public Comment update(ObjectId id, String comm) throws Exception {
        Comment comment = commentRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Comment.class.getSimpleName(), id)));;
        if(comm!= null)
            comment.setComment(comm);
        try {
            commentRepository.save(comment);
        } catch (DataIntegrityViolationException exception) {
            handlerException(exception, Book.class.getSimpleName());
        }
        return comment;
    }

    @Override
    public void delete(ObjectId id) throws Exception {
        try {
            commentRepository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            handlerException(exception, Comment.class.getSimpleName());
        }
    }

    @Override
    public void deleteAll() {
        commentRepository.deleteAll();
    }
}