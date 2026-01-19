package com.school.gestionscolarite.controller;

import com.school.gestionscolarite.dto.EleveDTO;
import com.school.gestionscolarite.entity.Eleve;
import com.school.gestionscolarite.entity.Notes;
import com.school.gestionscolarite.repository.NotesRepository;
import com.school.gestionscolarite.service.EleveService;
import com.school.gestionscolarite.service.ParentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/eleves")
@CrossOrigin(origins = "http://localhost:3000")
public class EleveController {

    private final EleveService eleveService;
    private final ParentService parentService;
    private final NotesRepository notesRepository;

    public EleveController(EleveService eleveService, ParentService parentService, NotesRepository notesRepository) {
        this.eleveService = eleveService;
        this.parentService = parentService;
        this.notesRepository = notesRepository;
    }

    @GetMapping
    public List<EleveDTO> getAllEleves() {
        return eleveService.getAllEleves().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EleveDTO> getEleveById(@PathVariable Long id) {
        return eleveService.getEleveById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EleveDTO createEleve(@RequestBody EleveDTO eleveDTO) {
        Eleve eleve = convertToEntity(eleveDTO);
        Eleve createdEleve = eleveService.createEleve(eleve);
        return convertToDTO(createdEleve);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EleveDTO> updateEleve(@PathVariable Long id, @RequestBody EleveDTO eleveDTO) {
        Eleve eleveDetails = convertToEntity(eleveDTO);
        Eleve updatedEleve = eleveService.updateEleve(id, eleveDetails);
        if (updatedEleve == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDTO(updatedEleve));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEleve(@PathVariable Long id) {
        eleveService.deleteEleve(id);
        return ResponseEntity.noContent().build();
    }

    private EleveDTO convertToDTO(Eleve eleve) {
        EleveDTO dto = new EleveDTO();
        dto.setId(eleve.getId());
        dto.setNom(eleve.getNom());
        dto.setPrenom(eleve.getPrenom());
        dto.setEmail(eleve.getEmail());
        dto.setPassword(eleve.getPassword());
        dto.setAdresse(eleve.getAdresse());
        dto.setClasse(eleve.getClasse());
        if (eleve.getParent() != null) {
            dto.setParentId(eleve.getParent().getId());
        }

        // --- CALCUL DE LA MOYENNE GENERALE ---
        List<Notes> notesList = notesRepository.findByEleveId(eleve.getId());
        if (!notesList.isEmpty()) {
            double someNoteFoisCoef = 0.0;
            double someCoefs = 0.0;

            for (Notes note : notesList) {
                if (note.getValeur() != null && note.getMatiere() != null
                        && note.getMatiere().getCoefficient() != null) {
                    someNoteFoisCoef += note.getValeur() * note.getMatiere().getCoefficient();
                    someCoefs += note.getMatiere().getCoefficient();
                }
            }

            if (someCoefs > 0) {
                // Arrondir à 2 chiffres après la virgule si souhaité, ici brut
                dto.setMoyenneGenerale(someNoteFoisCoef / someCoefs);
            } else {
                dto.setMoyenneGenerale(0.0);
            }
        } else {
            dto.setMoyenneGenerale(0.0);
        }

        return dto;
    }

    private Eleve convertToEntity(EleveDTO dto) {
        Eleve eleve = new Eleve();
        eleve.setId(dto.getId());
        eleve.setNom(dto.getNom());
        eleve.setPrenom(dto.getPrenom());
        eleve.setEmail(dto.getEmail());
        eleve.setPassword(dto.getPassword());
        eleve.setAdresse(dto.getAdresse());
        eleve.setClasse(dto.getClasse());
        if (dto.getParentId() != null) {
            parentService.getParentById(dto.getParentId()).ifPresent(eleve::setParent);
        }
        return eleve;
    }
}
