package com.school.gestionscolarite.service;

import com.school.gestionscolarite.entity.Matiere;
import com.school.gestionscolarite.repository.MatiereRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatiereService {

    private final MatiereRepository matiereRepository;

    public MatiereService(MatiereRepository matiereRepository) {
        this.matiereRepository = matiereRepository;
    }

    public List<Matiere> getAllMatieres() {
        return matiereRepository.findAll();
    }

    public Optional<Matiere> getMatiereById(Long id) {
        return matiereRepository.findById(id);
    }

    public Matiere createMatiere(Matiere matiere) {
        return matiereRepository.save(matiere);
    }

    public Matiere updateMatiere(Long id, Matiere matiereDetails) {
        return matiereRepository.findById(id)
                .map(matiere -> {
                    matiere.setNom(matiereDetails.getNom());
                    matiere.setCharges(matiereDetails.getCharges());
                    matiere.setCoefficient(matiereDetails.getCoefficient());
                    return matiereRepository.save(matiere);
                })
                .orElse(null);
    }

    public void deleteMatiere(Long id) {
        matiereRepository.deleteById(id);
    }
}
