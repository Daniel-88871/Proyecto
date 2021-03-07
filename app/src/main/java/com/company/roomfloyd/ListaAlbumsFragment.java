package com.company.roomfloyd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.company.roomfloyd.databinding.FragmentListaAlbumsBinding;
import com.company.roomfloyd.databinding.ViewholderAlbumBinding;

import java.util.List;


public class ListaAlbumsFragment extends Fragment {

    FragmentListaAlbumsBinding binding;
    NavController navController;
    AlbumsViewModel albumsViewModel;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentListaAlbumsBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        albumsViewModel = new ViewModelProvider(requireActivity()).get(AlbumsViewModel.class);

        navController = Navigation.findNavController(view);

        binding.irAInsertarAlbum.setOnClickListener(v -> {
            navController.navigate(R.id.action_listaAlbumsFragment_to_insertarAlbumFragment);
        });

        AlbumsAdapter albumsAdapter = new AlbumsAdapter();
        binding.recyclerView.setAdapter(albumsAdapter);

        albumsViewModel.obtenerAlbums().observe(getViewLifecycleOwner(), albums -> {
            albumsAdapter.setAlbumList(albums);
        });
    }


    class AlbumsAdapter extends RecyclerView.Adapter<AlbumViewHolder> {

        List<Album> albumList;

        @NonNull
        @Override
        public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AlbumViewHolder(ViewholderAlbumBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
            Album album = albumList.get(position);

            holder.binding.titulo.setText(album.titulo);
            holder.binding.anyo.setText(album.anyo);
            Glide.with(holder.itemView)
                    .load(album.portada)
                    .transform(new RoundedCorners(64))
                    .into(holder.binding.portada);
        }

        @Override
        public int getItemCount() {
            return albumList == null ? 0 : albumList.size();
        }

        public void setAlbumList(List<Album> albumList) {
            this.albumList = albumList;
            notifyDataSetChanged();
        }
    }

    static class AlbumViewHolder extends RecyclerView.ViewHolder {
        ViewholderAlbumBinding binding;

        public AlbumViewHolder(@NonNull ViewholderAlbumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}