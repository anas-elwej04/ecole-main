package com.school.gestionscolarite.dto;

import lombok.Data;

import java.util.List;

@Data
public class ParentDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private String password;
    private List<Long> eleveIds; // liste des ids des élèves liés
}
