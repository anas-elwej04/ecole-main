package com.school.gestionscolarite.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "matiere")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Matiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private int charges; // nombre d'heures

    private Double coefficient;
}
