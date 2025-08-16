package com.mithpath.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthDto {
    @NotNull(groups = {RegisterGroup.class, LoginGroup.class}, message = "{auth.username.not-null}")
    @NotBlank(groups = {RegisterGroup.class, LoginGroup.class}, message = "{auth.username.not-blank}")
    private String username;

    @NotNull(groups = {RegisterGroup.class}, message = "{auth.email.not-null}")
    @Email(groups = {RegisterGroup.class}, message = "{auth.email.invalid}")
    @NotBlank(groups = {RegisterGroup.class}, message = "{auth.email.not-blank}")
    private String email;

    @NotNull(groups = {LoginGroup.class}, message = "{auth.password.not-null}")
    @NotBlank(groups = {LoginGroup.class}, message = "{auth.password.not-blank}")
    private String password;

    @NotNull(groups = {RegisterGroup.class}, message = "{auth.role.not-null}")
    @NotBlank(groups = {RegisterGroup.class}, message = "{auth.role.not-blank}")
    private String Role;

    public interface RegisterGroup {}
    public interface LoginGroup {}

}
