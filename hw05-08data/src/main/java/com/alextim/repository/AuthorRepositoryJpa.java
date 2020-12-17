package com.alextim.repository;


import com.alextim.domain.Author;
import com.alextim.domain.Book;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.alextim.domain.Author.FIELD_LASTNAME;


@SuppressWarnings("JpaQlInspection")
@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void insert(Author author) {
        em.persist(author);
    }

    @Override
    public long getCount() {
        Query query = em.createQuery("select count(a) from Author a");
        return (long)query.getSingleResult();
    }

    @Override
    public List<Author> getAll(int page, int amountByOnePage) {
        if(amountByOnePage <= 0 || page <= 0)
            throw new IllegalArgumentException("Must be amountByOnePage > 0 and page > 0");

        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        query.setFirstResult((page-1) * amountByOnePage);
        query.setMaxResults(amountByOnePage);
        return query.getResultList();
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> findByLastname(String lastname) {
        TypedQuery<Author> query = em.createQuery(String.format("select a from Author a where %s = :lastname", FIELD_LASTNAME), Author.class);
        query.setParameter("lastname", lastname);
        return query.getResultList();
    }

    @Override
    public void update(Author author) {
        em.merge(author);
    }


    @Override
    public void delete(Author author) {
        em.remove(author);
    }
}