package com.alextim.domain;

import lombok.*;

import javax.persistence.*;

import static com.alextim.domain.Comment.COLLECTION_TITLE;

@Entity @Table(name = COLLECTION_TITLE)
@Data @NoArgsConstructor @RequiredArgsConstructor @EqualsAndHashCode(exclude = {"id"})
public class Comment {

    public static final String COLLECTION_TITLE = "comments";
    public static final String FIELD_COMMENT = "comment";
    public static final String FIELD_BOOK_ID = "book_id";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(length = 100, name = FIELD_COMMENT)
    @NonNull
    private String comment;

    @ManyToOne @JoinColumn(name= FIELD_BOOK_ID)
    @NonNull
    private Book book;
}