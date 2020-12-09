package fr.leftac.listify.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.leftac.listify.R;
import fr.leftac.listify.controller.Controller;
import fr.leftac.listify.model.adapter.TrackAdapter;

public class FavoritesFragment extends Fragment {

    private Controller controller;
    private RecyclerView list;
    public TrackAdapter listAdapter;
    private ArrayList tracks;
    private GridLayoutManager gridLayoutManager;
    private int spanCount = 1;

    public FavoritesFragment(Controller controller, int spanCount) {
        super();
        this.controller = controller;
        this.spanCount = spanCount;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        // Recycler View
        gridLayoutManager = new GridLayoutManager(getContext(), spanCount);
        list = view.findViewById(R.id.favoritesList);
        list.setHasFixedSize(true);

        list.setLayoutManager(gridLayoutManager);

        tracks = controller.getSavedTracks();
        listAdapter = new TrackAdapter(tracks, gridLayoutManager, controller, getFragmentManager());
        list.setAdapter(listAdapter);

        return view;
    }

    public void updateListAdapter(){
        tracks = controller.getSavedTracks();
        listAdapter.updateItems(tracks);
        listAdapter.notifyDataSetChanged();
    }

    public TrackAdapter getListAdapter() {
        return listAdapter;
    }

    public GridLayoutManager getGridLayoutManager() {
        return gridLayoutManager;
    }
}