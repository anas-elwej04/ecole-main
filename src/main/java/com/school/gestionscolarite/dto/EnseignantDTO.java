package com.school.gestionscolarite.dto;

import lombok.Data;

import java.util.List;

@Data
public class EnseignantDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private List<Long> salleIds; // liste des ids des salles liées
    private Long matiereId;
    private MatiereDTO matiere;
}
