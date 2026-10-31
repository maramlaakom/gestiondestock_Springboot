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
public class DemandeMouvement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Demande;

    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date dateDemande;

    private Integer qteDemande;
    @Enumerated(EnumType.STRING)
    private  etat_demande etat ;



    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "id_article")
    private Article article;

    @OneToOne (mappedBy = "dm")
    private Mouvement mouvement;
}