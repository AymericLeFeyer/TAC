package fr.leftac.listify.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class SearchFragment extends Fragment {

    private Button searchButton;
    private Controller controller;
    private List<Track> tracks;
    private TrackAdapter listAdapter;
    private EditText searchField;
    private GridLayoutManager gridLayoutManager;
    private ProgressBar progressBar;
    private boolean ordreTri1 = false, ordreTri2 = false, ordreTri3 = false, ordreTri4 = false;

    public SearchFragment(Controller controller) {
        super();
        this.controller = controller;
    }

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        setHasOptionsMenu(true);

        // Views
        searchButton = view.findViewById(R.id.searchButton);
        RecyclerView list = view.findViewById(R.id.list);
        searchField = view.findViewById(R.id.artist);

        // Init variables
        if (tracks == null) tracks = new ArrayList<>();

        // Recycler View
        gridLayoutManager = new GridLayoutManager(getContext(), 1);
        list.setHasFixedSize(true);
        list.setLayoutManager(gridLayoutManager);

        // specify an adapter (see also next example)
        listAdapter = new TrackAdapter(tracks, gridLayoutManager, controller, getFragmentManager());
        list.setAdapter(listAdapter);

        // Progress Bar
        progressBar = view.findViewById(R.id.progressBar);

        // Buttons
        searchButton.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                if (!searchField.getText().toString().equals("")) {
                    tracks = new ArrayList<>();
                    controller.searchTracks(searchField.getText().toString());
                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), getString(R.string.empty_searchfield), Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                if (getActivity() != null) {
                    Toast.makeText(getActivity(), getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
                }

            }
        });

        searchField.setOnEditorActionListener((v, actionId, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                //do what you want on the press of 'done'
                searchButton.performClick();

            }
            return false;
        });
        return view;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void updateListAdapter() {
        listAdapter.updateItems(tracks);
        listAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
                Toast.makeText(getActivity(), "Tri recherche par ordre alphabétique du titre réalisé (ordre " + (ordreTri1 ? "croissant" : "décroissant") + ")", length).show();

                break;
            case 2:
                Collections.sort(tracks, new Comparator() {
                    @Override
                    public int compare(Object t1, Object t2) {
                        int cmp = ((Track) t1).getArtist().getName().compareTo(((Track) t2).getArtist().getName());
                        if (!ordreTri2) return cmp;
                        else return -cmp;
                    }
                });

                ordreTri2 = !ordreTri2;
                Toast.makeText(getActivity(), "Tri recherche par ordre alphabétique d'artiste réalisé (ordre " + (ordreTri2 ? "croissant" : "décroissant") + ")", length).show();

                break;
            case 3:
                Collections.sort(tracks, new Comparator() {
                    @Override
                    public int compare(Object t1, Object t2) {
                        int cmp = Integer.compare(((Track)t1).getPopularity(),((Track)t2).getPopularity());
                        if(!ordreTri3) return -cmp;
                        else return cmp;
                    }
                });

                ordreTri3 = !ordreTri3;
                Toast.makeText(getActivity(), "Tri recherche par ordre de popularité réalisé (ordre " + (ordreTri3 ? "croissant" : "décroissant") + ")", length).show();

                break;
            case 4:
                Collections.sort(tracks, new Comparator() {
                    @Override
                    public int compare(Object t1, Object t2) {
                        int cmp = Boolean.compare(((Track)t1).isFavorite(), ((Track)t2).isFavorite());
                        if(!ordreTri4) return -cmp;
                        else return cmp;
                    }
                });

                ordreTri4 = !ordreTri4;
                Toast.makeText(getActivity(), "Tri recherche par favoris réalisé (ordre " + (ordreTri4 ? "croissant" : "décroissant") + ")", length).show();

                break;
            default:
                break;
        }
    }
}