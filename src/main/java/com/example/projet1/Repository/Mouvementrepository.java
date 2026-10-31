package com.example.projet1.Repository;

import com.example.projet1.entitice.Mouvement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Mouvementrepository extends JpaRepository<Mouvement, Long> {

    List<Mouvement> findByUtilisateurIdUtilisateur(Long id);

    List<Mouvement> findTop20ByUtilisateurIdUtilisateurOrderByDateMouvementDesc(Long idUser);

    List<Mouvement> findTop5ByUtilisateurIdUtilisateurOrderByDateMouvementDesc(Long idUser);

    @Query("SELECT COUNT(m) FROM Mouvement m WHERE m.utilisateur.idUtilisateur = :idUser " +
            "AND MONTH(m.dateMouvement) = :month AND YEAR(m.dateMouvement) = :year")
    long countMouvementsByMonth(@Param("idUser") Long idUser,
                                @Param("month") int month,
                                @Param("year") int year);


}