package com.school.gestionscolarite.repository;

import com.school.gestionscolarite.entity.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EleveRepository extends JpaRepository<Eleve, Long> {
    java.util.Optional<Eleve> findByEmail(String email);
}
