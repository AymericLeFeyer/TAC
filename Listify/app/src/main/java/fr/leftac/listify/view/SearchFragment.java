package fr.leftac.listify.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.leftac.listify.R;
import fr.leftac.listify.controller.Controller;
import fr.leftac.listify.model.adapter.TrackAdapter;
import fr.leftac.listify.model.api.TokenManager;
import fr.leftac.listify.model.pojo.Track;

public class SearchFragment extends Fragment implements Controller.TrackCallbackListener {

    private Button tokenButton, searchButton;
    private Controller controller;
    private List<Track> tracks;
    private RecyclerView list;
    private TrackAdapter listAdapter;
    private RecyclerView.LayoutManager listLayoutManager;
    private EditText artistField;

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
        list = view.findViewById(R.id.list);
        artistField = view.findViewById(R.id.artist);

        // Init variables
        tracks = new ArrayList<>();


        // Recycler View
        list.setHasFixedSize(true);
        listLayoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(listLayoutManager);

        // specify an adapter (see also next example)
        listAdapter = new TrackAdapter(tracks);
        list.setAdapter(listAdapter);


        // Buttons
        if (TokenManager.getToken() == null) {
            searchButton.setEnabled(false);
        }

        tokenButton.setOnClickListener(v -> {
            TokenManager.generateToken();
            searchButton.setEnabled(true);
        });

        searchButton.setOnClickListener(v -> {
            if (!artistField.getText().toString().equals("")) {
                tracks = new ArrayList<>();
                controller = new Controller(this);
                controller.searchTracks(artistField.getText().toString());
            } else {
                Toast.makeText(getActivity(), getString(R.string.empty_searchfield), Toast.LENGTH_SHORT).show();
            }


        });

        return view;
    }

    @Override
    public void onFetchProgress(Track track) {
        tracks.add(track);
        listAdapter.updateItems(tracks);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFetchComplete() {
        listAdapter.updateItems(tracks);
        listAdapter.notifyDataSetChanged();
    }
}