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
import fr.leftac.listify.model.pojo.Artist;

public class ArtistFragment extends Fragment {
    private Artist artist;
    private Controller controller;

    private TextView name;
    private ImageView image;

    public ArtistFragment(Artist artist, Controller controller) {
        this.artist = artist;
        this.controller = controller;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init
        image = view.findViewById(R.id.image);
        name = view.findViewById(R.id.name);

        // Set
        Log.e("url", artist.getImage());
        Glide.with(getContext())
                .load(artist.getImage())
                .into(image);

        name.setText(artist.getName());


    }
}
