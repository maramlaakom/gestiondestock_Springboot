package com.example.projet1.Service;


import com.example.projet1.Repository.Magasinrepository;
import com.example.projet1.Repository.articlerepository;
import com.example.projet1.entitice.Article;
import com.example.projet1.entitice.Magasin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MagasinService implements IdMagasinservice{
    @Autowired
    Magasinrepository magR;
    @Autowired
    articlerepository artR ;


    @Override
    public List<Magasin> findAll() {
        return magR.findAll();
    }

    @Override
    public Magasin findByIdMagasin(Long id) {
        Magasin mag  = magR.findById(id).orElse(null) ;
        return  mag;
    }

    @Override
    public Magasin addEMagasin(Magasin m) {

        return magR.save(m);    }

    @Override
    public void DeleteMagasin(long id) {
    magR.deleteById(id);
    }

    @Override
    public Magasin updateMagasin(long id, Magasin m) {
        Magasin mag =magR.findById(id).orElse(null) ;
        mag.setIdMagasin(id);
        mag.setIdMagasin(m.getIdMagasin());
        mag.setNomMagasin(m.getNomMagasin());
        mag.setAdresse(m.getAdresse());
        return magR.save(m);
    }

    @Override
    public Article addArticleTomagasin(long magasinId, Article a) {
        Magasin mag = magR.findById(magasinId).orElseThrow(
                () -> new RuntimeException("magasin non trouvé avec id " + magasinId)
        );

        a.setMagasin(mag);



        return artR.save(a);
    }
    @Override
    public List<Magasin> searchmagasin(String search) {
        if (search == null || search.trim().isEmpty()) {
            return magR.findAll();
        }


        return magR.findByNomMagasinContainingIgnoreCase(search);
    }
}
