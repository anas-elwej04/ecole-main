package com.school.gestionscolarite.repository;

import com.school.gestionscolarite.entity.Seance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeanceRepository extends JpaRepository<Seance, Long> {
}
