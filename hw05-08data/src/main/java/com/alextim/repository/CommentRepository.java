package com.alextim.repository;

import com.alextim.domain.Comment;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
    List<Comment> findByContentContaining(String sub);
}