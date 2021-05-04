package com.alextim.repository;

import com.alextim.domain.Author;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends MongoRepository<Author, ObjectId> {

    List<Author> findByFirstnameOrLastname(String firstname, String lastname);
}
