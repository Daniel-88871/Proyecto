package com.company.roomfloyd;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class AlbumsViewModel extends AndroidViewModel {

    AlbumsRepository albumsRepository;

    MutableLiveData<Uri> imagenSeleccionada = new MutableLiveData<>();

    public AlbumsViewModel(@NonNull Application application) {
        super(application);

        albumsRepository = new AlbumsRepository(application);
    }


    public LiveData<List<Album>> obtenerAlbums() {
        return albumsRepository.obtenerAlbums();
    }

    void insertarAlbum(String titulo, String anyo, String portada) {
        albumsRepository.insertarAlbum(titulo, anyo, portada);
    }

    void establecerImagenSeleccionada(Uri uri){
        imagenSeleccionada.setValue(uri);
    }
}