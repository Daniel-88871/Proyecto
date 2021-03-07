package com.company.roomfloyd;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Album {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String titulo;
    public String anyo;
    public String portada;

    public Album(String titulo, String anyo, String portada) {
        this.titulo = titulo;
        this.anyo = anyo;
        this.portada = portada;
    }
}