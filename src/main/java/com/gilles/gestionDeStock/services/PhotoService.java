package com.gilles.gestionDeStock.services;

import java.io.InputStream;

public interface PhotoService {

    String savePhoto(InputStream photo, String title);
}
