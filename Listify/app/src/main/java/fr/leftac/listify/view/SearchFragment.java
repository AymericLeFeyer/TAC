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

}