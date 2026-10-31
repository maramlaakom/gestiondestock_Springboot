package com.example.projet1.entitice;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "magasines")public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArticle;

    private String nomArticle;
    private String modele;
    private Float cout;
    private Integer quantite;

    @ManyToOne
    @JoinColumn(name = "id_magasin")
    private Magasin magasin;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private Set<DemandeMouvement> demandes;
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private Set<Mouvement> mouvements;




}