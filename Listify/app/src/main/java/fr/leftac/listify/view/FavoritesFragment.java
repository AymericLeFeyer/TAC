package fr.leftac.listify.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.leftac.listify.R;
import fr.leftac.listify.controller.Controller;
import fr.leftac.listify.model.adapter.TrackAdapter;
import fr.leftac.listify.model.pojo.Track;

public class FavoritesFragment extends Fragment {

    private final Controller controller;
    public TrackAdapter listAdapter;
    private List<Track> tracks;
    private GridLayoutManager gridLayoutManager;
    private final int spanCount;
    private boolean ordreTri1 = false, ordreTri2 = false, ordreTri3 = false;

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
        setHasOptionsMenu(true);

        // Recycler View
        gridLayoutManager = new GridLayoutManager(getContext(), spanCount);
        RecyclerView list = view.findViewById(R.id.favoritesList);
        list.setHasFixedSize(true);

        list.setLayoutManager(gridLayoutManager);

        ArrayList<fr.leftac.listify.model.pojo.Track> tracks = controller.getSavedTracks();
        listAdapter = new TrackAdapter(tracks, gridLayoutManager, controller, getFragmentManager());
        list.setAdapter(listAdapter);

        getSavedTracks();

        return view;
    }

    public void getSavedTracks(){
        tracks = controller.getSavedTracks();
    }

    public void updateListAdapter(){
        listAdapter.updateItems(tracks);
        listAdapter.notifyDataSetChanged();
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public TrackAdapter getListAdapter() {
        return listAdapter;
    }

    public GridLayoutManager getGridLayoutManager() {
        return gridLayoutManager;
    }

    public void sort(int i) {
        int length = Toast.LENGTH_LONG;
        if(tracks != null) switch(i){
            case 1:
                Collections.sort(tracks, new Comparator() {
                    @Override
                    public int compare(Object t1, Object t2) {
                        int cmp = ((Track)t1).getName().compareTo(((Track)t2).getName());
                        if(!ordreTri1) return cmp;
                        else return -cmp;
                    }
                });

                ordreTri1 = !ordreTri1;
                Toast.makeText(getActivity(), "Tri favoris par ordre alphabétique du titre réalisé (ordre " + (ordreTri1 ? "croissant" : "décroissant") + ")", length).show();

                break;
            case 2:
                Collections.sort(tracks, new Comparator() {
                    @Override
                    public int compare(Object t1, Object t2) {
                        int cmp = ((Track)t1).getArtist().getName().compareTo(((Track)t2).getArtist().getName());
                        if (!ordreTri2) return cmp;
                        else return -cmp;
                    }
                });

                ordreTri2 = !ordreTri2;
                Toast.makeText(getActivity(), "Tri favoris par ordre alphabétique d'artiste réalisé (ordre " + (ordreTri2 ? "croissant" : "décroissant") + ")", length).show();

                break;
            case 3:
                Collections.sort(tracks, new Comparator() {
                    @Override
                    public int compare(Object t1, Object t2) {
                        int cmp = ((Track)t1).getFavDate().compareTo(((Track)t2).getFavDate());
                        if(!ordreTri3) return -cmp;
                        else return cmp;
                    }
                });

                ordreTri3 = !ordreTri3;
                Toast.makeText(getActivity(), "Tri favoris par date d'ajout réalisé (ordre " + (ordreTri3 ? "croissant" : "décroissant") + ")", length).show();

                break;
            default:
                break;
        }
    }
}