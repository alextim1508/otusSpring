package com.alextim.service;

import com.alextim.domain.Comment;

import java.util.List;

public interface CommentService {
    void add(String comment, int bookId);

    long getCount();
    List<Comment> getAll(int page, int amountByOnePage);

    Comment findById(long id);

    Comment update(long id, String comment, int bookId);

    void delete(long id);
}