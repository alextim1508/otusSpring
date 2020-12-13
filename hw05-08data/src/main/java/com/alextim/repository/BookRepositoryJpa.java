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

import static com.alextim.domain.Book.FIELD_TITLE;

@SuppressWarnings("JpaQlInspection")
@Repository
public class BookRepositoryJpa implements BookRepository{

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void insert(Book book) {
        em.persist(book);
    }

    @Override
    public long getCount() {
        Query query = em.createQuery("select count(b) from Book b");
        return (Long)query.getSingleResult();
    }

    @Override
    public List<Book> getAll(int page, int amountByOnePage) {
        if(amountByOnePage <= 0 || page <= 0)
            throw new IllegalArgumentException("Must be amountByOnePage > 0 and page > 0");

        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setFirstResult((--page) * amountByOnePage);
        query.setMaxResults(amountByOnePage);
        return query.getResultList();
    }

    @Override
    public Book findById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> findByTitle(String title) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where "+ FIELD_TITLE + " = :title", Book.class);
        query.setParameter("title", title);
        return query.getResultList();
    }

    @Transactional
    @Override
    public List<Comment> getComments(long id) {
        return new ArrayList<>(em.find(Book.class, id).getComments());
    }

    @Transactional
    @Override
    public void update(long id, Book book) {
        Book byId = findById(id);
        byId.setTitle(book.getTitle());
        byId.setAuthor(book.getAuthor());
        byId.setGenre(book.getGenre());
    }

    @Transactional
    @Override
    public void delete(long id) {
        Query query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}