package com.example.projet1.Service;

import com.example.projet1.Repository.Utilisateurrepository;
import com.example.projet1.Repository.articlerepository;
import com.example.projet1.Repository.Mouvementrepository;
import com.example.projet1.entitice.Article;
import com.example.projet1.entitice.Mouvement;
import com.example.projet1.entitice.Utilisateur;
import com.example.projet1.entitice.type_mouvement;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ArticleService implements IdArticleService{
    @Autowired
    private  articlerepository artR;
    @Autowired
    private  Mouvementrepository histR;

    @Override
    public List<Article> findAll() {
        return artR.findAll();
    }



    @Override
    public Article findByIdArticle(Long id) {
        Article art = artR.findByIdArticle(id) ;
        return  art ;
    }
    @Transactional
    public void   addArticle(Article a ,  Utilisateur user) {

        a.setQuantite(1);
        Article savedArticle = artR.save(a);


        String commentaire = "Nouvel article ajoute " + user.getNom() + " " + user.getPrenom()
                + " au magasin " + a.getMagasin().getNomMagasin();


        Mouvement hist = new Mouvement();
        hist.setArticle(savedArticle);
        hist.setTypeMouvement(type_mouvement.entree);
        hist.setQuantite(1);
        hist.setUtilisateur(user);
        hist.setCommentaire(commentaire);

        histR.save(hist);

    }

    @Override
    public void DeleteArticle(long id) {
        artR.deleteById(id);
    }

    @Override
    public Article updateArticle(long id, Article a) {
        Article ar =artR.findByIdArticle(id) ;
        ar.setIdArticle(id);
        ar.setIdArticle(a.getIdArticle());
        ar.setCout(a.getCout());
        ar.setNomArticle(a.getNomArticle());
        return artR.save(ar);
    }

    @Override
    public List<Article> searchArticles(String search) {
        if (search == null || search.trim().isEmpty()) {
            return artR.findAll();
        }


        return artR.findByNomArticleContainingOrModeleContaining(search,search);    }
}














