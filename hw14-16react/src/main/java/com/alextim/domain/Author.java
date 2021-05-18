package com.alextim.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import static com.alextim.domain.Author.COLLECTION_TITLE;

@CompoundIndexes({
        @CompoundIndex(unique = true, name = "unicNamesAuthor", def="{'Firstname' : 1, 'Lastname': 1}")
})
@Document(collection=COLLECTION_TITLE)
@Data @NoArgsConstructor @RequiredArgsConstructor @EqualsAndHashCode(exclude = {"id"})
public class Author {

    public static final String COLLECTION_TITLE = "authors";
    public static final String FIELD_FIRSTNAME = "Firstname";
    public static final String FIELD_LASTNAME = "Lastname";

    @Id
    @Setter(AccessLevel.NONE)
    private String id;

    @Field(value = FIELD_FIRSTNAME)
    @NonNull
    private String firstname;

    @Field(value = FIELD_LASTNAME)
    @NonNull
    private String lastname;
}
