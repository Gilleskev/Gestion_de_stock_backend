package com.gilles.gestionDeStock.servicesImpl;


import com.flickr4java.flickr.Flickr;
import com.gilles.gestionDeStock.services.PhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@Slf4j
public class PhotoServiceImpl implements PhotoService {

    @Value("${flickr.apiKey}")
    private  String apiKey;

    @Value("${flickr.apiSecret")
    private String apiSecret;

    private String appKey;

    private String appSecret;

    private Flickr flickr;

    @Override
    public String savePhoto(InputStream photo, String title) {
        return null;
    }
}
