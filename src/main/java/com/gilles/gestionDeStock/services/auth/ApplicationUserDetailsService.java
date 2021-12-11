package com.gilles.gestionDeStock.services.auth;

import com.gilles.gestionDeStock.dto.UtilisateurDto;
import com.gilles.gestionDeStock.exception.EntityNotFoundException;
import com.gilles.gestionDeStock.exception.ErrorCodes;
import com.gilles.gestionDeStock.model.Utilisateur;
import com.gilles.gestionDeStock.model.auth.ExtendedUser;
import com.gilles.gestionDeStock.repository.UtilisateurRepository;
import com.gilles.gestionDeStock.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {



    @Autowired
    private UtilisateurService service;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Utilisateur utilisateur = repository.findUtilisateurByEmail(email).orElseThrow(()->
//            new EntityNotFoundException("Aucun utilisateur avec l'email fourni", ErrorCodes.UTILISATEURS_NOT_FOUND)
//        );

        UtilisateurDto utilisateur = service.findByEmail(email);

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        utilisateur.getRoles().forEach(role-> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));

        return new ExtendedUser(utilisateur.getEmail(), utilisateur.getMotDePasse(), utilisateur.getEntreprise().getId(), authorities);

        //return new User(utilisateur.getEmail(), utilisateur.getMotDePasse(), Collections.emptyList());

    }
}
