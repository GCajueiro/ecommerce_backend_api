package com.ecommerce.liven.ecommerce_backend.api.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegistrationBody {

    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String password;
    @Email
    @NotNull
    @NotBlank
    private String email;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
