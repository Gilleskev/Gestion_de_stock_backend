package com.gilles.gestionDeStock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import com.gilles.gestionDeStock.dto.FournisseurDto;
import com.gilles.gestionDeStock.exception.ErrorCodes;
import com.gilles.gestionDeStock.exception.InvalidOperationException;
import com.gilles.gestionDeStock.model.Fournisseur;
import com.gilles.gestionDeStock.services.FournisseurService;
import com.gilles.gestionDeStock.services.PhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("fournisseurStrategy")
@Slf4j
public class SaveFournisseurPhoto implements Strategy<FournisseurDto>{

    private PhotoService photoService;
    private FournisseurService fournisseurService;

    @Autowired
    public SaveFournisseurPhoto(PhotoService photoService, FournisseurService fournisseurService) {
        this.photoService = photoService;
        this.fournisseurService = fournisseurService;
    }

    @Override
    public FournisseurDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        FournisseurDto fournisseur = fournisseurService.findById(id);
        String urlPhoto = photoService.savePhoto(photo, titre);
        if (!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo du fournisseur", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        fournisseur.setPhoto(urlPhoto);
        return fournisseurService.save(fournisseur);
    }
}
