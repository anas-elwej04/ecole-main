package com.school.gestionscolarite.service;

import com.school.gestionscolarite.entity.Enseignant;
import com.school.gestionscolarite.repository.EnseignantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnseignantService {

    private final EnseignantRepository enseignantRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public EnseignantService(EnseignantRepository enseignantRepository,
            org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.enseignantRepository = enseignantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Enseignant> getAllEnseignants() {
        return enseignantRepository.findAll();
    }

    public Optional<Enseignant> getEnseignantById(Long id) {
        return enseignantRepository.findById(id);
    }

    public Enseignant createEnseignant(Enseignant enseignant) {
        enseignant.setPassword(passwordEncoder.encode(enseignant.getPassword()));
        return enseignantRepository.save(enseignant);
    }

    public Enseignant updateEnseignant(Long id, Enseignant enseignantDetails) {
        return enseignantRepository.findById(id).map(enseignant -> {
            enseignant.setNom(enseignantDetails.getNom());
            enseignant.setPrenom(enseignantDetails.getPrenom());
            enseignant.setEmail(enseignantDetails.getEmail());
            enseignant.setSalles(enseignantDetails.getSalles());
            enseignant.setMatiere(enseignantDetails.getMatiere());
            if (enseignantDetails.getPassword() != null && !enseignantDetails.getPassword().isEmpty()) {
                enseignant.setPassword(passwordEncoder.encode(enseignantDetails.getPassword()));
            }
            return enseignantRepository.save(enseignant);
        }).orElse(null);
    }

    public void deleteEnseignant(Long id) {
        enseignantRepository.deleteById(id);
    }
}
