package com.example.projet1.Service;

import com.example.projet1.Repository.Utilisateurrepository;
import com.example.projet1.entitice.Role;
import com.example.projet1.entitice.Utilisateur;
import com.example.projet1.entitice.etat_compt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private  Utilisateurrepository utR;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Utilisateur addNewUser(String username, String password, String confirmPassword, String email, String prenom, Role role) {
        Utilisateur appUser = utR.findByEmail(email);
        if (appUser != null) throw new RuntimeException("User already exists");

        if (!password.equals(confirmPassword)) throw new RuntimeException("Password mismatch");

        Utilisateur newUser = new Utilisateur();
        newUser.setNom(username);
        newUser.setEmail(email);
        newUser.setPrenom(prenom);
        newUser.setRole(role);

        newUser.setEtatCompte((etat_compt.ACTIF));
        newUser.setDateCreation(new java.util.Date());

        newUser.setMotDePasse(passwordEncoder.encode(password));

        return utR.save(newUser);
    }

    @Override
    public Utilisateur loadUserByUsername(String email) {
        Utilisateur user = utR.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException("User " + email + " not found");
        return user;
    }

    @Override
    public List<Utilisateur> findAll() {
        return utR.findAll();
    }

    @Override
    public Utilisateur findByIdUtilisateur(Long id) {
        return utR.findById(id).orElse(null);
    }

    @Override
    public void DeleteUtilisateur(long id) {
        utR.deleteById(id);
    }

    @Override
    public Utilisateur updateUtilisateur(long id, Utilisateur u) {
        Utilisateur ut = utR.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        ut.setNom(u.getNom());
        ut.setPrenom(u.getPrenom());
        ut.setRole(u.getRole());
        ut.setEmail(u.getEmail());

        ut.setEtatCompte(u.getEtatCompte());
        ut.setDateCreation(u.getDateCreation());

        if(u.getMotDePasse() != null && !u.getMotDePasse().isEmpty()) {
            ut.setMotDePasse(passwordEncoder.encode(u.getMotDePasse()));
        }

        return utR.save(ut);
    }

    @Override
    public List<Utilisateur> searchuser(String search) {
        if (search == null || search.trim().isEmpty()) {
            return utR.findAll();
        }


        return utR.findByNomContainingIgnoreCaseOrEmailContainingIgnoreCase(search, search);
    }

    }
