package com.command.project.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public class SignupRequestDTO {

    @NotNull
    private String password;

    @Email(message = "Указан некорректный email", regexp = "")
    @NotNull
    private String email;

    @NotNull
    private String username;


    public SignupRequestDTO() {
    }

    public Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Optional<String> getUsername() {
        return Optional.ofNullable(username);
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
