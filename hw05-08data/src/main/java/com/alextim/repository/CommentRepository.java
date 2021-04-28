package com.alextim.repository;

import com.alextim.domain.Comment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
    List<Comment> findByContentContaining(String sub);
}