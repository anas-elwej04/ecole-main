package com.school.gestionscolarite.service;

import com.school.gestionscolarite.entity.Eleve;
import com.school.gestionscolarite.repository.EleveRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EleveService {

    private final EleveRepository eleveRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public EleveService(EleveRepository eleveRepository,
            org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.eleveRepository = eleveRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Eleve> getAllEleves() {
        return eleveRepository.findAll();
    }

    public Optional<Eleve> getEleveById(Long id) {
        return eleveRepository.findById(id);
    }

    public Eleve createEleve(Eleve eleve) {
        eleve.setPassword(passwordEncoder.encode(eleve.getPassword()));
        return eleveRepository.save(eleve);
    }

    public Eleve updateEleve(Long id, Eleve eleveDetails) {
        return eleveRepository.findById(id).map(eleve -> {
            eleve.setNom(eleveDetails.getNom());
            eleve.setPrenom(eleveDetails.getPrenom());
            eleve.setEmail(eleveDetails.getEmail());
            eleve.setAdresse(eleveDetails.getAdresse());
            eleve.setClasse(eleveDetails.getClasse());
            eleve.setParent(eleveDetails.getParent());
            return eleveRepository.save(eleve);
        }).orElse(null);
    }

    public void deleteEleve(Long id) {
        eleveRepository.deleteById(id);
    }
}
