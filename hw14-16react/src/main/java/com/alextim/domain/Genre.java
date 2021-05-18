package com.alextim.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import static com.alextim.domain.Genre.COLLECTION_TITLE;

@Document(collection=COLLECTION_TITLE)
@Data @NoArgsConstructor @RequiredArgsConstructor @EqualsAndHashCode(exclude = {"id"})
public class Genre {

    public static final String COLLECTION_TITLE = "genres";
    public static final String FIELD_TITLE = "title";

    @Id
    @Setter(AccessLevel.NONE)
    private String id;

    @Field(FIELD_TITLE)
    @NonNull
    private String title;
}