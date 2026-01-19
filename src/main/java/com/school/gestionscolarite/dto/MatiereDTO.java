package com.school.gestionscolarite.dto;

import lombok.Data;

@Data
public class MatiereDTO {
    private Long id;
    private String nom;
    private int charges;
    private Double coefficient;
}
