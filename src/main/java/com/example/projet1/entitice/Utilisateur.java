package com.example.projet1.entitice;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtilisateur;

    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private etat_compt etatCompte;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private Set<DemandeMouvement> demandes;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private Set<Mouvement> mouvements;

    // --- El-constructeur l-farregh el-ghalat fassatnah men hna ---
}