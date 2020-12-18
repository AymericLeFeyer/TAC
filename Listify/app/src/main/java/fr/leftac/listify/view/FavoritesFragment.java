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
    public TrackAdapter listAdapter;
    private GridLayoutManager gridLayoutManager;
    private int spanCount;

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
        RecyclerView list = view.findViewById(R.id.favoritesList);
        list.setHasFixedSize(true);

        list.setLayoutManager(gridLayoutManager);

        ArrayList<fr.leftac.listify.model.pojo.Track> tracks = controller.getSavedTracks();
        listAdapter = new TrackAdapter(tracks, gridLayoutManager, controller, getFragmentManager());
        list.setAdapter(listAdapter);

        return view;
    }

    public void updateListAdapter(){
        listAdapter.updateItems(controller.getSavedTracks());
        listAdapter.notifyDataSetChanged();
    }

    public TrackAdapter getListAdapter() {
        return listAdapter;
    }

    public GridLayoutManager getGridLayoutManager() {
        return gridLayoutManager;
    }
}