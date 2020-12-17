package com.alextim.repository;


import com.alextim.domain.Genre;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

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
        return (long) query.getSingleResult();
    }

    @Override
    public List<Genre> getAll(int page, int amountByOnePage) {
        if(amountByOnePage <= 0 || page <=0)
            throw new IllegalArgumentException("Must be amountByOnePage > 0 and page > 0");

        TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
        query.setFirstResult((page-1) * amountByOnePage);
        query.setMaxResults(amountByOnePage);
        return query.getResultList();
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public List<Genre> findByTitle(String title) {
        TypedQuery<Genre> query = em.createQuery(String.format("select g from Genre g where %s = :title", FIELD_TITLE), Genre.class);
        query.setParameter("title", title);
        return query.getResultList();
    }

    @Override
    public void update(Genre genre) {
        em.merge(genre);
    }

    @Override
    public void delete(Genre genre) {
        em.remove(genre);
    }
}