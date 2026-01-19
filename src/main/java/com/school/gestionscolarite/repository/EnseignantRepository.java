package com.school.gestionscolarite.repository;

import com.school.gestionscolarite.entity.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
    java.util.Optional<Enseignant> findByEmail(String email);
}
