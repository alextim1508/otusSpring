package com.alextim.shell;

import com.alextim.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor @Slf4j
public class BookCommands {

    private final BookService service;

    @ShellMethod("add genre")
    public void addBook(@ShellOption String title,
                        @ShellOption String authorId,
                        @ShellOption String genreId) {
        try {
            service.add(title, new ObjectId(authorId), new ObjectId(genreId));
            log.info("Book added");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod("get books count")
    public void getBooksCount() {
        try {
            log.info("Number of books: {}", service.getCount());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod("get books count")
    public void getAllBooks( @ShellOption int amountByOnePage) {
        long booksCount = service.getCount();
        int pages = (int)Math.ceil((double)booksCount/amountByOnePage);
        for(int page = 1; page<=pages; page++) {
            log.info("Page {}: {}", page, service.getAll(page, amountByOnePage));
        }
    }

    @ShellMethod("find book by id")
    public void findBookById(@ShellOption String id) {
        try {
            log.info("Book with {} id: {}", id , service.findById(new ObjectId(id)));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod("update genre")
    public void updateBookById(@ShellOption String id,
                               @ShellOption String title,
                               @ShellOption String  authorId,
                               @ShellOption String genreId) {
        try {
            service.update(new ObjectId(id), title, new ObjectId(authorId), new ObjectId(genreId));
            log.info("Book updated");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod("delete book")
    public void deleteBookById(@ShellOption String id) {
        try {
            service.delete(new ObjectId(id));
            log.info("Book deleted");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}