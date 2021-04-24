package com.alextim.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

import static com.alextim.domain.Author.COLLECTION_TITLE;

@Entity @Table(name = COLLECTION_TITLE, uniqueConstraints= @UniqueConstraint(columnNames={Author.FIELD_FIRSTNAME, Author.FIELD_LASTNAME}))
@Data @NoArgsConstructor @RequiredArgsConstructor @EqualsAndHashCode(exclude = {"id", "books"}) @ToString(exclude = "books")
public class Author {

    public static final String COLLECTION_TITLE = "authors";
    public static final String FIELD_FIRSTNAME = "firstname";
    public static final String FIELD_LASTNAME = "lastname";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(name = FIELD_FIRSTNAME)
    @NonNull
    private String firstname;

    @Column(name = FIELD_LASTNAME)
    @NonNull
    private String lastname;

    @OneToMany(mappedBy="author", fetch=FetchType.LAZY)
    private List<Book> books;
}