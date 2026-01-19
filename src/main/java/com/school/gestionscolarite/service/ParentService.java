package com.school.gestionscolarite.service;

import com.school.gestionscolarite.entity.Parent;
import com.school.gestionscolarite.repository.ParentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParentService {

    private final ParentRepository parentRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public ParentService(ParentRepository parentRepository,
            org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.parentRepository = parentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    public Optional<Parent> getParentById(Long id) {
        return parentRepository.findById(id);
    }

    public Parent createParent(Parent parent) {
        parent.setPassword(passwordEncoder.encode(parent.getPassword()));
        return parentRepository.save(parent);
    }

    public Parent updateParent(Long id, Parent parentDetails) {
        return parentRepository.findById(id).map(parent -> {
            parent.setNom(parentDetails.getNom());
            parent.setPrenom(parentDetails.getPrenom());
            parent.setTelephone(parentDetails.getTelephone());
            parent.setEmail(parentDetails.getEmail());
            parent.setEleves(parentDetails.getEleves());
            if (parentDetails.getPassword() != null && !parentDetails.getPassword().isEmpty()) {
                parent.setPassword(passwordEncoder.encode(parentDetails.getPassword()));
            }
            return parentRepository.save(parent);
        }).orElse(null);
    }

    public void deleteParent(Long id) {
        parentRepository.deleteById(id);
    }
}
