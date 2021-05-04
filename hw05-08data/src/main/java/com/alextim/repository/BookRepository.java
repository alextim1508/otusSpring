package com.alextim.repository;

import com.alextim.domain.Book;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.alextim.domain.Book.FIELD_AUTHOR;
import static com.alextim.domain.Book.FIELD_GENRE;

@Repository
public interface BookRepository extends MongoRepository<Book, ObjectId>, BookRepositoryCustom {

    @Query("{'" + FIELD_AUTHOR + ".$id' : ?0}")
    List<Book> findByAuthor(ObjectId authorId);

    @Query("{'" + FIELD_GENRE + ".$id' : ?0}")
    List<Book> findByGenre(ObjectId genreId);

    List<Book> findByTitleContaining(String sub);
}