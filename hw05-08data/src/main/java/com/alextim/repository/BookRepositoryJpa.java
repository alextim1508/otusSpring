package com.alextim.repository;


import com.alextim.domain.Book;
import com.alextim.domain.Comment;
import com.alextim.domain.Genre;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.alextim.domain.Book.FIELD_TITLE;

@SuppressWarnings("JpaQlInspection")
@Repository
public class BookRepositoryJpa implements BookRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void insert(Book book) {
        em.persist(book);
    }

    @Override
    public long getCount() {
        Query query = em.createQuery("select count(b) from Book b");
        return (long)query.getSingleResult();
    }

    @Override
    public List<Book> getAll(int page, int amountByOnePage) {
        if(amountByOnePage <= 0 || page <= 0)
            throw new IllegalArgumentException("Must be amountByOnePage > 0 and page > 0");

        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setFirstResult((page-1) * amountByOnePage);
        query.setMaxResults(amountByOnePage);
        return query.getResultList();
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findByTitle(String title) {
        TypedQuery<Book> query = em.createQuery(String.format("select b from Book b where %s = :title", FIELD_TITLE), Book.class);
        query.setParameter("title", title);
        return query.getResultList();
    }

    @Override
    public void update(Book book) {
        em.merge(book);
    }

    @Override
    public void delete(Book book) {
        em.remove(book);
    }
}