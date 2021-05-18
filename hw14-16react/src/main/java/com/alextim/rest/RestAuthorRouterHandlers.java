package com.alextim.rest;

import com.alextim.domain.Author;
import com.alextim.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RestAuthorRouterHandlers {

    private final AuthorRepository repository;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok().body(repository.findAll(), Author.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        return ServerResponse.ok().body(repository.findById(request.pathVariable("id")), Author.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<Author> authorMono = request.body(BodyExtractors.toMono(Author.class)).flatMap(repository::save);
        return ServerResponse.ok().body(authorMono, Author.class);
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Mono<Author> authorMono = repository.findById(request.pathVariable("id"))
                .flatMap(author -> request.body(BodyExtractors.toMono(Author.class))
                        .flatMap(authorInRequestBody -> {
                            author.setFirstname(authorInRequestBody.getFirstname());
                            author.setLastname(authorInRequestBody.getLastname());
                            return repository.save(author);
                        }));
        return ServerResponse.ok().body(authorMono, Author.class);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        repository.findById(request.pathVariable("id")).flatMap(repository::delete).subscribe();
        return ServerResponse.ok().build();
    }
}