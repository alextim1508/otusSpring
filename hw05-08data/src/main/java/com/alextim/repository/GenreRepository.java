package com.alextim.repository;

import com.alextim.domain.Genre;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends MongoRepository<Genre, ObjectId> {

    List<Genre> findByTitle(String title);
}