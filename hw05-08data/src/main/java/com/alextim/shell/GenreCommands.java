package com.alextim.shell;


import com.alextim.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor @Slf4j
public class GenreCommands {

    private final GenreService service;

    @ShellMethod("add genre")
    public void addGenre(@ShellOption String title) {
        try {
            service.add(title);
            log.info("Genre added");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod("get genres count")
    public void getGenresCount() {
        try {
            log.info("Number of genres: {}", service.getCount());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod("get all genres")
    public void getAllGenres( @ShellOption int amountByOnePage) {
        long genresCount = service.getCount();
        int pages = (int)Math.ceil((double)genresCount/amountByOnePage);
        for(int page = 1; page<=pages; page++) {
            log.info("Page {}: {}", page, service.getAll(page, amountByOnePage));
        }
    }


    @ShellMethod("find genre by id")
    public void findGenreById(@ShellOption int id) {
        try {
            log.info("Genre with {} id: {}", id , service.findById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod("update genre")
    public void updateGenreById(@ShellOption int id, @ShellOption String title) {
        try {
            service.update(id, title);
            log.info("Genre updated");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod("delete genre")
    public void deleteGenreById(@ShellOption int id) {
        try {
            service.delete(id);
            log.info("Genre deleted");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}