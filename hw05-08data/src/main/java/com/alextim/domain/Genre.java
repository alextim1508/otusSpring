package com.alextim.domain;

import lombok.*;

@Data @RequiredArgsConstructor @EqualsAndHashCode(exclude = {"id"})
public class Genre  {

    private long id;

    @NonNull
    private String title;
}