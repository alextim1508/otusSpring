package com.alextim.domain;

import lombok.*;

import javax.persistence.*;

import java.util.List;

import static com.alextim.domain.Genre.COLLECTION_TITLE;

@Entity @Table(name = COLLECTION_TITLE)
@Data @NoArgsConstructor @RequiredArgsConstructor @EqualsAndHashCode(exclude = {"id","books"}) @ToString(exclude = "books")
public class Genre  {

    public static final String COLLECTION_TITLE = "genres";
    public static final String FIELD_TITLE = "title";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(name = FIELD_TITLE)
    @NonNull
    private String title;

    @OneToMany(mappedBy="genre", fetch=FetchType.LAZY)
    private List<Book> books;
}