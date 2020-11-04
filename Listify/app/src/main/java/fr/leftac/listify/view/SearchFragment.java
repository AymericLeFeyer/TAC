package fr.leftac.listify.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import fr.leftac.listify.R;
import fr.leftac.listify.controller.Controller;
import fr.leftac.listify.model.api.TokenManager;
import fr.leftac.listify.model.pojo.Track;

public class SearchFragment extends Fragment implements Controller.TrackCallbackListener {

    private Button tokenButton, searchButton;
    private Controller controller;
    private List<Track> tracks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Views
        tokenButton = view.findViewById(R.id.tokenButton);
        searchButton = view.findViewById(R.id.searchButton);

        // Init variables
        tracks = new ArrayList<>();

        // Buttons
        if (TokenManager.getToken() == null) {
            searchButton.setEnabled(false);
        }

        tokenButton.setOnClickListener(v -> {
            TokenManager.generateToken();
            searchButton.setEnabled(true);
        });

        searchButton.setOnClickListener(v -> {
            tracks = new ArrayList<>();
            controller = new Controller(this);
            controller.searchTracks("orelsan");
        });

        return view;
    }

    @Override
    public void onFetchProgress(Track track) {
        tracks.add(track);
    }

    @Override
    public void onFetchComplete() {
        StringBuilder msg = new StringBuilder();
        for (int i = 0; i < tracks.size(); i++) {
            msg.append("Track n°").append(i + 1).append(" : ").append(tracks.get(i).getName()).append('\n');
        }
        Toast.makeText(getActivity(), msg.toString(), Toast.LENGTH_LONG).show();
    }
}