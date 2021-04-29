package com.alextim.domain.nosql;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import static com.alextim.domain.nosql.AuthorNosql.COLLECTION_TITLE;

@CompoundIndexes({
        @CompoundIndex(unique = true, name = "unicNamesAuthor", def="{'firstname' : 1, 'lastname': 1}")})
@Document(collection=COLLECTION_TITLE)
@Data @NoArgsConstructor @RequiredArgsConstructor @EqualsAndHashCode(exclude = "id")
public class AuthorNosql {

    public static final String COLLECTION_TITLE = "authors";
    public static final String FIELD_FIRSTNAME = "Firstname";
    public static final String FIELD_LASTNAME = "Lastname";

    @Id
    private ObjectId id;

    @NonNull
    @Field(value = FIELD_FIRSTNAME)
    private String firstname;

    @NonNull
    @Field(value = FIELD_LASTNAME)
    private String lastname;
}