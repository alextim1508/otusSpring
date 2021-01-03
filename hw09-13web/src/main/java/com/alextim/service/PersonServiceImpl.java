package com.alextim.service;


import com.alextim.domain.Person;
import com.alextim.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.alextim.service.Helper.*;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private AuthorRepository personRepository;

    @Transactional
    @Override
    public Person add(String name) {
        Person author = new Person(name);
        try {
            personRepository.save(author);
        } catch(DataIntegrityViolationException exception) {
            if(exception.getCause().getCause().getMessage().contains("Нарушение уникального индекса или первичного ключ"))
                throw new RuntimeException(String.format(DUPLICATE_ERROR_STRING, author));
            else
                throw new RuntimeException(String.format(ERROR_STRING, author));
        }
        return author;
    }

    @Transactional(readOnly = true)
    @Override
    public long getCount() {
        return personRepository.count();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Person> getAll(int page, int amountByOnePage) {
        return personRepository.findAll(PageRequest.of(page,amountByOnePage)).getContent();
    }

    @Transactional(readOnly = true)
    @Override
    public Person findById(long id) {
        Person person;
        try {
            person = personRepository.findById(id).orElseThrow(()->
                    new RuntimeException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING,  Person.class.getSimpleName(), id)));
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Person.class.getSimpleName()));
        }
        return person;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Person> find(String name) {
        List<Person> persons;
        try {
            persons = personRepository.findByName(name);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Person.class.getSimpleName()));
        }
        return persons;
    }

    @Transactional
    @Override
    public Person update(long id, String name) {
        Person person = findById(id);

        if(name != null)
            person.setName(name);

        try {
            personRepository.save(person);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Person.class.getSimpleName()));
        }
        return person;
    }

    @Transactional
    @Override
    public void delete(long id) {
        try {
            personRepository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException(String.format(ERROR_STRING, Person.class.getSimpleName()));
        }
    }
}