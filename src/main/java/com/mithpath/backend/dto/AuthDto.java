package com.mithpath.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthDto {
    @NotNull(groups = {RegisterGroup.class, LoginGroup.class}, message = "Username cannot be null")
    private String username;

    @NotNull(groups = {RegisterGroup.class}, message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(groups = {LoginGroup.class}, message = "Password cannot be null")
    private String password;

    public interface RegisterGroup {}
    public interface LoginGroup {}

}
