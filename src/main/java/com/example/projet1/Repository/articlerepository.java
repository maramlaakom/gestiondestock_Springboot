package com.example.projet1.Repository;

import com.example.projet1.entitice.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface articlerepository extends JpaRepository<Article,Long> {
    Article findByIdArticle(long id);

    List<Article> findByNomArticleContainingOrModeleContaining(String s1, String s2);
    long countByQuantiteGreaterThan(int qty);
    long countByQuantiteLessThanAndQuantiteGreaterThan(int max, int min);

    List<Article> findTop10ByQuantiteLessThanAndQuantiteGreaterThan(int max, int min);
}