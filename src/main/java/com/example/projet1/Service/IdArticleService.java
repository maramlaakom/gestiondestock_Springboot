package com.example.projet1.Service;

import com.example.projet1.entitice.Article;
import com.example.projet1.entitice.Utilisateur;

import java.util.List;

public interface IdArticleService {
    public List<Article> findAll();
    public  Article findByIdArticle (Long id )  ;
    public  void addArticle (Article a , Utilisateur user );
    public  void  DeleteArticle (long id );
    public  Article updateArticle (long id , Article a  );

    List<Article> searchArticles(String search);
}
