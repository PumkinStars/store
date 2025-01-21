package com.online.store.Models.Dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntityDto {
    @NotBlank(message = "Username required")
    private String username;
    @NotBlank(message = "Password required")
    private String password;
    @NotBlank(message = "Email required")
    private String email;
    private String roles;
}
