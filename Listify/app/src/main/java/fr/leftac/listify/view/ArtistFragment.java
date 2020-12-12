package fr.leftac.listify.view;

import android.os.Bundle;
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
import fr.leftac.listify.model.DataFormatter;
import fr.leftac.listify.model.pojo.Artist;

public class ArtistFragment extends Fragment {
    private Artist artist;
    private Controller controller;

    private TextView name, popularity, followers, genres;
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
        followers = view.findViewById(R.id.followers);
        genres = view.findViewById(R.id.genres);
        popularity = view.findViewById(R.id.popularity);

        // Set
        Glide.with(requireContext()).load(artist.getImage()).fitCenter().into(image);
        popularity.setText(DataFormatter.popularity(artist.getPopularity()));
        genres.setText(DataFormatter.genres(artist.getGenres()));
        followers.setText(DataFormatter.followers(artist.getFollowers()));

        name.setText(artist.getName());


    }
}
