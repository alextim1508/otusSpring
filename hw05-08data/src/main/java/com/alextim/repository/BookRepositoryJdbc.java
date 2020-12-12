package com.alextim.repository;

import com.alextim.domain.Author;
import com.alextim.domain.Book;
import com.alextim.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository @RequiredArgsConstructor
public class BookRepositoryJdbc implements BookRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    @Override
    public void insert(Book book) {
        final Map<String, Object> params = new HashMap<>(3);
        params.put("title", book.getTitle());
        params.put("authorId", book.getAuthor().getId());
        params.put("genreId", book.getGenre().getId());
        jdbcOperations.update("insert books(title author_id, genre_id) values (:title, :authorId, :genreId)",
                params);
    }

    @Override
    public long getCount() {
        return jdbcOperations.getJdbcOperations().queryForObject("select count(*) from books;", Integer.class);
    }

    @Override
    public List<Book> getAll(int page, int amountByOnePage) {
        final Map<String, Object> params = new HashMap<>(2);
        params.put("low_limit", amountByOnePage * (page - 1));
        params.put("amount", amountByOnePage);
        return jdbcOperations.query("select * from books order by id limit :low_limit, :amount", params, new BookMapper());
    }

    @Override
    public Book findById(long id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbcOperations.queryForObject("select * from books where id = :id",
                params,
                new BookMapper());
    }


    @Override
    public void update(long id, Book book) {
        final Map<String, Object> params = new HashMap<>(4);
        params.put("idBook", id);
        params.put("titleBook", book.getTitle());
        params.put("authorId", book.getAuthor().getId());
        params.put("genreId", book.getGenre().getId());

        jdbcOperations.update("update books set title = :titleBook, author_id = :authorId, genre_id = :genreId where id = :idBook",
                params);
    }

    @Override
    public void delete(long id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        jdbcOperations.update("delete from books where id = :id", params);
    }


    private class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            int authorId = resultSet.getInt("author_id");
            int genreId = resultSet.getInt("genre_id");

            Author author = authorRepository.findById(authorId);
            Genre genre = genreRepository.findById(genreId);
            Book book = new Book(title, author, genre);
            book.setId(id);

            return book;
        }
    }
}
