package com.school.gestionscolarite.dto;

import lombok.Data;

@Data
public class NotesDTO {
    private Long id;
    private Double valeur;
    private String matiere;
    private Long eleveId;   // juste l'id de l'élève pour éviter de renvoyer tout l'objet
    private Long seanceId;  // idem pour séance
}
