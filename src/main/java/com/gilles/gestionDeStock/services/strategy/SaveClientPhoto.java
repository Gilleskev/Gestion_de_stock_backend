package com.gilles.gestionDeStock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import com.gilles.gestionDeStock.dto.ClientDto;
import com.gilles.gestionDeStock.exception.ErrorCodes;
import com.gilles.gestionDeStock.exception.InvalidOperationException;
import com.gilles.gestionDeStock.model.Client;
import com.gilles.gestionDeStock.repository.ClientRepository;
import com.gilles.gestionDeStock.services.ClientService;
import com.gilles.gestionDeStock.services.PhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("clientStrategy")
@Slf4j
public class SaveClientPhoto implements Strategy<ClientDto>{

    private PhotoService photoService;

    private ClientService clientService;

    @Autowired
    public SaveClientPhoto(PhotoService photoService, ClientService clientService) {
        this.photoService = photoService;
        this.clientService = clientService;
    }

    @Override
    public ClientDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {

        ClientDto client = clientService.findById(id);
        String urlPhoto = photoService.savePhoto(photo, titre);

        if (!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo du client", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        client.setPhoto(urlPhoto);
        return clientService.save(client);
    }
}
