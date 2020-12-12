package com.alextim.repository;

import com.alextim.domain.Author;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorRepositoryIntegrationTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test(expected = DuplicateKeyException.class)
    public void getDuplicateException() {
        authorRepository.insert(new Author("Александр", "Пушкин"));
        authorRepository.insert(new Author("Александр", "Пушкин"));
    }

    @Test
    public void insertAndDeleteTest() {
        Author esenin = new Author("Сергей", "Есенин");
        authorRepository.insert(esenin);

        List<Author> authors = authorRepository.getAll(1, 10);
        long lastId = authors.get(authors.size()-1).getId();
        Assert.assertTrue(authors.contains(esenin));

        authorRepository.delete(lastId);
        authors = authorRepository.getAll(1, 10);
        Assert.assertFalse(authors.contains(esenin));
    }

    @Test
    public void insertAndUpdateTest() {
        Author dostoevsky = new Author("Федор", "Достоевский");
        authorRepository.insert(dostoevsky);

        List<Author> authors = authorRepository.getAll(1, 10);
        Assert.assertTrue(authors.contains(dostoevsky));

        Author lermontov = new Author("Михаил", "Лермонтов");
        long lastId = authors.get(authors.size()-1).getId();
        authorRepository.update(lastId, lermontov);

        authors = authorRepository.getAll(1, 10);

        Assert.assertTrue(authors.contains(lermontov));
        Assert.assertFalse(authors.contains(dostoevsky));
    }

    @Test
    public void findByIdTest() {
        Author turgenev = new Author("Иван", "Тургенев");
        authorRepository.insert(turgenev);
        List<Author> authors = authorRepository.getAll(1, 10);
        Assert.assertTrue(authors.contains(turgenev));

        long lastId = authors.get(authors.size()-1).getId();
        Assert.assertEquals(turgenev, authorRepository.findById(lastId));
    }
}
