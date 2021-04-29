package com.alextim.service;

import com.alextim.domain.nosql.AuthorNosql;
import com.alextim.domain.relational.AuthorRelation;

public class NosqlToRelationTransformer {
    public static AuthorRelation toRelationAuthor(AuthorNosql author) {
        return new AuthorRelation(author.getFirstname(), author.getLastname());
    }
}
