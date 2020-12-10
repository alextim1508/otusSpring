package com.alextim.repository;

import com.alextim.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository @RequiredArgsConstructor
public class GenreRepositoryJdbc implements GenreRepository {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public void insert(Genre genre) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("title", genre.getTitle());
        jdbc.update("insert genres (title) values (:title)", params);
    }

    @Override
    public long getCount() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from genres;", Integer.class);
    }

    @Override
    public List<Genre> getAll(int page, int amountByOnePage) {
        final Map<String, Object> params = new HashMap<>(2);
        params.put("low_limit", amountByOnePage * (page - 1));
        params.put("amount", amountByOnePage);
        return jdbc.query("select * from genres order by id limit :low_limit, :amount", params, new GenreMapper());
    }

    @Override
    public Genre findById(long id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbc.queryForObject("select * from genres where id = :id", params, new GenreMapper());
    }

    @Override
    public void update(long id, Genre genre) {
        final Map<String, Object> params = new HashMap<>(2);
        params.put("id", id);
        params.put("title", genre.getTitle());
        jdbc.update("update genres set title = :title where id = :id", params);
    }

    @Override
    public void delete(long id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        jdbc.update("delete from genres where id = id = :id", params);
     }

    private class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            Genre genre = new Genre(title);
            genre.setId(id);
            return genre;
        }
    }
}
