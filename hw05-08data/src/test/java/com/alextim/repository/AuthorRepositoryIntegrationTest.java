package com.alextim.repository;

import com.alextim.domain.Author;
import com.alextim.domain.Book;
import com.alextim.domain.Genre;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@DataJpaTest
@Import({AuthorRepositoryJpa.class, GenreRepositoryJpa.class, BookRepositoryJpa.class})
public class AuthorRepositoryIntegrationTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;


    @Test(expected = PersistenceException.class)
    public void getDuplicateException() {
        authorRepository.insert(new Author("Александр", "Пушкин"));
    }

    @Test
    public void insertTest() {
        Author esenin = new Author("Сергей", "Есенин");
        authorRepository.insert(esenin);

        List<Author> authors = authorRepository.getAll(1, 10);
        Assert.assertTrue(authors.contains(esenin));
    }

    @Test
    public void deleteTest() {
        Author pushkin = authorRepository.findByLastname("Пушкин").get(0);
        authorRepository.delete(pushkin);

        List<Author> authors = authorRepository.getAll(1, 10);
        Assert.assertFalse(authors.contains(pushkin));
    }

    @Test
    public void updateTest() {
        Author gorkiy = authorRepository.findByLastname("Горький").get(0);

        gorkiy.setFirstname("Михаил");
        gorkiy.setLastname("Лермонтов");

        authorRepository.update(gorkiy);

        List<Author> authors = authorRepository.getAll(1, 10);

        Assert.assertTrue(authors.contains(new Author("Михаил", "Лермонтов")));
        Assert.assertFalse(authors.contains(new Author("Максим", "Горький")));
    }

    @Test
    public void findByIdTest() {
        Assert.assertEquals(new Author("Анри", "де Мопасан"), authorRepository.findById(5).get());
    }

    @Test
    public void findByLastnameTest() {
        List<Author> authors = authorRepository.findByLastname("Левашов");
        Assert.assertTrue(authors.contains(new Author("Николай", "Левашов")));
    }

    @Test
    public void getBooksByAuthorTest() {
        Author pelevin = authorRepository.findById(8).orElseThrow(() ->
                new RuntimeException("Not found"));

        List<Book> books = pelevin.getBooks();
        Assert.assertEquals(3, books.size());
    }
}
