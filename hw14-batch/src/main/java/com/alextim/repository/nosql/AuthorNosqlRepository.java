package com.alextim.repository.nosql;

import com.alextim.domain.nosql.AuthorNosql;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorNosqlRepository extends MongoRepository<AuthorNosql, ObjectId> {
}
