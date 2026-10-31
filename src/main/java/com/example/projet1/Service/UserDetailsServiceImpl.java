package com.example.projet1.Service;

import com.example.projet1.entitice.Utilisateur;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private IAccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Utilisateur appUser = accountService.loadUserByUsername(username);

        if(appUser == null){
            throw new UsernameNotFoundException("User " + username + " not found");
        }


        String[] roles = { appUser.getRole().toString() };


        return User.withUsername(appUser.getEmail())
                .password(appUser.getMotDePasse())
                .roles(roles)
                .build();
    }
}
