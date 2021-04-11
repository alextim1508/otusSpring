package com.alextim.controller.dto;

import com.alextim.domain.Author;
import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data @NoArgsConstructor @AllArgsConstructor
public class AuthorDto {

    @Setter(AccessLevel.NONE)
    private long id;

    @NotNull(message = "firstname cannot be null") @NotEmpty @NotBlank
    private String firstname;

    @NotNull(message = "lastname cannot be null") @NotEmpty @NotBlank
    private String lastname;

    public static AuthorDto toTransferObject(Author dao) {
        return new AuthorDto(dao.getId(), dao.getFirstname(), dao.getLastname());
    }
}
