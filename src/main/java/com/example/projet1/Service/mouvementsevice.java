package com.example.projet1.Service;

import com.example.projet1.Repository.DemandeMouvementrepository;
import com.example.projet1.Repository.Mouvementrepository;
import com.example.projet1.Repository.articlerepository;
import com.example.projet1.entitice.*;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class mouvementsevice implements IdmouvementService {

    Mouvementrepository histR;
    articlerepository artR;
    DemandeMouvementrepository demandeR;

    public mouvementsevice(Mouvementrepository histR, articlerepository artR, DemandeMouvementrepository demandeR) {
        this.histR = histR;
        this.artR = artR;
        this.demandeR = demandeR;
    }

    @Override
    public List<Mouvement> findAll() {
        return histR.findAll();
    }

    @Override
    public List<Mouvement> findByUtilisateurIdUtilisateur(Long id) {
        return histR.findByUtilisateurIdUtilisateur(id);
    }

    @Transactional
    @Override
    public void effectuerEntreeStock(Long idArticle, int qte, Long idDem, Utilisateur user) throws Exception {
        if (qte <= 0) {
            throw new Exception("La quantité ajoutée doit être supérieure à 0");
        }

        Article article = artR.findById(idArticle)
                .orElseThrow(() -> new Exception("Article introuvable"));

        if ("employee".equalsIgnoreCase(String.valueOf(user.getRole())) || "employe_stock".equalsIgnoreCase(String.valueOf(user.getRole()))) {
            DemandeMouvement demande = demandeR.findById(idDem)
                    .orElseThrow(() -> new Exception("Demande introuvable ou ID incorrect."));

            if (!demande.getArticle().getIdArticle().equals(idArticle)) {
                throw new Exception("Cet article ne correspond pas à la demande d'entrée");
            }

            if (demande.getQteDemande() != qte) {
                throw new Exception("La quantité est différente de la quantité de la demande d'entrée");
            }
        }

        article.setQuantite(article.getQuantite() + qte);
        artR.save(article);

        String nomMagasin = article.getMagasin() != null ? article.getMagasin().getNomMagasin() : "Inconnu";
        String commentaire = "Entrée au magasin: " + nomMagasin;

        Mouvement hist = new Mouvement();
        hist.setArticle(article);
        hist.setTypeMouvement(type_mouvement.entree);
        hist.setQuantite(qte);
        hist.setUtilisateur(user);
        hist.setCommentaire(commentaire);
        hist.setDateMouvement(new Date());

        if (idDem != null) {
            DemandeMouvement demande = demandeR.findById(idDem).orElse(null);
            hist.setDm(demande);
        }
        histR.save(hist);

        if ("employee".equalsIgnoreCase(String.valueOf(user.getRole())) || "employe_stock".equalsIgnoreCase(String.valueOf(user.getRole()))) {
            DemandeMouvement demande = demandeR.findById(idDem).orElseThrow();
            demande.setEtat(etat_demande.execute);
            demandeR.save(demande);
        }
    }

    @Transactional
    @Override
    public void effectuerSortieStock(Long idArticle, int qte, Long idDem, Utilisateur user) throws Exception {
        Article article = artR.findById(idArticle)
                .orElseThrow(() -> new Exception("Article introuvable"));

        if ("employee".equalsIgnoreCase(String.valueOf(user.getRole())) || "employe_stock".equalsIgnoreCase(String.valueOf(user.getRole()))) {
            DemandeMouvement demande = demandeR.findById(idDem)
                    .orElseThrow(() -> new Exception("Demande introuvable ou ID incorrect."));

            if (!demande.getArticle().getIdArticle().equals(idArticle)) {
                throw new Exception("Cet article ne correspond pas à la demande de sortie");
            }

            if (demande.getQteDemande() != qte) {
                throw new Exception("La quantité est différente de la quantité de la demande de sortie");
            }
        }

        if (qte > article.getQuantite()) {
            throw new Exception("Quantité insuffisante en stock");
        }

        article.setQuantite(article.getQuantite() - qte);
        artR.save(article);

        String nomMagasin = article.getMagasin() != null ? article.getMagasin().getNomMagasin() : "Inconnu";
        String commentaire = "Sortie du magasin: " + nomMagasin;

        Mouvement hist = new Mouvement();
        hist.setArticle(article);
        hist.setTypeMouvement(type_mouvement.sortie);
        hist.setQuantite(qte);
        hist.setUtilisateur(user);
        hist.setCommentaire(commentaire);
        hist.setDateMouvement(new Date());

        if (idDem != null) {
            DemandeMouvement demande = demandeR.findById(idDem).orElse(null);
            hist.setDm(demande);
        }
        histR.save(hist);

        if ("employee".equalsIgnoreCase(String.valueOf(user.getRole())) || "employe_stock".equalsIgnoreCase(String.valueOf(user.getRole()))) {
            DemandeMouvement demande = demandeR.findById(idDem).orElseThrow();
            demande.setEtat(etat_demande.execute);
            demandeR.save(demande);
        }
    }
}