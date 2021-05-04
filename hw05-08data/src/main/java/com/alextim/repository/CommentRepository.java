package com.alextim.repository;

import com.alextim.domain.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, ObjectId> {

    List<Comment> findByCommentContaining(String sub);
}