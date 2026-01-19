package com.school.gestionscolarite.service;

import com.school.gestionscolarite.entity.Seance;
import com.school.gestionscolarite.repository.SeanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeanceService {

    private final SeanceRepository seanceRepository;

    public SeanceService(SeanceRepository seanceRepository) {
        this.seanceRepository = seanceRepository;
    }

    public List<Seance> getAllSeances() {
        return seanceRepository.findAll();
    }

    public Optional<Seance> getSeanceById(Long id) {
        return seanceRepository.findById(id);
    }

    public Seance createSeance(Seance seance) {
        return seanceRepository.save(seance);
    }

    public Seance updateSeance(Long id, Seance seanceDetails) {
        return seanceRepository.findById(id).map(seance -> {
            seance.setDateHeureDebut(seanceDetails.getDateHeureDebut());
            seance.setDateHeureFin(seanceDetails.getDateHeureFin());
            seance.setMatiere(seanceDetails.getMatiere());
            seance.setSalle(seanceDetails.getSalle());
            return seanceRepository.save(seance);
        }).orElse(null);
    }

    public void deleteSeance(Long id) {
        seanceRepository.deleteById(id);
    }
}
