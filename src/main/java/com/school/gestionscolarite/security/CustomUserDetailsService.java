package com.school.gestionscolarite.security;

import com.school.gestionscolarite.entity.Admin;
import com.school.gestionscolarite.entity.Eleve;
import com.school.gestionscolarite.entity.Enseignant;
import com.school.gestionscolarite.entity.Parent;
import com.school.gestionscolarite.repository.AdminRepository;
import com.school.gestionscolarite.repository.EleveRepository;
import com.school.gestionscolarite.repository.EnseignantRepository;
import com.school.gestionscolarite.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final EleveRepository eleveRepository;
    private final EnseignantRepository enseignantRepository;
    private final ParentRepository parentRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        System.out.println("Trying to authenticate user: " + identifier);

        // 1. Try Admin (username)
        Optional<Admin> admin = adminRepository.findByUsername(identifier);
        if (admin.isPresent()) {
            System.out.println("Found Admin: " + admin.get().getUsername());
            return User.builder()
                    .username(admin.get().getUsername())
                    .password(admin.get().getPassword())
                    .roles("ADMIN")
                    .build();
        }

        // 2. Try Enseignant (email)
        Optional<Enseignant> enseignant = enseignantRepository.findByEmail(identifier);
        if (enseignant.isPresent()) {
            System.out.println("Found Enseignant: " + enseignant.get().getEmail());
            return User.builder()
                    .username(enseignant.get().getEmail())
                    .password(enseignant.get().getPassword())
                    .roles("ENSEIGNANT")
                    .build();
        }

        // 3. Try Eleve (email)
        Optional<Eleve> eleve = eleveRepository.findByEmail(identifier);
        if (eleve.isPresent()) {
            System.out.println("Found Eleve: " + eleve.get().getEmail());
            System.out.println("Stored Password Hash: " + eleve.get().getPassword()); // Debug only
            return User.builder()
                    .username(eleve.get().getEmail())
                    .password(eleve.get().getPassword())
                    .roles("ELEVE")
                    .build();
        }

        // 4. Try Parent (email)
        Optional<Parent> parent = parentRepository.findByEmail(identifier);
        if (parent.isPresent()) {
            System.out.println("Found Parent: " + parent.get().getEmail());
            return User.builder()
                    .username(parent.get().getEmail())
                    .password(parent.get().getPassword())
                    .roles("PARENT")
                    .build();
        }

        System.out.println("User NOT found: " + identifier);
        throw new UsernameNotFoundException("User not found: " + identifier);
    }
}
