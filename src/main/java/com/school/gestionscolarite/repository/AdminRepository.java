package com.school.gestionscolarite.repository;

import com.school.gestionscolarite.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    java.util.Optional<Admin> findByUsername(String username);
}
