package com.alextim.controller;


import com.alextim.domain.Person;
import com.alextim.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

import static javax.servlet.http.HttpServletResponse.*;

@RestController
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/person")
    public List<PersonDto> getAll(HttpServletResponse response) {
        List<PersonDto> authors = personService.getAll(0, Integer.MAX_VALUE).stream()
                .map(PersonDto::toDataTransferObject)
                .collect(Collectors.toList());
        response.setStatus(SC_OK);
        response.setContentType("application/json");
        return authors;
    }

    @PostMapping("/person/save/{id}")
    public PersonDto save(  @PathVariable("id") String id,
                            @RequestBody PersonDto personDto,
                            HttpServletResponse response) {
        Person authorDao = PersonDto.toDomainObject(personDto);
        Person updated = personService.update(Integer.parseInt(id), authorDao.getName());
        response.setStatus(SC_ACCEPTED );
        return PersonDto.toDataTransferObject(updated);
    }

    @PostMapping("/person/insert")
    public PersonDto insert(@RequestBody PersonDto personDto,
                            HttpServletResponse response) {
        Person authorDao = PersonDto.toDomainObject(personDto);
        Person inserted = personService.add(authorDao.getName());
        response.setStatus(SC_CREATED);
        return PersonDto.toDataTransferObject(inserted);
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, HttpServletResponse  response) {
        response.setStatus(SC_BAD_REQUEST);
        return ex.getMessage();
    }
}