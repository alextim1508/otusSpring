package com.alextim.controller.dto;

import com.alextim.domain.Genre;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data @NoArgsConstructor @AllArgsConstructor
public class GenreDto implements Dto {

    @Setter(AccessLevel.NONE)
    private long id;

    @NotNull(message = "title cannot be null") @NotEmpty @NotBlank
    private String title;

    public static GenreDto toTransferObject(Genre dao) {
        return new GenreDto(dao.getId(), dao.getTitle());
    }
}
