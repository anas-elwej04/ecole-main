package com.school.gestionscolarite.controller;

import com.school.gestionscolarite.dto.EnseignantDTO;
import com.school.gestionscolarite.entity.Enseignant;
import com.school.gestionscolarite.entity.Salle;
import com.school.gestionscolarite.service.EnseignantService;
import com.school.gestionscolarite.service.SalleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/enseignants")
@CrossOrigin(origins = "http://localhost:3000")
public class EnseignantController {

    private final EnseignantService enseignantService;
    private final SalleService salleService;

    public EnseignantController(EnseignantService enseignantService, SalleService salleService) {
        this.enseignantService = enseignantService;
        this.salleService = salleService;
    }

    @GetMapping
    public List<EnseignantDTO> getAllEnseignants() {
        return enseignantService.getAllEnseignants().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnseignantDTO> getEnseignantById(@PathVariable Long id) {
        return enseignantService.getEnseignantById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EnseignantDTO createEnseignant(@RequestBody EnseignantDTO enseignantDTO) {
        Enseignant enseignant = convertToEntity(enseignantDTO);
        Enseignant createdEnseignant = enseignantService.createEnseignant(enseignant);
        return convertToDTO(createdEnseignant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnseignantDTO> updateEnseignant(@PathVariable Long id,
            @RequestBody EnseignantDTO enseignantDTO) {
        Enseignant enseignantDetails = convertToEntity(enseignantDTO);
        Enseignant updatedEnseignant = enseignantService.updateEnseignant(id, enseignantDetails);
        if (updatedEnseignant == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDTO(updatedEnseignant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnseignant(@PathVariable Long id) {
        enseignantService.deleteEnseignant(id);
        return ResponseEntity.noContent().build();
    }

    private EnseignantDTO convertToDTO(Enseignant enseignant) {
        EnseignantDTO dto = new EnseignantDTO();
        dto.setId(enseignant.getId());
        dto.setNom(enseignant.getNom());
        dto.setPrenom(enseignant.getPrenom());
        dto.setEmail(enseignant.getEmail());
        dto.setPassword(enseignant.getPassword());
        if (enseignant.getSalles() != null) {
            dto.setSalleIds(enseignant.getSalles().stream()
                    .map(Salle::getId)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    private Enseignant convertToEntity(EnseignantDTO dto) {
        Enseignant enseignant = new Enseignant();
        enseignant.setId(dto.getId());
        enseignant.setNom(dto.getNom());
        enseignant.setPrenom(dto.getPrenom());
        enseignant.setEmail(dto.getEmail());
        enseignant.setPassword(dto.getPassword());
        if (dto.getSalleIds() != null) {
            List<Salle> salles = new ArrayList<>();
            for (Long salleId : dto.getSalleIds()) {
                salleService.getSalleById(salleId).ifPresent(salles::add);
            }
            enseignant.setSalles(salles);
        }
        return enseignant;
    }
}
