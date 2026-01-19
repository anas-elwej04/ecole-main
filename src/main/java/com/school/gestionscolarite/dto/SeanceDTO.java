package com.school.gestionscolarite.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SeanceDTO {
    private Long id;
    private LocalDateTime dateHeureDebut;
    private LocalDateTime dateHeureFin;
    private String matiere;
    private Long salleId;  // id de la salle liée
}
