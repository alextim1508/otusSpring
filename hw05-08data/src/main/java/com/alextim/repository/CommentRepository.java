package com.alextim.repository;


import com.alextim.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    void insert(Comment comment);

    long getCount();
    List<Comment> getAll(int page, int amountByOnePage);

    Optional<Comment> findById(long id);

    void update(Comment comment);

    void delete(Comment comment);
}