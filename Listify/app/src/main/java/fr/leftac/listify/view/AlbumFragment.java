package fr.leftac.listify.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import fr.leftac.listify.R;
import fr.leftac.listify.controller.Controller;
import fr.leftac.listify.model.pojo.Album;

public class AlbumFragment extends Fragment {
    private Album album;
    private Controller controller;

    private TextView name;
    private ImageView image;

    public AlbumFragment(Album album, Controller controller) {
        this.album = album;
        this.controller = controller;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init
        image = view.findViewById(R.id.image);
        name = view.findViewById(R.id.name);

        // Set
        Log.e("url", album.getImage());
        Glide.with(getContext())
                .load(album.getImage())
                .into(image);

        name.setText(album.getName());


    }
}
