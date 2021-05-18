package com.alextim.repository;


import com.alextim.domain.Comment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookRepositoryCustom {
    Mono<Void> setComment(String id, Comment comment);
    Flux<Comment> getComments(String id);
}