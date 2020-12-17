package com.alextim.repository;


import com.alextim.domain.Comment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("JpaQlInspection")
@Repository
public class CommentRepositoryJpa implements CommentRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void insert(Comment comment) {
        em.persist(comment);
    }

    @Override
    public long getCount() {
        Query query = em.createQuery("select count(c) from Comment c");
        return (long)query.getSingleResult();
    }

    @Override
    public List<Comment> getAll(int page, int amountByOnePage) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c", Comment.class);
        query.setFirstResult((page-1) * amountByOnePage);
        query.setMaxResults(amountByOnePage);
        return query.getResultList();
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public void update(Comment comment) {
        em.merge(comment);
    }

    @Override
    public void delete(Comment comment) {
        em.remove(comment);
    }
}