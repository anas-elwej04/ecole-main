package com.school.gestionscolarite.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email; // can be username for admin
    private String password;
}
