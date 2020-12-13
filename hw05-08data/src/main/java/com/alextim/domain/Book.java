package com.alextim.domain;

import lombok.*;

import javax.persistence.*;

import java.util.List;

import static com.alextim.domain.Book.COLLECTION_TITLE;


@Entity @Table(name = COLLECTION_TITLE)
@Data @NoArgsConstructor @RequiredArgsConstructor @EqualsAndHashCode(exclude = {"id", "comments"}) @ToString(exclude = "comments")
public class Book {

    public static final String COLLECTION_TITLE = "books";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_AUTHOR_ID = "author_id";
    public static final String FIELD_GENRE_ID = "genre_id";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(name = FIELD_TITLE)
    @NonNull
    private String title;

    @ManyToOne @JoinColumn(name = FIELD_AUTHOR_ID)
    @NonNull
    private Author author;

    @ManyToOne @JoinColumn(name = FIELD_GENRE_ID)
    @NonNull
    private Genre genre;

    @OneToMany(mappedBy="book", fetch=FetchType.LAZY)
    private List<Comment> comments;

}