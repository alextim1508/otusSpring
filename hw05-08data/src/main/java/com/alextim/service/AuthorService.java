package com.alextim.service;


import com.alextim.domain.Author;
import org.bson.types.ObjectId;

import java.util.List;

public interface AuthorService {

    Author add(String firsname, String lastname) throws Exception;
    long getCount();
    List<Author> getAll(int page, int amountByOnePage);
    Author findById(ObjectId id) throws Exception;
    List<Author> find(String firstname, String lastname) throws Exception;
    Author update(ObjectId id, String firstname, String lastname) throws Exception;
    void delete(ObjectId id) throws Exception;
    void deleteAll();
}
