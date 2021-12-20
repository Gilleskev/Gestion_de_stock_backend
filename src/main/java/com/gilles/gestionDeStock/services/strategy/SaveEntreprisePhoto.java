package com.gilles.gestionDeStock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import com.gilles.gestionDeStock.dto.EntrepriseDto;
import com.gilles.gestionDeStock.exception.ErrorCodes;
import com.gilles.gestionDeStock.exception.InvalidOperationException;
import com.gilles.gestionDeStock.model.Entreprise;
import com.gilles.gestionDeStock.services.EntrepriseService;
import com.gilles.gestionDeStock.services.PhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("entrepriseStrategy")
@Slf4j
public class SaveEntreprisePhoto implements Strategy<EntrepriseDto> {
    private PhotoService photoService;

    private EntrepriseService entrepriseService;

    @Autowired
    public SaveEntreprisePhoto(PhotoService photoService, EntrepriseService entrepriseService) {
        this.photoService = photoService;
        this.entrepriseService = entrepriseService;
    }

    @Override
    public EntrepriseDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        EntrepriseDto entreprise = entrepriseService.findById(id);
        String urlPhoto = photoService.savePhoto(photo, titre);

        if (!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo de l'entreprise", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        entreprise.setPhoto(urlPhoto);
        return entrepriseService.save(entreprise);
    }
}
