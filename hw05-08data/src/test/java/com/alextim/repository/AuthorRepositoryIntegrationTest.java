package com.alextim.repository;

import com.alextim.domain.Author;
import com.alextim.domain.Book;
import com.alextim.domain.Genre;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorRepositoryIntegrationTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;


    @Test(expected = DataIntegrityViolationException.class)
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

    @Test
    public void findByLastnameTest() {
        Author bulgakov = new Author("Михаил", "Булгаков");
        authorRepository.insert(bulgakov);

        List<Author> authors = authorRepository.findByLastname("Булгаков");
        Assert.assertEquals(1, authors.size());
        Assert.assertTrue(authors.contains(bulgakov));
    }

    @Test
    public void getBooksByAuthorTest() {
        Author pelevin = new Author("Виктор", "Пелевин");
        authorRepository.insert(pelevin);

        Genre roman = genreRepository.findByTitle("Роман").get(0);

        Book book1 = new Book("Empire V", pelevin, roman);
        bookRepository.insert(book1);
        Book book2 = new Book("Священная книга оборотня", pelevin, roman);
        bookRepository.insert(book2);
        Book book3 = new Book(" Generation П", pelevin, roman);
        bookRepository.insert(book3);


        List<Book> books = authorRepository.getBooks(pelevin.getId());

        Assert.assertEquals(3, books.size());
        Assert.assertTrue(books.contains(book1));
        Assert.assertTrue(books.contains(book2));
        Assert.assertTrue(books.contains(book3));
    }
}
