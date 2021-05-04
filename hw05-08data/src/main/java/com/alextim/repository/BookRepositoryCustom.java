package com.alextim.repository;

import com.alextim.domain.Comment;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepositoryCustom {
    void addComment(ObjectId bookId, Comment comment);
}