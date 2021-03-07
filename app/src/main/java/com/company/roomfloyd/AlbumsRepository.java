package com.company.roomfloyd;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AlbumsRepository {

    Executor executor = Executors.newSingleThreadExecutor();

    AppBaseDeDatos.AlbumsDao dao;

    AlbumsRepository(Application application) {
        dao = AppBaseDeDatos.getInstance(application).obtenerAlbumsDao();
    }

    LiveData<List<Album>> obtenerAlbums() {
        return dao.obtenerAlbums();
    }

    void insertarAlbum(String titulo, String anyo, String portada) {
        executor.execute(() -> dao.insertarAlbum(new Album(titulo, anyo, portada)));
    }
}