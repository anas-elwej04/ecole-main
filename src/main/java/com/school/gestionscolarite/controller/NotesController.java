package com.school.gestionscolarite.controller;

import com.school.gestionscolarite.entity.Notes;
import com.school.gestionscolarite.service.NotesService;
import com.school.gestionscolarite.service.EleveService;
import com.school.gestionscolarite.service.SeanceService;
import com.school.gestionscolarite.dto.NotesDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "http://localhost:3000")
public class NotesController {

    private final NotesService notesService;
    private final com.school.gestionscolarite.service.EleveService eleveService;
    private final com.school.gestionscolarite.service.SeanceService seanceService;

    public NotesController(NotesService notesService,
            com.school.gestionscolarite.service.EleveService eleveService,
            com.school.gestionscolarite.service.SeanceService seanceService) {
        this.notesService = notesService;
        this.eleveService = eleveService;
        this.seanceService = seanceService;
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
        dto.setMatiere(notes.getMatiere());
        if (notes.getEleve() != null) {
            dto.setEleveId(notes.getEleve().getId());
        }
        if (notes.getSeance() != null) {
            dto.setSeanceId(notes.getSeance().getId());
        }
        return dto;
    }

    private Notes convertToEntity(com.school.gestionscolarite.dto.NotesDTO dto) {
        Notes notes = new Notes();
        notes.setId(dto.getId()); // valuable for updates
        notes.setValeur(dto.getValeur());
        notes.setMatiere(dto.getMatiere());
        if (dto.getEleveId() != null) {
            eleveService.getEleveById(dto.getEleveId()).ifPresent(notes::setEleve);
        }
        if (dto.getSeanceId() != null) {
            seanceService.getSeanceById(dto.getSeanceId()).ifPresent(notes::setSeance);
        }
        return notes;
    }
}
