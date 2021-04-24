package com.alextim.controller.dto;

import com.alextim.domain.Book;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data @NoArgsConstructor @AllArgsConstructor
public class BookDto implements Dto {

    @Setter(AccessLevel.NONE)
    private long id;

    @NotNull(message = "title cannot be null") @NotEmpty @NotBlank
    private String title;

    @Min(value = 1, message = "idAuthor must be longer than 0") @NotNull(message = "authorId cannot be null")
    private long authorId;

    @Min(value = 1, message = "idGenre must be longer than 0") @NotNull(message = "genreId cannot be null")
    private long genreId;

    public static BookDto toTransferObject(Book dao) {
        return new BookDto(dao.getId(), dao.getTitle(), dao.getAuthor().getId(), dao.getGenre().getId());
    }
}
