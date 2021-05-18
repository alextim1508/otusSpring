package com.alextim.repository;

import com.alextim.domain.Book;
import com.alextim.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepositoryCustom {

    private final ReactiveMongoOperations operations;

    @Override
    public Mono<Void> setComment(String id, Comment comment) {
        operations.updateFirst(Query.query(Criteria.where("_id").is(id)), new Update().push("comments", comment), Book.class);
        return Mono.empty();
    }

    @Override
    public Flux<Comment> getComments(String id) {
        Mono<Book> bookMono = operations.findOne(Query.query(Criteria.where("_id").is(id)), Book.class);
        return bookMono.flatMapIterable(Book::getComments);
    }
}
