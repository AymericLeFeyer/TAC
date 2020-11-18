package fr.leftac.listify.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fr.leftac.listify.R;
import fr.leftac.listify.controller.Controller;
import fr.leftac.listify.model.api.TokenManager;
import fr.leftac.listify.model.pojo.Album;
import fr.leftac.listify.model.pojo.Artist;
import fr.leftac.listify.model.pojo.Track;
import io.realm.Realm;

public class FavoritesFragment extends Fragment implements Controller.TrackCallbackListener {

    Controller controller;
    Button button;


    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        controller = new Controller(this);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(v -> {
            controller.searchTracks("Orelsan");
        });
        return view;
    }

    @Override
    public void onFetchProgress(Track track) {
        controller.saveTrack(track);
    }

    @Override
    public void onFetchComplete() {
        for(Track track : controller.getSavedTracks()){
            System.out.println(track.toString());
        }
    }
}