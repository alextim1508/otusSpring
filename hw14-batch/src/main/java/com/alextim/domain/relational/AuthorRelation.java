package com.alextim.domain.relational;

import lombok.*;

import javax.persistence.*;

import static com.alextim.domain.relational.AuthorRelation.TABLE;

@Entity @Table(name = TABLE, uniqueConstraints = {@UniqueConstraint(columnNames = {"firstname", "lastname"})})
@Data @NoArgsConstructor @RequiredArgsConstructor @EqualsAndHashCode(exclude = {"id"})
public class AuthorRelation {

    public static final String TABLE = "authors";
    public static final String FIRSTNAME_COLUMN = "Firstname";
    public static final String LASTNAME_COLUMN = "Lastname";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(name = FIRSTNAME_COLUMN)
    private String firstname;

    @NonNull
    @Column(name = LASTNAME_COLUMN)
    private String lastname;
}
