package com.alextim.repository;

import com.alextim.domain.Author;
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
public class AuthorRepositoryJdbc implements AuthorRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public void insert(Author author) {
        final Map<String, Object> params = new HashMap<>(2);
        params.put("firstname", author.getFirstname());
        params.put("lastname", author.getLastname());
        jdbcOperations.update("insert into authors (firstname, lastname) values (:firstname, :lastname);",
                params);

    }

    @Override
    public long getCount() {
        return jdbcOperations.getJdbcOperations().queryForObject("select count(*) from authors;", Integer.class);
    }

    @Override
    public List<Author> getAll(int page, int amountByOnePage) {
        final Map<String, Object> params = new HashMap<>(2);
        params.put("low_limit", amountByOnePage * (page-1));
        params.put("amount", amountByOnePage);
        return jdbcOperations.query("select * from authors order by id limit :low_limit, :amount", params, new AuthorMapper());
    }

    @Override
    public Author findById(long id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbcOperations.queryForObject("select * from authors where id = :id", params, new AuthorMapper());
    }

    @Override
    public void update(long id, Author author) {
        final Map<String, Object> params = new HashMap<>(3);
        params.put("id", id);
        params.put("firstname", author.getFirstname());
        params.put("lastname", author.getLastname());

        jdbcOperations.update("update authors set firstname = :firstname,  lastname = :lastname where id = :id", params);
    }

    @Override
    public void delete(long id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        jdbcOperations.update("delete from authors where id = :id", params);
    }

    private class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            int id              = resultSet.getInt("id");
            String firstname    = resultSet.getString("firstname");
            String lastname     = resultSet.getString("lastname");
            Author author = new Author(firstname, lastname);
            author.setId(id);
            return author;
        }
    }
}
