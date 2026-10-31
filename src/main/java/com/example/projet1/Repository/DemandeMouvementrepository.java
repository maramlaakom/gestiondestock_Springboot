package com.example.projet1.Repository;

import com.example.projet1.entitice.DemandeMouvement;
import com.example.projet1.entitice.etat_demande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DemandeMouvementrepository extends JpaRepository<DemandeMouvement, Long> {

    List<DemandeMouvement> findByUtilisateurIdUtilisateur(long idu);

    List<DemandeMouvement> findByEtat(etat_demande etat);


    long countByUtilisateurIdUtilisateur(Long idUser);


}