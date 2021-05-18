package com.alextim.repository;

import com.alextim.domain.Book;
import com.alextim.domain.Comment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String>, BookRepositoryCustom {

    @Override
    Mono<Void> setComment(String id, Comment comment);

    @Override
    Flux<Comment> getComments(String id);
}