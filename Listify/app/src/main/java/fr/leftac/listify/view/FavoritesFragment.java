package fr.leftac.listify.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import fr.leftac.listify.R;
import fr.leftac.listify.controller.Controller;
import fr.leftac.listify.model.adapter.TrackAdapter;
import fr.leftac.listify.model.pojo.Track;

public class FavoritesFragment extends Fragment implements Controller.TrackCallbackListener {

    private Controller controller;
    private RecyclerView list;
    public TrackAdapter listAdapter;
    private RecyclerView.LayoutManager listLayoutManager;
    private ArrayList tracks;

    public FavoritesFragment() {}

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

        // Recycler View
        list = view.findViewById(R.id.favoritesList);
        list.setHasFixedSize(true);
        listLayoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(listLayoutManager);

        tracks = controller.getSavedTracks();
        listAdapter = new TrackAdapter(tracks);
        list.setAdapter(listAdapter);
        return view;
    }

    //TODO: Trouver une façon plus opti d'update le listAdapter car actuellement ça crée un petit freeze
    @Override
    public void onResume() {
        tracks = controller.getSavedTracks();
        listAdapter.updateItems(tracks);
        listAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onFetchProgress(Track track) {
    }

    @Override
    public void onFetchComplete() {
    }
}