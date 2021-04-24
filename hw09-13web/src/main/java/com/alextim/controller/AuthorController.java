package com.alextim.controller;

import com.alextim.controller.dto.AuthorDto;
import com.alextim.controller.dto.BookDto;
import com.alextim.controller.dto.Dto;
import com.alextim.controller.dto.MessageDto;
import com.alextim.domain.Author;
import com.alextim.domain.Book;
import com.alextim.service.working.AuthorService;
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

@RestController @RequestMapping("/author")
@RequiredArgsConstructor @Slf4j
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping()
    public Dto savAuthor(@Valid @RequestBody AuthorDto authorDto,
                         BindingResult result,
                         HttpServletResponse response) {
        if(result.hasErrors()) {
            response.setStatus(SC_BAD_REQUEST);
            return new MessageDto("input data error");
        }

        Author author = authorService.add(authorDto.getFirstname(), authorDto.getLastname());
        response.setStatus(SC_OK);
        log.info("{} saved", author);
        return AuthorDto.toTransferObject(author);
    }

    @GetMapping()
    public List<AuthorDto> getAllAuthors(@RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "amountByOnePage", defaultValue = "100") int amountByOnePage,
                                         HttpServletResponse response) {
        List<Author> authors = authorService.getAll(page, amountByOnePage);
        response.setStatus(SC_OK);
        log.info("Author: {}", authors);
        return authors.stream().map(AuthorDto::toTransferObject).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AuthorDto getAuthorById(@PathVariable("id") int id,
                                 HttpServletResponse response) {
        Author authorById = authorService.findById(id);
        response.setStatus(SC_OK);
        log.info("Author: {}", authorById);
        return AuthorDto.toTransferObject(authorById);
    }

    @GetMapping("/books/{authorId}")
    public List<BookDto> getBooksByAuthorId(@PathVariable("authorId") int authorId,
                                     HttpServletResponse response) {
        List<Book> books = authorService.getBooks(authorId);
        response.setStatus(SC_OK);
        log.info("Founded books by author id: {}", books);
        return books.stream().map(BookDto::toTransferObject).collect(Collectors.toList());
    }

    @GetMapping("/find")
    public List<AuthorDto> findAuthors(@RequestParam(name = "title", defaultValue = "null") String firstname,
                                       @RequestParam(name = "title", defaultValue = "null") String lastname,
                                       HttpServletResponse response) {
        List<Author> founded = new ArrayList<>();
        founded.addAll(authorService.find(firstname, lastname));
        response.setStatus(SC_OK);
        log.info("Founded authors: {}", founded);
        return founded.stream().map(AuthorDto::toTransferObject).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public AuthorDto updateAuthors(@PathVariable("id") int id,
                                    @RequestBody AuthorDto authorDto,
                                    HttpServletResponse response) {
        Author updated = authorService.update(id, authorDto.getFirstname(), authorDto.getLastname());
        response.setStatus(SC_OK);
        log.info("Author update: {}", updated);
        return AuthorDto.toTransferObject(updated);
    }

    @DeleteMapping("/{id}")
    public MessageDto deleteAuthorById(@PathVariable("id") int id,
                                      HttpServletResponse response) {
        authorService.delete(id);
        response.setStatus(SC_OK);
        log.info("Delete author with id {}", id);
        return new MessageDto(String.format("Delete author with id %d", id));
    }
}