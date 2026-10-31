package com.example.projet1.Service;

import com.example.projet1.entitice.Mouvement;
import com.example.projet1.entitice.Utilisateur;
import java.util.List;

public interface IdmouvementService {
    List<Mouvement> findAll();
    List<Mouvement> findByUtilisateurIdUtilisateur(Long id);
    void effectuerSortieStock(Long idArticle, int qte, Long idDem, Utilisateur user) throws Exception;
    void effectuerEntreeStock(Long idArticle, int qte, Long idDem, Utilisateur user) throws Exception;
}