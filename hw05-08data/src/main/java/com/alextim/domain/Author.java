package com.alextim.domain;

import lombok.*;

@Data @RequiredArgsConstructor @EqualsAndHashCode(exclude = {"id"})
public class Author {

    private long id;

    @NonNull
    private String firstname;

    @NonNull
    private String lastname;
}