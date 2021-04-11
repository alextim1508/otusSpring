package com.alextim.controller;

import com.alextim.controller.dto.BookDto;
import com.alextim.controller.dto.MessageDto;
import com.alextim.domain.Book;
import com.alextim.service.working.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static javax.servlet.http.HttpServletResponse.*;

@RestController @RequestMapping("/book")
@RequiredArgsConstructor @Slf4j
public class BookController {

    private final BookService bookService;

    @PostMapping()
    public BookDto saveBook(@Valid @RequestBody BookDto bookDto,
                                BindingResult result,
                                HttpServletResponse response) {
        if(result.hasErrors()) {
            response.setStatus(SC_BAD_REQUEST);
            return null;
        }
        Book book = bookService.add(bookDto.getTitle(), bookDto.getAuthorId(), bookDto.getGenreId());
        response.setStatus(SC_CREATED);
        log.info("{} saved", book);
        return BookDto.toTransferObject(book);
    }

    @GetMapping()
    public List<BookDto> getAlBooks(@RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "amountByOnePage", defaultValue = "100") int amountByOnePage,
                                         HttpServletResponse response) {
        List<Book> books = bookService.getAll(page, amountByOnePage);
        response.setStatus(SC_OK);
        log.info("Books: {}", books);
        return books.stream().map(BookDto::toTransferObject).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable("id") int id,
                                   HttpServletResponse response) {
        Book bookById = bookService.findById(id);
        response.setStatus(SC_OK);
        log.info("Book: {}", bookById);
        return BookDto.toTransferObject(bookById);
    }

    @PutMapping("/{id}")
    public BookDto updateBooks(@PathVariable("id") int id,
                                    @RequestBody BookDto bookDto,
                                    HttpServletResponse response) {
        Book updated = bookService.update(id, bookDto.getTitle(), bookDto.getAuthorId(), bookDto.getGenreId());
        response.setStatus(SC_ACCEPTED);
        log.info("Book update: {}", updated);
        return BookDto.toTransferObject(updated);
    }

    @DeleteMapping("/{id}")
    public MessageDto deleteBookById(@PathVariable("id") int id,
                                     HttpServletResponse response) {
        bookService.delete(id);
        response.setStatus(SC_OK);
        log.info("Delete book with id {}", id);
        return new MessageDto(String.format("Delete book with id %d", id));
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, HttpServletResponse  response) {
        response.setStatus(SC_BAD_REQUEST);
        return ex.getMessage();
    }
}