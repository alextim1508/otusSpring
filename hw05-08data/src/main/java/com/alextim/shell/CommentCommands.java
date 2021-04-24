package com.alextim.shell;

import com.alextim.service.AuthorService;
import com.alextim.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor @Slf4j
public class CommentCommands {

    private final CommentService service;

    @ShellMethod("add comment")
    public void addComment(@ShellOption String comment, @ShellOption int bookId) {
        try {
            service.add(comment, bookId);
            log.info("Comment added");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod("get comment count")
    public void getCommentCount() {
        try {
            log.info("Number of comments: {}", service.getCount());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod("get all comments")
    public void getAllComment(@ShellOption int amountByOnePage) {
        long authorsCount = service.getCount();
        int pages = (int) Math.ceil((double) authorsCount / amountByOnePage);
        for (int page = 1; page <= pages; page++) {
            log.info("Page {}: {}", page, service.getAll(page, amountByOnePage));
        }
    }

    @ShellMethod("find comment by id")
    public void findCommentById(@ShellOption int id) {
        try {
            log.info("Comment with {} id: {}", id , service.findById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod("update comment")
    public void updateComment(@ShellOption int id,
                             @ShellOption String comment) {
        try {
            service.update(id, comment);
            log.info("Comment updated");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod("delete comment")
    public void deleteComment(@ShellOption int id) {
        try {
            service.delete(id);
            log.info("Comment deleted");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
