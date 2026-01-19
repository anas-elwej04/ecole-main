package com.school.gestionscolarite.controller;

import com.school.gestionscolarite.entity.Notes;
import com.school.gestionscolarite.service.NotesService;
import com.school.gestionscolarite.service.MatiereService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "http://localhost:3000")
public class NotesController {

    private final NotesService notesService;
    private final com.school.gestionscolarite.service.EleveService eleveService;
    private final MatiereService matiereService;

    public NotesController(NotesService notesService,
            com.school.gestionscolarite.service.EleveService eleveService,
            MatiereService matiereService) {
        this.notesService = notesService;
        this.eleveService = eleveService;
        this.matiereService = matiereService;
    }

    @GetMapping
    public java.util.List<com.school.gestionscolarite.dto.NotesDTO> getAllNotes() {
        return notesService.getAllNotes().stream()
                .map(this::convertToDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.school.gestionscolarite.dto.NotesDTO> getNoteById(@PathVariable Long id) {
        return notesService.getNoteById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public com.school.gestionscolarite.dto.NotesDTO createNote(
            @RequestBody com.school.gestionscolarite.dto.NotesDTO notesDTO) {
        Notes notes = convertToEntity(notesDTO);
        Notes createdNote = notesService.createNote(notes);
        return convertToDTO(createdNote);
    }

    @PutMapping("/{id}")
    public ResponseEntity<com.school.gestionscolarite.dto.NotesDTO> updateNote(@PathVariable Long id,
            @RequestBody com.school.gestionscolarite.dto.NotesDTO notesDTO) {
        Notes notesDetails = convertToEntity(notesDTO);
        Notes updatedNote = notesService.updateNote(id, notesDetails);
        if (updatedNote == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDTO(updatedNote));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        notesService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }

    private com.school.gestionscolarite.dto.NotesDTO convertToDTO(Notes notes) {
        com.school.gestionscolarite.dto.NotesDTO dto = new com.school.gestionscolarite.dto.NotesDTO();
        dto.setId(notes.getId());
        dto.setValeur(notes.getValeur());
        if (notes.getMatiere() != null) {
            dto.setMatiereId(notes.getMatiere().getId());
            dto.setMatiereNom(notes.getMatiere().getNom());
        }
        if (notes.getEleve() != null) {
            dto.setEleveId(notes.getEleve().getId());
            dto.setEleveNom(notes.getEleve().getPrenom() + " " + notes.getEleve().getNom());
        }

        return dto;
    }

    private Notes convertToEntity(com.school.gestionscolarite.dto.NotesDTO dto) {
        Notes notes = new Notes();
        notes.setId(dto.getId()); // valuable for updates
        notes.setValeur(dto.getValeur());
        if (dto.getMatiereId() != null) {
            matiereService.getMatiereById(dto.getMatiereId()).ifPresent(notes::setMatiere);
        }
        if (dto.getEleveId() != null) {
            eleveService.getEleveById(dto.getEleveId()).ifPresent(notes::setEleve);
        }

        return notes;
    }
}
