package com.gilles.gestionDeStock.services;

import com.flickr4java.flickr.FlickrException;

import java.io.InputStream;

public interface PhotoService {

    String savePhoto(InputStream photo, String title) throws FlickrException;
}
