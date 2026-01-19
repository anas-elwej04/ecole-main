package com.school.gestionscolarite.service;

import com.school.gestionscolarite.entity.Salle;
import com.school.gestionscolarite.repository.SalleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalleService {

    private final SalleRepository salleRepository;

    public SalleService(SalleRepository salleRepository) {
        this.salleRepository = salleRepository;
    }

    public List<Salle> getAllSalles() {
        return salleRepository.findAll();
    }

    public Optional<Salle> getSalleById(Long id) {
        return salleRepository.findById(id);
    }

    public Salle createSalle(Salle salle) {
        return salleRepository.save(salle);
    }

    public Salle updateSalle(Long id, Salle salleDetails) {
        return salleRepository.findById(id).map(salle -> {
            salle.setNom(salleDetails.getNom());
            salle.setLocalisation(salleDetails.getLocalisation());
            salle.setEnseignant(salleDetails.getEnseignant());
            return salleRepository.save(salle);
        }).orElse(null);
    }

    public void deleteSalle(Long id) {
        salleRepository.deleteById(id);
    }
}
