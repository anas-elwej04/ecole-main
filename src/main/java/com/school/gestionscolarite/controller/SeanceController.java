package com.school.gestionscolarite.controller;

import com.school.gestionscolarite.entity.Seance;
import com.school.gestionscolarite.service.SeanceService;
import com.school.gestionscolarite.service.SalleService;
import com.school.gestionscolarite.service.MatiereService;
import com.school.gestionscolarite.service.EnseignantService;
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
    private final SalleService salleService;
    private final MatiereService matiereService;
    private final EnseignantService enseignantService;

    public SeanceController(SeanceService seanceService,
            SalleService salleService,
            MatiereService matiereService,
            EnseignantService enseignantService) {
        this.seanceService = seanceService;
        this.salleService = salleService;
        this.matiereService = matiereService;
        this.enseignantService = enseignantService;
    }

    @GetMapping
    public List<SeanceDTO> getAllSeances() {
        return seanceService.getAllSeances().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeanceDTO> getSeanceById(@PathVariable Long id) {
        return seanceService.getSeanceById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public SeanceDTO createSeance(
            @RequestBody SeanceDTO seanceDTO) {
        Seance seance = convertToEntity(seanceDTO);
        Seance createdSeance = seanceService.createSeance(seance);
        return convertToDTO(createdSeance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeanceDTO> updateSeance(@PathVariable Long id,
            @RequestBody SeanceDTO seanceDTO) {
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

    private SeanceDTO convertToDTO(Seance seance) {
        SeanceDTO dto = new SeanceDTO();
        dto.setId(seance.getId());
        dto.setDateHeureDebut(seance.getDateHeureDebut());
        dto.setDateHeureFin(seance.getDateHeureFin());
        if (seance.getMatiere() != null) {
            dto.setMatiereId(seance.getMatiere().getId());
            dto.setMatiereNom(seance.getMatiere().getNom());
        }
        if (seance.getEnseignant() != null) {
            dto.setEnseignantId(seance.getEnseignant().getId());
            dto.setEnseignantNom(seance.getEnseignant().getNom() + " " + seance.getEnseignant().getPrenom());
        }
        if (seance.getSalle() != null) {
            dto.setSalleId(seance.getSalle().getId());
            dto.setSalleNom(seance.getSalle().getNom());
        }
        return dto;
    }

    private Seance convertToEntity(SeanceDTO dto) {
        Seance seance = new Seance();
        seance.setId(dto.getId()); // updates
        seance.setDateHeureDebut(dto.getDateHeureDebut());
        seance.setDateHeureFin(dto.getDateHeureFin());
        if (dto.getMatiereId() != null) {
            matiereService.getMatiereById(dto.getMatiereId()).ifPresent(seance::setMatiere);
        }
        if (dto.getEnseignantId() != null) {
            enseignantService.getEnseignantById(dto.getEnseignantId()).ifPresent(seance::setEnseignant);
        }
        if (dto.getSalleId() != null) {
            salleService.getSalleById(dto.getSalleId()).ifPresent(seance::setSalle);
        }
        return seance;
    }
}
