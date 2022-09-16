package com.example.SpringFullStack.DTO.User;

import lombok.Data;

@Data
public class SignInDTO {

    private String email;
    private String password;

    public SignInDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
