package com.school.gestionscolarite.dto;

import lombok.Data;

@Data
public class EleveDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String adresse;
    private String classe;
    private Long parentId; // juste l'id du parent
}
