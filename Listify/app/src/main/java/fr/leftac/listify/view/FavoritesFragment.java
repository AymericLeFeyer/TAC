package fr.leftac.listify.view;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fr.leftac.listify.R;
import fr.leftac.listify.controller.Controller;
import fr.leftac.listify.model.adapter.TrackAdapter;

public class FavoritesFragment extends Fragment {

    private Controller controller;
    private RecyclerView list;
    public TrackAdapter listAdapter;
    private GridLayoutManager gridLayoutManager;
    private ArrayList tracks;
    private Toolbar toolbar;

    public FavoritesFragment(Controller controller) {
        super();
        this.controller = controller;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        toolbar = getActivity().findViewById(R.id.toolbar);

        // Recycler View
        list = view.findViewById(R.id.favoritesList);
        list.setHasFixedSize(true);
        Context context;
        gridLayoutManager = new GridLayoutManager(getContext(), 1);
        list.setLayoutManager(gridLayoutManager);

        tracks = controller.getSavedTracks();
        listAdapter = new TrackAdapter(tracks, gridLayoutManager, controller);
        list.setAdapter(listAdapter);

        // Toolbar
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switchLayout();
                switchIcon(item);
                return false;
            }
        });



        return view;
    }

    //TODO: Mettre ces methodes qui sont communes avec SearchFragment dans un fichier à part
    private void switchLayout() {
        if (gridLayoutManager.getSpanCount() == 1) {
            gridLayoutManager.setSpanCount(3);
        } else {
            gridLayoutManager.setSpanCount(1);
        }
        listAdapter.notifyItemRangeChanged(0, listAdapter.getItemCount());
    }

    private void switchIcon(MenuItem item) {
        if (gridLayoutManager.getSpanCount() == 3) {
            item.setIcon(getResources().getDrawable(R.drawable.ic_list));
        } else {
            item.setIcon(getResources().getDrawable(R.drawable.ic_grid));
        }
    }

    public void updateListAdapter(){
        tracks = controller.getSavedTracks();
        listAdapter.updateItems(tracks);
        listAdapter.notifyDataSetChanged();
    }
}