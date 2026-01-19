package com.school.gestionscolarite.dto;

import lombok.Data;

@Data
public class NotesDTO {
    private Long id;
    private Double valeur;
    private Long matiereId;
    private String matiereNom;
    private Long eleveId;
    private String eleveNom;
    private String date;
}
