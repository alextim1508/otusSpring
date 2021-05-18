package com.alextim.rest;


import com.alextim.domain.Author;
import com.alextim.repository.AuthorRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestAuthorTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    protected AuthorRepository repository;

    @Test
    public void findAllTest() {
        Author author1 = new Author("Sergey", "Esenin");
        Author author2 = new Author("Leo", "Tolstoy");

        given(repository.findAll()).willReturn(Flux.just(author1, author2));

        EntityExchangeResult<List<Author>> result = webTestClient.get()
                .uri("/rest/authors")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Author.class)
                .returnResult();

        Objects.requireNonNull(result.getResponseBody()).forEach(System.out::println);
    }

    @Test
    public void findByIdTest() {
        Author author = new Author("Sergey", "Esenin");

        given(repository.findById("1")).willReturn(Mono.just(author));

        EntityExchangeResult<Author> result = webTestClient.get()
                .uri("/rest/authors/" + author.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(Author.class)
                .returnResult();

        System.out.println(result.getResponseBody());
    }

    @Test
    public void saveTest() {
        Author authorNew = new Author("Sergey", "Esenin");
        Author authorSaved = new Author("Alex", "Pushkin");

        given(repository.save(authorNew)).willReturn(Mono.just(authorSaved));

        EntityExchangeResult<Author> result = webTestClient.post()
                .uri("/rest/authors")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(authorNew), Author.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(Author.class)
                .consumeWith(response -> {
                    Assertions.assertThat(response.getResponseBody()).isEqualTo(authorSaved);
                })
                .returnResult();

        System.out.println(result.getResponseBody());
    }

    @Test
    public void updateTest() {
        Author author = new Author("Sergey", "Esenin");
        Author authorUpdated = new Author("Alex", "Pushkin");

        given(repository.findById("1")).willReturn(Mono.just(author));
        given(repository.save(authorUpdated)).willReturn(Mono.just(authorUpdated));

        EntityExchangeResult<Author> result = webTestClient.put()
                .uri("/rest/authors/" + author.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(authorUpdated), Author.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(Author.class)
                .consumeWith(response -> Assertions.assertThat(response.getResponseBody()).isEqualTo(authorUpdated))
                .returnResult();
    }

    @Test
    public void deleteTest() {
        Author author = new Author("Sergey", "Esenin");

        given(repository.findById(author.getId())).willReturn(Mono.just(author));
        given(repository.delete(author)).willReturn(Mono.empty());

        webTestClient.delete()
                .uri("/rest/authors/" + author.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk();
    }
}
