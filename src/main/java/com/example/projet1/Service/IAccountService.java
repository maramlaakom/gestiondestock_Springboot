package com.example.projet1.Service;

import com.example.projet1.entitice.Article;
import com.example.projet1.entitice.Role;
import com.example.projet1.entitice.Utilisateur;
import java.util.List;

public interface IAccountService {

    public Utilisateur addNewUser(String username, String password, String confirmPassword, String email, String prenom, Role role);

    public Utilisateur loadUserByUsername(String email);

    public List<Utilisateur> findAll();

    public Utilisateur findByIdUtilisateur(Long id);

    public void DeleteUtilisateur(long id);

    public Utilisateur updateUtilisateur(long id, Utilisateur u);

    List<Utilisateur> searchuser(String search);
}
