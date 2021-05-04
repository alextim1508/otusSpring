package com.alextim.service;

import com.alextim.domain.Book;
import com.alextim.domain.Genre;
import org.bson.types.ObjectId;

import java.util.List;


public interface GenreService {
    Genre add(String title) throws Exception;
    long getCount();
    List<Genre> getAll(int page, int amountByOnePage);
    Genre findById(ObjectId id) throws Exception;
    List<Genre> find(String title) throws Exception;
    Genre update(ObjectId id, String title) throws Exception;
    void delete(ObjectId id) throws Exception;
    void deleteAll();
}
