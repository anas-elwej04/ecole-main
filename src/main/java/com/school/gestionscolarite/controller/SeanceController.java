package com.school.gestionscolarite.controller;

import com.school.gestionscolarite.entity.Seance;
import com.school.gestionscolarite.service.SeanceService;
import com.school.gestionscolarite.service.SalleService;
import com.school.gestionscolarite.dto.SeanceDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/seances")
@CrossOrigin(origins = "http://localhost:3000")
public class SeanceController {

    private final SeanceService seanceService;
    private final com.school.gestionscolarite.service.SalleService salleService;

    public SeanceController(SeanceService seanceService,
            com.school.gestionscolarite.service.SalleService salleService) {
        this.seanceService = seanceService;
        this.salleService = salleService;
    }

    @GetMapping
    public java.util.List<com.school.gestionscolarite.dto.SeanceDTO> getAllSeances() {
        return seanceService.getAllSeances().stream()
                .map(this::convertToDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.school.gestionscolarite.dto.SeanceDTO> getSeanceById(@PathVariable Long id) {
        return seanceService.getSeanceById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public com.school.gestionscolarite.dto.SeanceDTO createSeance(
            @RequestBody com.school.gestionscolarite.dto.SeanceDTO seanceDTO) {
        Seance seance = convertToEntity(seanceDTO);
        Seance createdSeance = seanceService.createSeance(seance);
        return convertToDTO(createdSeance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<com.school.gestionscolarite.dto.SeanceDTO> updateSeance(@PathVariable Long id,
            @RequestBody com.school.gestionscolarite.dto.SeanceDTO seanceDTO) {
        Seance seanceDetails = convertToEntity(seanceDTO);
        Seance updatedSeance = seanceService.updateSeance(id, seanceDetails);
        if (updatedSeance == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDTO(updatedSeance));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeance(@PathVariable Long id) {
        seanceService.deleteSeance(id);
        return ResponseEntity.noContent().build();
    }

    private com.school.gestionscolarite.dto.SeanceDTO convertToDTO(Seance seance) {
        com.school.gestionscolarite.dto.SeanceDTO dto = new com.school.gestionscolarite.dto.SeanceDTO();
        dto.setId(seance.getId());
        dto.setDateHeureDebut(seance.getDateHeureDebut());
        dto.setDateHeureFin(seance.getDateHeureFin());
        dto.setMatiere(seance.getMatiere());
        if (seance.getSalle() != null) {
            dto.setSalleId(seance.getSalle().getId());
        }
        return dto;
    }

    private Seance convertToEntity(com.school.gestionscolarite.dto.SeanceDTO dto) {
        Seance seance = new Seance();
        seance.setId(dto.getId()); // updates
        seance.setDateHeureDebut(dto.getDateHeureDebut());
        seance.setDateHeureFin(dto.getDateHeureFin());
        seance.setMatiere(dto.getMatiere());
        if (dto.getSalleId() != null) {
            salleService.getSalleById(dto.getSalleId()).ifPresent(seance::setSalle);
        }
        return seance;
    }
}
