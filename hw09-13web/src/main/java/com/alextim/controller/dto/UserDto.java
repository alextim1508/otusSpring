package com.alextim.controller.dto;

import com.alextim.domain.User;
import lombok.*;

import javax.validation.constraints.*;

@Data @NoArgsConstructor @RequiredArgsConstructor @AllArgsConstructor
public class UserDto {

    @Setter(AccessLevel.NONE)
    private long id;

    @NotNull(message = "username cannot be null") @NotEmpty @NotBlank
    @NonNull
    private String username;

    @NotNull(message = "name cannot be null") @NotEmpty @NotBlank
    @NonNull
    private String name;

    @NotNull(message = "surname cannot be null") @NotEmpty @NotBlank
    @NonNull
    private String surname;

    @Email(message = "email does not match the format ") @NotNull(message = "email cannot be null") @NotEmpty @NotBlank
    @NonNull
    private String email;

    @NotNull(message = "phone cannot be null") @NotEmpty @NotBlank @Size(min = 3)
    @NonNull
    private String phone;

    @NotNull(message = "rawPassword cannot be null") @NotEmpty @NotBlank @Pattern(regexp="(^$|[0-9]{10})")
    @NonNull
    private String rawPassword;

    public static UserDto toTransferObject(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getName(), user.getSurname(), user.getEmail(), user.getPhone(), "***" );
    }
}
