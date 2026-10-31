package com.example.projet1.Service;

import com.example.projet1.Repository.DemandeMouvementrepository;
import com.example.projet1.Repository.Utilisateurrepository;
import com.example.projet1.Repository.articlerepository;
import com.example.projet1.entitice.Article;
import com.example.projet1.entitice.DemandeMouvement;
import com.example.projet1.entitice.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DemandService implements  IdDemandService{
    @Autowired
    DemandeMouvementrepository dmR ;
    @Autowired
   articlerepository artR;

    @Autowired
     Utilisateurrepository utiR;
    @Override
    public List<DemandeMouvement> findAll() {
        return dmR.findAll();
    }

    @Override
    public DemandeMouvement findByIddemande(Long id) {
        DemandeMouvement dm  = dmR.findById(id).orElse(null);
        return dm;
    }

    @Override
    public DemandeMouvement addDemande(long idArticle, long idUtilisateur, DemandeMouvement dm) {

        Article art = artR.findById(idArticle).orElseThrow(
                () -> new RuntimeException("L'article avec l'ID " + idArticle + " n'existe pas")
        );

        Utilisateur uti = utiR.findById(idUtilisateur).orElseThrow(
                () -> new RuntimeException("L'utilisateur avec l'ID " + idUtilisateur + " n'existe pas")
        );

        dm.setArticle(art);
        dm.setUtilisateur(uti);

        if (dm.getDateDemande() == null) {
            dm.setDateDemande(new java.util.Date());
        }

        String nomComplet = uti.getPrenom() + " " + uti.getNom();
        String nomArticle = art.getNomArticle();

        String nomMagasin = (art.getMagasin() != null) ? art.getMagasin().getNomMagasin() : "Aucun Magasin";


        int qty = (dm.getQteDemande() != null) ? dm.getQteDemande() : 0;

        String type = dm.getEtat().toString();

        String customMessage = String.format("DEMANDE_ACCES | Employe: %s | Art: %s | Qty: %d | Type: %s | From: %s",
                nomComplet, nomArticle, qty, type, nomMagasin);

        dm.setMessage(customMessage);

        return dmR.save(dm);
    }

    @Override
    public void Deletedemande(long id) {
        dmR.deleteById(id);

    }

    @Override
    public DemandeMouvement updatedemande(long id, DemandeMouvement DM) {

        DemandeMouvement dm  = dmR.findById(id).orElse(null);
        dm.setId_Demande(id);
        dm.setId_Demande(DM.getId_Demande());
        dm.setEtat(DM.getEtat());
        return dmR.save(dm);
    }

}
