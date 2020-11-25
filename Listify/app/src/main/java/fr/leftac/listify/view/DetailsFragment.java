package fr.leftac.listify.view;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import fr.leftac.listify.R;
import fr.leftac.listify.model.pojo.Track;

public class DetailsFragment extends Fragment {

    private Track track;

    private ImageView image;
    private TextView name, artist, album, duration, popularity;


    public DetailsFragment() {
        // Required empty public constructor
    }

    public DetailsFragment(Track track) {
        this.track = track;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Init

        image = view.findViewById(R.id.image);
        name = view.findViewById(R.id.name);
        artist = view.findViewById(R.id.artist);
        album = view.findViewById(R.id.album);
        duration = view.findViewById(R.id.duration);
        popularity = view.findViewById(R.id.popularity);

//        Set

        Glide.with(getContext())
                .load(track.getAlbum().getImage())
                .into(image);

        name.setText(track.getName());
        artist.setText(track.getArtist().getName());
        album.setText(track.getAlbum().getName());
        int durationValue = track.getDuration() / 1000;
        String durationText = durationValue / 60 + ":" + (durationValue % 60 < 10 ? "0" : "") + durationValue % 60;
        duration.setText(durationText);
        String popularityText = track.getPopularity()+" %";
        popularity.setText(popularityText);







    }
}