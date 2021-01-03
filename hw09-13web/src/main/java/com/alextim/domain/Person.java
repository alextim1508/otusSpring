package com.alextim.domain;

import lombok.*;

import javax.persistence.*;

import static com.alextim.domain.Person.COLLECTION_TITLE;

@Entity @Table(name = COLLECTION_TITLE, uniqueConstraints= @UniqueConstraint(columnNames={Person.FIELD_NAME}))
@Data @NoArgsConstructor @RequiredArgsConstructor @EqualsAndHashCode(exclude = {"id"})
public class Person {

    public static final String COLLECTION_TITLE = "persons";
    public static final String FIELD_NAME = "name";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(name = FIELD_NAME)
    @NonNull
    private String name;

}

