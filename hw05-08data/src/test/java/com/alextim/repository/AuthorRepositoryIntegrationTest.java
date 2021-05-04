package com.alextim.repository;

import com.alextim.domain.Author;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@DataJpaTest
public class AuthorRepositoryIntegrationTest {

    @Autowired
    private static AuthorRepository authorRepository;

    private static ObjectId pushkinId;

    @BeforeAll
    public static void setUp(){
        pushkinId = authorRepository.save(new Author("Александр", "Пушкин")).getId();
        authorRepository.save(new Author("Михаил", "Лермонтов"));
        authorRepository.save(new Author("Максим", "Горький"));
        authorRepository.save(new Author("Николай", "Гоголь"));
        authorRepository.save(new Author("Николай", "Левашов"));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void getDuplicateException() {
        authorRepository.save(new Author("Александр", "Пушкин"));
    }

    @Test
    public void insertTest() {
        Author esenin = new Author("Сергей", "Есенин");
        authorRepository.save(esenin);

        List<Author> authors = authorRepository.findAll(PageRequest.of(0, 10)).getContent();;
        Assert.assertTrue(authors.contains(esenin));
    }

    @Test
    public void deleteTest() {
        Author gogol = authorRepository.findByFirstnameOrLastname(null, "Гоголь").get(0);
        authorRepository.delete(gogol);

        List<Author> authors = authorRepository.findAll(PageRequest.of(0, 10)).getContent();
        Assert.assertFalse(authors.contains(gogol));
    }

    @Test
    public void updateTest() {
        Author gorkiy = authorRepository.findByFirstnameOrLastname(null, "Горький").get(0);

        gorkiy.setFirstname("Михаил");
        gorkiy.setLastname("Лермонтов");

        authorRepository.save(gorkiy);

        List<Author> authors = authorRepository.findAll(PageRequest.of(0, 10)).getContent();

        Assert.assertTrue(authors.contains(new Author("Михаил", "Лермонтов")));
        Assert.assertFalse(authors.contains(new Author("Максим", "Горький")));
    }

    @Test
    public void findByIdTest() {
        Assert.assertEquals(new Author("Анри", "де Мопасан"), authorRepository.findById(pushkinId).get());
    }

    @Test
    public void findByLastnameTest() {
        List<Author> authors = authorRepository.findByFirstnameOrLastname(null, "Левашов");
        Assert.assertTrue(authors.contains(new Author("Николай", "Левашов")));
    }
}
