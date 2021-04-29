package com.alextim.repository.relation;

import com.alextim.domain.relational.AuthorRelation;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRelationRepository extends PagingAndSortingRepository<AuthorRelation, Long> {
}