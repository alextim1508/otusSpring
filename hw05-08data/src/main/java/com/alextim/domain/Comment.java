package com.alextim.domain;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import static com.alextim.domain.Comment.COLLECTION_TITLE;


@Document(collection=COLLECTION_TITLE)
@Data @NoArgsConstructor @RequiredArgsConstructor
public class Comment {

    public static final String COLLECTION_TITLE = "comments";
    public static final String FIELD_COMMENT = "comment";

    @Id
    @Setter(AccessLevel.NONE)
    private ObjectId id;

    @Field(FIELD_COMMENT)
    @NonNull
    private String comment;
}