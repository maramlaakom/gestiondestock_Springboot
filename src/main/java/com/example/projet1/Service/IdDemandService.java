package com.example.projet1.Service;

import com.example.projet1.entitice.Article;
import com.example.projet1.entitice.DemandeMouvement;
import com.example.projet1.entitice.Utilisateur;

import java.util.List;

public interface IdDemandService {
    public List<DemandeMouvement> findAll();
    public  DemandeMouvement findByIddemande (Long id )  ;
    public DemandeMouvement addDemande(long idArticle, long idUtilisateur, DemandeMouvement dm);
    public  void  Deletedemande (long id );
    public  DemandeMouvement  updatedemande (long id , DemandeMouvement DM  );
}
