package com.school.gestionscolarite.repository;

import com.school.gestionscolarite.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    java.util.Optional<Parent> findByEmail(String email);
}
