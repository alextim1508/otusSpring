package com.alextim.service;

import com.alextim.domain.nosql.AuthorNosql;
import com.alextim.domain.relational.AuthorRelation;

public class RelationToNosqlTransformer {
    public static AuthorNosql toNosqlAuthor(AuthorRelation author) {
        return new AuthorNosql(author.getFirstname(), author.getLastname());
    }
}