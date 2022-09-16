package com.example.SpringFullStack.DTO.User;

import lombok.Data;

@Data
public class SignInResponseDTO {

    private String status;
    private String token;

    public SignInResponseDTO(String status, String message) {
        this.status = status;
        this.token = message;
    }
}
