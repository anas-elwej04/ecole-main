package com.school.gestionscolarite.dto;

import lombok.Data;

@Data
public class AdminDTO {
    private Long id;
    private String username;
    private String password;
    private String role;
}

