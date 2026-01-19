package com.school.gestionscolarite.dto;

import lombok.Data;

@Data
public class SalleDTO {
    private Long id;
    private String nom;
    private String localisation;
    private Long enseignantId; // id de l'enseignant lié
}
