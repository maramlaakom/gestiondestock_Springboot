package com.example.projet1.entitice;


import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString(exclude = "articles")
public class Magasin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMagasin;

    private String nomMagasin;
    private String adresse;

    @OneToMany(mappedBy = "magasin", cascade = CascadeType.ALL)
    private Set<Article> articles;


}