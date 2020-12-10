package com.alextim.domain;

import lombok.*;

@Data  @RequiredArgsConstructor @EqualsAndHashCode(exclude = {"id"})
public class Book {

    private long id;

    @NonNull
    private String title;

    @NonNull
    private Author author;

    @NonNull
    private Genre genre;
}
