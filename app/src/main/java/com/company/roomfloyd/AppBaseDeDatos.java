package com.company.roomfloyd;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* https://developer.android.com/training/data-storage/room */

@Database(entities = {Album.class}, version = 2, exportSchema = false)
public abstract class AppBaseDeDatos extends RoomDatabase {

    static Executor executor = Executors.newSingleThreadExecutor();

    public abstract AlbumsDao obtenerAlbumsDao();

    private static volatile AppBaseDeDatos db;

    public static AppBaseDeDatos getInstance(final Context context){
        if (db == null) {
            synchronized (AppBaseDeDatos.class) {
                if (db == null) {
                    db = Room.databaseBuilder(context, AppBaseDeDatos.class, "app.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    insertarDatosIniciales(getInstance(context).obtenerAlbumsDao());
                                }

                                @Override
                                public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
                                    super.onDestructiveMigration(db);
                                    insertarDatosIniciales(getInstance(context).obtenerAlbumsDao());
                                }
                            })
                            .build();
                }
            }
        }

        return db;
    }

    private static void insertarDatosIniciales(AlbumsDao dao) {
        List<Album> albums = Arrays.asList(
                new Album("Curl de biceps con barras", "Biceps", "file:///android_asset/curlbiceps.png"),
                new Album("Contractor pecho","pecho","file:///android_asset/contractorpecho.png"),
                new Album("Dominadas","Espalda","file:///android_asset/dominadas.png"),
                new Album("Eliptica","Cardio","file:///android_asset/eliptica.png"),
                new Album("Apertura con mancuernas","Pecho","file:///android_asset/pecho.png"),
                new Album("Plancha","Abdomen","file:///android_asset/plancha.png"),
                new Album("Polea al pecho","Pecho","file:///android_asset/poleaalpecho.png"),
                new Album("Remo","Espalda","file:///android_asset/remo.png"),
                new Album("Press francés en banco plano con barra","Triceps","file:///android_asset/triceps.png"),
                new Album("Extensión de triceps en polea alta","Triceps","file:///android_asset/tricepspolea.png"),
                new Album("Cinta","Cardio","file:///android_asset/cinta.png"),
                new Album("Peso muerto","Pecho","file:///android_asset/pesomuerto.png"),
                new Album("Abdominales con rueda","Abdomen","file:///android_asset/rueda.png"),
                new Album("Fondos en barras paralelas","Pecho","file:///android_asset/fondosenbarrasparalelas.png"),
                new Album("Elevación de rodillas colgando","Abdomen","file:///android_asset/rodillas.png"),
                new Album("Curl de biceps con manos en prolongación","Biceps","file:///android_asset/curlbiceps.png"),
                new Album("Jumping jacks","Cardio","file:///android_asset/jacks.png"),
                new Album("Alpinistas","Cardio","file:///android_asset/alpinistas.png"),
                new Album("Press de banca","Pecho","file:///android_asset/banca.png")
        );

        executor.execute(()-> {
            dao.insertarAlbums(albums);
        });
    }

    @Dao
    public interface AlbumsDao {
        @Insert
        void insertarAlbum(Album album);

        @Insert
        void insertarAlbums(List<Album> albums);

        @Query("SELECT * FROM Album")
        LiveData<List<Album>> obtenerAlbums();
    }
}