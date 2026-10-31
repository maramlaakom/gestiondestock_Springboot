package com.example.projet1.Repository;

import com.example.projet1.entitice.Article;
import com.example.projet1.entitice.Magasin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Magasinrepository extends JpaRepository<Magasin ,Long> {
    Magasin findByIdMagasin(long id);

    List<Magasin> findByNomMagasinContainingIgnoreCase(String search);

}
