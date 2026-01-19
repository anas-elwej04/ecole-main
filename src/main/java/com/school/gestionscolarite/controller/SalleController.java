package com.school.gestionscolarite.controller;

import com.school.gestionscolarite.entity.Salle;
import com.school.gestionscolarite.service.SalleService;
import com.school.gestionscolarite.service.EnseignantService;
import com.school.gestionscolarite.dto.SalleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/salles")
@CrossOrigin(origins = "http://localhost:3000")
public class SalleController {

    private final SalleService salleService;
    private final com.school.gestionscolarite.service.EnseignantService enseignantService;

    public SalleController(SalleService salleService,
            com.school.gestionscolarite.service.EnseignantService enseignantService) {
        this.salleService = salleService;
        this.enseignantService = enseignantService;
    }

    @GetMapping
    public java.util.List<com.school.gestionscolarite.dto.SalleDTO> getAllSalles() {
        return salleService.getAllSalles().stream()
                .map(this::convertToDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.school.gestionscolarite.dto.SalleDTO> getSalleById(@PathVariable Long id) {
        return salleService.getSalleById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public com.school.gestionscolarite.dto.SalleDTO createSalle(
            @RequestBody com.school.gestionscolarite.dto.SalleDTO salleDTO) {
        Salle salle = convertToEntity(salleDTO);
        Salle createdSalle = salleService.createSalle(salle);
        return convertToDTO(createdSalle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<com.school.gestionscolarite.dto.SalleDTO> updateSalle(@PathVariable Long id,
            @RequestBody com.school.gestionscolarite.dto.SalleDTO salleDTO) {
        Salle salleDetails = convertToEntity(salleDTO);
        Salle updatedSalle = salleService.updateSalle(id, salleDetails);
        if (updatedSalle == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDTO(updatedSalle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalle(@PathVariable Long id) {
        salleService.deleteSalle(id);
        return ResponseEntity.noContent().build();
    }

    private com.school.gestionscolarite.dto.SalleDTO convertToDTO(Salle salle) {
        com.school.gestionscolarite.dto.SalleDTO dto = new com.school.gestionscolarite.dto.SalleDTO();
        dto.setId(salle.getId());
        dto.setNom(salle.getNom());
        dto.setLocalisation(salle.getLocalisation());
        if (salle.getEnseignant() != null) {
            dto.setEnseignantId(salle.getEnseignant().getId());
        }
        return dto;
    }

    private Salle convertToEntity(com.school.gestionscolarite.dto.SalleDTO dto) {
        Salle salle = new Salle();
        salle.setId(dto.getId()); // updates
        salle.setNom(dto.getNom());
        salle.setLocalisation(dto.getLocalisation());
        if (dto.getEnseignantId() != null) {
            enseignantService.getEnseignantById(dto.getEnseignantId()).ifPresent(salle::setEnseignant);
        }
        return salle;
    }
}
