package fr.leftac.listify.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private Controller controller;
    public TrackAdapter listAdapter;
    private List<Track> tracks;
    private GridLayoutManager gridLayoutManager;
    private int spanCount;
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

                if(!ordreTri1) {
                    ordreTri1 = true;
                } else ordreTri1 = false;

                break;
            case 2:
                Collections.sort(tracks, new Comparator() {
                    @Override
                    public int compare(Object t1, Object t2) {
                        int cmp = ((Track)t1).getArtist().getName().compareTo(((Track)t2).getArtist().getName());
                        if (!ordreTri2) return -cmp;
                        else return cmp;
                    }
                });

                if(!ordreTri2) {
                    ordreTri2 = true;
                } else ordreTri2 = false;

                break;
            case 3:
                Collections.sort(tracks, new Comparator() {
                    @Override
                    public int compare(Object t1, Object t2) {
                        int cmp = ((Track)t1).getFavDate().compareTo(((Track)t2).getFavDate());
                        if(!ordreTri3) return cmp;
                        else return -cmp;
                    }
                });

                if(!ordreTri3) {
                    ordreTri3 = true;
                } else ordreTri3 = false;

                break;
            default:
                Log.e("sortError", "FavoritesFragment méthode "+i+" non prise en compte");
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}