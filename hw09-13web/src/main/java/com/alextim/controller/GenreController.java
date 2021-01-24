package com.alextim.controller;

import com.alextim.controller.dto.BookDto;
import com.alextim.controller.dto.GenreDto;
import com.alextim.controller.dto.MessageDto;
import com.alextim.domain.Book;
import com.alextim.domain.Genre;
import com.alextim.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@RestController @RequestMapping("/genre")
@RequiredArgsConstructor @Slf4j
public class GenreController {

    private final GenreService genreService;

    @PostMapping()
    public GenreDto saveGenre(@Valid @RequestBody GenreDto genreDto, BindingResult result,
                                 HttpServletResponse response) {
        if(result.hasErrors()) {
            response.setStatus(SC_BAD_REQUEST);
            return null;
        }

        Genre genre = genreService.add(genreDto.getTitle());
        response.setStatus(SC_OK);
        log.info("{} saved", genre);
        return GenreDto.toTransferObject(genre);
    }

    @GetMapping()
    public List<GenreDto> getAllGenres(@RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "amountByOnePage", defaultValue = "100") int amountByOnePage,
                                        HttpServletResponse response) {
        List<Genre> genres = genreService.getAll(page, amountByOnePage);
        response.setStatus(SC_OK);
        log.info("Genre: {}", genres);
        return genres.stream().map(GenreDto::toTransferObject).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public GenreDto getGenreById(@PathVariable("id") int id,
                                   HttpServletResponse response) {
        Genre genreById = genreService.findById(id);
        response.setStatus(SC_OK);
        log.info("Genre: {}", genreById);
        return GenreDto.toTransferObject(genreById);
    }

    @GetMapping("/books/{genreId}")
    public List<BookDto> getBooksByAuthorId(@PathVariable("genreId") int genreId,
                                            HttpServletResponse response) {
        List<Book> books = genreService.getBooks(genreId);
        response.setStatus(SC_OK);
        log.info("Founded genres by author id: {}", books);
        return books.stream().map(BookDto::toTransferObject).collect(Collectors.toList());
    }

    @GetMapping("/find")
    public List<GenreDto> findGenres(@RequestParam(name = "title", defaultValue = "") String title,
                                       HttpServletResponse response) {
        List<Genre> founded = new ArrayList<>();
        if(!title.isEmpty()) {
            founded.addAll(genreService.find(title));
        }
        response.setStatus(SC_OK);
        log.info("Founded genres: {}", founded);
        return founded.stream().map(GenreDto::toTransferObject).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public GenreDto updateGenre(@PathVariable("id") int id,
                                   @RequestBody GenreDto genreDto,
                                   HttpServletResponse response) {
        Genre updated = genreService.update(id, genreDto.getTitle());
        response.setStatus(SC_OK);
        log.info("Genre update: {}", updated);
        return GenreDto.toTransferObject(updated);
    }

    @DeleteMapping("/{id}")
    public MessageDto deleteGenreById(@PathVariable("id") int id,
                                       HttpServletResponse response) {
        genreService.delete(id);
        response.setStatus(SC_OK);
        log.info("Delete genre with id {}", id);
        return new MessageDto(String.format("Delete genre with id %d", id));
    }
}