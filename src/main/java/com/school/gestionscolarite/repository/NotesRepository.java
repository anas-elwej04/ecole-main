package com.school.gestionscolarite.repository;

import com.school.gestionscolarite.entity.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes, Long> {
	
}
