package com.alextim.repository;


import com.alextim.domain.Author;
import com.alextim.domain.Book;
import com.alextim.domain.Genre;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static com.alextim.domain.Author.FIELD_LASTNAME;
import static com.alextim.domain.Genre.FIELD_TITLE;

@SuppressWarnings("JpaQlInspection")
@Repository
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void insert(Genre genre) {
        em.persist(genre);
    }

    @Override
    public long getCount() {
        Query query = em.createQuery("select count(g) from Genre g");
        return (Long) query.getSingleResult();
    }

    @Override
    public List<Genre> getAll(int page, int amountByOnePage) {
        if(amountByOnePage <= 0 || page <=0)
            throw new IllegalArgumentException("Must be amountByOnePage > 0 and page > 0");

        TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
        query.setFirstResult((--page) * amountByOnePage);
        query.setMaxResults(amountByOnePage);
        return query.getResultList();
    }

    @Override
    public Genre findById(long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public List<Genre> findByTitle(String title) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where "+ FIELD_TITLE + " = :title", Genre.class);
        query.setParameter("title", title);
        return query.getResultList();
    }

    @Transactional
    @Override
    public List<Book> getBooks(long id) {
        return new ArrayList<>(em.find(Genre.class, id).getBooks());
    }

    @Transactional
    @Override
    public void update(long id, Genre genre) {
        Genre byId = findById(id);
        byId.setTitle(genre.getTitle());
        em.merge(byId);
    }

    @Transactional
    @Override
    public void delete(long id) {
        Query query = em.createQuery("delete from Genre g where g.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}