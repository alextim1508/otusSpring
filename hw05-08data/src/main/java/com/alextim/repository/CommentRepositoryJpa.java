package com.alextim.repository;


import com.alextim.domain.Comment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@SuppressWarnings("JpaQlInspection")
@Repository
public class CommentRepositoryJpa implements CommentRepository{
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void insert(Comment comment) {
        em.persist(comment);
    }

    @Override
    public long getCount() {
        Query query = em.createQuery("select count(c) from Comment c");
        return (Long)query.getSingleResult();
    }

    @Override
    public List<Comment> getAll(int page, int amountByOnePage) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c", Comment.class);
        query.setFirstResult((--page) * amountByOnePage);
        query.setMaxResults(amountByOnePage);
        return query.getResultList();
    }

    @Override
    public Comment findById(long id) {
        return em.find(Comment.class, id);
    }

    @Transactional
    @Override
    public void update(long id, Comment comment) {
        Comment byId = findById(id);
        byId.setComment(comment.getComment());
        em.merge(byId);
    }

    @Transactional
    @Override
    public void delete(long id) {
        Query query = em.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}