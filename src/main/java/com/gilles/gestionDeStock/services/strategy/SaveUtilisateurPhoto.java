package com.gilles.gestionDeStock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import com.gilles.gestionDeStock.dto.ArticleDto;
import com.gilles.gestionDeStock.dto.UtilisateurDto;
import com.gilles.gestionDeStock.exception.ErrorCodes;
import com.gilles.gestionDeStock.exception.InvalidOperationException;
import com.gilles.gestionDeStock.model.Utilisateur;
import com.gilles.gestionDeStock.services.PhotoService;
import com.gilles.gestionDeStock.services.UtilisateurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("utilisateurStrategy")
@Slf4j
public class SaveUtilisateurPhoto implements Strategy<UtilisateurDto>{

    private PhotoService photoService;
    private UtilisateurService utilisateurService;

    @Autowired
    public SaveUtilisateurPhoto(PhotoService photoService, UtilisateurService utilisateurService) {
        this.photoService = photoService;
        this.utilisateurService = utilisateurService;
    }

    @Override
    public UtilisateurDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        UtilisateurDto utilisateur = utilisateurService.findById(id);
        String urlPhoto = photoService.savePhoto(photo, titre);
        if (!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo de l'utilisateur", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        utilisateur.setPhoto(urlPhoto);
        return utilisateurService.save(utilisateur);
    }
}
