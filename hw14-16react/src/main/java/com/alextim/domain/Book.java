package com.alextim.domain;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import java.util.ArrayList;
import java.util.List;

import static com.alextim.domain.Book.COLLECTION_TITLE;
import static com.alextim.domain.Book.FIELD_TITLE;

@CompoundIndexes({
        @CompoundIndex(unique = true, name = "unicTitleBook", def="{'"+ FIELD_TITLE + "' : 1}")
})
@Document(collection = COLLECTION_TITLE)
@Data @NoArgsConstructor @RequiredArgsConstructor @EqualsAndHashCode(exclude = {"id"})
public class Book {

    public static final String COLLECTION_TITLE = "books";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_AUTHOR = "author";
    public static final String FIELD_GENRE = "genre";
    public static final String FIELD_COMMENTS = "comments";

    @Id
    @Setter(AccessLevel.NONE)
    private String id;

    @Field(FIELD_TITLE)
    @NonNull
    private String title;

    @Field(FIELD_AUTHOR)
    @DBRef
    @NonNull
    private Author author;

    @Field(FIELD_GENRE)
    @DBRef
    @NonNull
    private Genre genre;

    @Field(FIELD_COMMENTS)
    @DBRef
    private List<Comment> comments = new ArrayList<>();
}