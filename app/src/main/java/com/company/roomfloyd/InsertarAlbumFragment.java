package com.company.roomfloyd;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.company.roomfloyd.databinding.FragmentInsertarAlbumBinding;


public class InsertarAlbumFragment extends Fragment {

    private FragmentInsertarAlbumBinding binding;
    private NavController navController;
    private AlbumsViewModel albumsViewModel;

    private Uri imagenSeleccionada;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentInsertarAlbumBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        albumsViewModel = new ViewModelProvider(requireActivity()).get(AlbumsViewModel.class);
        navController = Navigation.findNavController(view);

        binding.insertar.setOnClickListener(v -> {
            if (imagenSeleccionada == null) {
                Toast.makeText(requireContext(), "Seleccione una imagen", Toast.LENGTH_SHORT).show();
                return;
            } else if (binding.titulo.getText().toString().isEmpty()) {
                binding.titulo.setError("Introduzca el titulo");
                return;
            } else if (binding.anyo.getText().toString().isEmpty()) {
                binding.anyo.setError("Introduzca el año");
                return;
            }
            String titulo = binding.titulo.getText().toString();
            String anyo = binding.anyo.getText().toString();

            albumsViewModel.insertarAlbum(titulo, anyo, imagenSeleccionada.toString());

            albumsViewModel.establecerImagenSeleccionada(null);
            navController.popBackStack();

        });

        binding.portada.setOnClickListener(v -> {
            lanzadorGaleria.launch(new String[]{"image/*"});
        });

        albumsViewModel.imagenSeleccionada.observe(getViewLifecycleOwner(), uri -> {
            if (uri != null) {
                imagenSeleccionada = uri;
                Glide.with(requireView()).load(uri).into(binding.portada);
            }
        });
    }

    private final ActivityResultLauncher<String[]> lanzadorGaleria = registerForActivityResult(new ActivityResultContracts.OpenDocument(), uri -> {
        requireContext().getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        albumsViewModel.establecerImagenSeleccionada(uri);
    });
}

