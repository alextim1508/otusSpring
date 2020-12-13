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

import static com.alextim.domain.Author.FIELD_LASTNAME;


@SuppressWarnings("JpaQlInspection")
@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
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
        query.setFirstResult((--page) * amountByOnePage);
        query.setMaxResults(amountByOnePage);
        return query.getResultList();
    }

    @Override
    public Author findById(long id) {
        return em.find(Author.class, id);
    }

    @Override
    public List<Author> findByLastname(String lastname) {
        TypedQuery<Author> query = em.createQuery("select a from Author a where " + FIELD_LASTNAME + " = :lastname", Author.class);
        query.setParameter("lastname", lastname);
        return query.getResultList();
    }

    @Transactional
    @Override
    public List<Book> getBooks(long id) {
        return new ArrayList<>(em.find(Author.class, id).getBooks());
    }

    @Override
    @Transactional
    public void update(long id, Author author) {
        Author byId = findById(id);
        byId.setFirstname(author.getFirstname());
        byId.setLastname(author.getLastname());
        em.merge(byId);
    }

    @Transactional
    @Override
    public void delete(long id) {
        Query query = em.createQuery("delete from Author a where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}