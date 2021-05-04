package com.alextim.shell;

import com.alextim.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor @Slf4j
public class AuthorCommands {

    private final AuthorService service;

    @ShellMethod("add author")
    public void addAuthor(@ShellOption String firsname, @ShellOption String lastname) {
        try {
            service.add(firsname, lastname);
            log.info("Author added");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod("get author count")
    public void getAuthorCount() {
        try {
            log.info("Number of authors: {}", service.getCount());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod("get all authors")
    public void getAllAuthors(@ShellOption int amountByOnePage) {
        long authorsCount = service.getCount();
        int pages = (int) Math.ceil((double) authorsCount / amountByOnePage);
        for (int page = 1; page <= pages; page++) {
            log.info("Page {}: {}", page, service.getAll(page, amountByOnePage));
        }
    }

    @ShellMethod("find author by id")
    public void findAuthorById(@ShellOption String id) {
        try {
            log.info("Authors with {} id: {}", id , service.findById(new ObjectId(id)));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }



    @ShellMethod("update author")
    public void updateAuthor(@ShellOption String id,
                             @ShellOption String firstname,
                             @ShellOption String lastname) {
        try {
            service.update(new ObjectId(id), firstname, lastname);
            log.info("Author updated");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod("delete author")
    public void deleteAuthor(@ShellOption String id) {
        try {
            service.delete(new ObjectId(id));
            log.info("Author deleted");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}