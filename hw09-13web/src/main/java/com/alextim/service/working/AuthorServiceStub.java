package com.alextim.service.working;

import com.alextim.domain.Author;
import com.alextim.domain.Book;
import com.alextim.domain.Genre;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
public class AuthorServiceStub {

    private final static String SUFFIX = "_STUB";

    protected Author addStub(String firstname, String lastname) {
        log.warn("stub method call: {}", "addStub");
        return new Author(firstname + SUFFIX, lastname + SUFFIX);
    }

    protected long getCountStub() {
        log.warn("stub method call: {}", "getCountStub");
        return -1;
    }

    protected List<Author> getAllStub (int page, int amountByOnePage) {
        log.warn("stub method call: {}", "getAllStub");
        return Collections.singletonList(new Author("author" + SUFFIX, "author" + SUFFIX));
    }

    protected Author findByIdStub(long id) {
        log.warn("stub method call: {}", "findByIdStub");
        return new Author("author" + SUFFIX, "author" + SUFFIX);
    }

    protected List<Author> findStub(String firstname, String lastname) {
        log.warn("stub method call: {}", "findStub");
        return Collections.singletonList(new Author(firstname + SUFFIX, lastname + SUFFIX));
    }

    protected List<Book> getBooksStub(long idAuthor) {
        log.warn("stub method call: {}", "getBooksStub");
        return Collections.singletonList(new Book("book" + SUFFIX, new Author("author" + SUFFIX, "author" + SUFFIX), new Genre("genre" +SUFFIX)));
    }

    protected Author updateStub(long id, String firstname, String lastname) {
        log.warn("stub method call: {}", "updateStub");
        return new Author(firstname + SUFFIX, lastname + SUFFIX);
    }

    protected void deleteStub(long id) {
        log.warn("stub method call: {}", "deleteStub");
    }
}
