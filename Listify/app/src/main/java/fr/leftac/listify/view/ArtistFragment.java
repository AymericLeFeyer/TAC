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
import fr.leftac.listify.model.DataFormatter;
import fr.leftac.listify.model.pojo.Artist;

public class ArtistFragment extends Fragment {
    private Artist artist;

    public ArtistFragment(Artist artist) {
        this.artist = artist;

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
        ImageView image = view.findViewById(R.id.image);
        TextView name = view.findViewById(R.id.name);
        TextView followers = view.findViewById(R.id.followers);
        TextView genres = view.findViewById(R.id.genres);
        TextView popularity = view.findViewById(R.id.popularity);

        // Set
        Glide.with(requireContext()).load(artist.getImage()).fitCenter().into(image);
        popularity.setText(DataFormatter.popularity(artist.getPopularity()));
        genres.setText(DataFormatter.genres(artist.getGenres()));
        followers.setText(DataFormatter.followers(artist.getFollowers()));

        name.setText(artist.getName());


    }
}
