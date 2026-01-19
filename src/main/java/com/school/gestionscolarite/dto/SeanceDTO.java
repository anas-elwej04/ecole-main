package com.school.gestionscolarite.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SeanceDTO {
    private Long id;
    private LocalDateTime dateHeureDebut;
    private LocalDateTime dateHeureFin;
    private Long matiereId;
    private String matiereNom;
    private Long enseignantId;
    private String enseignantNom;
    private Long salleId;
    private String salleNom;
}
