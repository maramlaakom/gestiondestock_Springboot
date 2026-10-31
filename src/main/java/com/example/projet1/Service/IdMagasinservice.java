package com.example.projet1.Service;

import com.example.projet1.entitice.Article;
import com.example.projet1.entitice.Magasin;

import java.util.List;

public interface IdMagasinservice {
    public List<Magasin> findAll();
    public  Magasin findByIdMagasin (Long id )  ;
    public  Magasin addEMagasin (Magasin m );
    public  void  DeleteMagasin (long id );
    public  Magasin updateMagasin (long id , Magasin m  );
    public  Article addArticleTomagasin (long id , Article a  );
    public List<Magasin> searchmagasin(String search) ;

    }
