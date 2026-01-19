package com.school.gestionscolarite.service;


import com.school.gestionscolarite.entity.Notes;
import com.school.gestionscolarite.repository.NotesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotesService {

    private final NotesRepository notesRepository;

    public NotesService(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    public List<Notes> getAllNotes() {
        return notesRepository.findAll();
    }

    public Optional<Notes> getNoteById(Long id) {
        return notesRepository.findById(id);
    }

    public Notes createNote(Notes notes) {
        return notesRepository.save(notes);
    }

    public Notes updateNote(Long id, Notes notesDetails) {
        return notesRepository.findById(id)
                .map(note -> {
                    note.setValeur(notesDetails.getValeur());
                    note.setMatiere(notesDetails.getMatiere());
                    note.setEleve(notesDetails.getEleve());
                    note.setSeance(notesDetails.getSeance());
                    return notesRepository.save(note);
                })
                .orElse(null);
    }

    public void deleteNote(Long id) {
        notesRepository.deleteById(id);
    }
}

