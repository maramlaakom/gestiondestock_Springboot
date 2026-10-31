package com.example.projet1.Service;

import com.example.projet1.entitice.Utilisateur;

public interface UserDetailsService {
    public Utilisateur loadUserByUsername(String username) ;
}
