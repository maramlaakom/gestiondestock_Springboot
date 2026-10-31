package com.example.projet1.Repository;

import com.example.projet1.entitice.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Utilisateurrepository extends JpaRepository<Utilisateur, Long> {

    Utilisateur findByIdUtilisateur(Long id);

    Utilisateur findByEmail(String email);


    List<Utilisateur> findByNomContainingIgnoreCaseOrEmailContainingIgnoreCase(String nom, String email);

    Utilisateur findByNom(String nom);
}