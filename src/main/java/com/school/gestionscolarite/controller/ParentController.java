package com.school.gestionscolarite.controller;

import com.school.gestionscolarite.dto.ParentDTO;
import com.school.gestionscolarite.entity.Eleve;
import com.school.gestionscolarite.entity.Parent;
import com.school.gestionscolarite.service.EleveService;
import com.school.gestionscolarite.service.ParentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/parents")
@CrossOrigin(origins = "http://localhost:3000")
public class ParentController {

    private final ParentService parentService;
    private final EleveService eleveService;

    public ParentController(ParentService parentService, EleveService eleveService) {
        this.parentService = parentService;
        this.eleveService = eleveService;
    }

    @GetMapping
    public List<ParentDTO> getAllParents() {
        return parentService.getAllParents().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParentDTO> getParentById(@PathVariable Long id) {
        return parentService.getParentById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ParentDTO createParent(@RequestBody ParentDTO parentDTO) {
        Parent parent = convertToEntity(parentDTO);
        Parent createdParent = parentService.createParent(parent);
        return convertToDTO(createdParent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParentDTO> updateParent(@PathVariable Long id, @RequestBody ParentDTO parentDTO) {
        Parent parentDetails = convertToEntity(parentDTO);
        Parent updatedParent = parentService.updateParent(id, parentDetails);
        if (updatedParent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDTO(updatedParent));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParent(@PathVariable Long id) {
        parentService.deleteParent(id);
        return ResponseEntity.noContent().build();
    }

    private ParentDTO convertToDTO(Parent parent) {
        ParentDTO dto = new ParentDTO();
        dto.setId(parent.getId());
        dto.setNom(parent.getNom());
        dto.setPrenom(parent.getPrenom());
        dto.setTelephone(parent.getTelephone());
        dto.setPassword(parent.getPassword());
        if (parent.getEleves() != null) {
            dto.setEleveIds(parent.getEleves().stream()
                    .map(Eleve::getId)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    private Parent convertToEntity(ParentDTO dto) {
        Parent parent = new Parent();
        parent.setId(dto.getId());
        parent.setNom(dto.getNom());
        parent.setPrenom(dto.getPrenom());
        parent.setTelephone(dto.getTelephone());
        parent.setPassword(dto.getPassword());
        if (dto.getEleveIds() != null) {
            List<Eleve> eleves = new ArrayList<>();
            for (Long eleveId : dto.getEleveIds()) {
                eleveService.getEleveById(eleveId).ifPresent(eleves::add);
            }
            parent.setEleves(eleves);
        }
        return parent;
    }
}
