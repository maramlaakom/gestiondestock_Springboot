package com.example.projet1.entitice;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Mouvement implements Serializable  {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idHistorique;

        @Enumerated(EnumType.STRING)
        private type_mouvement typeMouvement;

        private Integer quantite;

        @Temporal(TemporalType.TIMESTAMP)
        private Date dateMouvement;

        private String commentaire;

        // Relation: N Historiques -> 1 Article (tracer)
        @ManyToOne
        @JoinColumn(name = "id_article",nullable = true)
        private Article article;
        @OneToOne
        @JoinColumn(name = "id_demande",nullable = true)
        private DemandeMouvement dm ;
        @ManyToOne
        @JoinColumn(name = "id_utilisateur",nullable = true)
        private Utilisateur utilisateur;
    }

