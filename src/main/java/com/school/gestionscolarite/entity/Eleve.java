package com.school.gestionscolarite.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "eleve")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Eleve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String prenom;

    private String email;

    private String password;

    private String adresse;

    private String classe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Parent parent;
}
