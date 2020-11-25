package fr.leftac.listify.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.ArrayList;
import java.util.List;

import fr.leftac.listify.R;
import fr.leftac.listify.controller.Controller;
import fr.leftac.listify.model.adapter.TrackAdapter;
import fr.leftac.listify.model.api.TokenManager;
import fr.leftac.listify.model.pojo.Track;

public class SearchFragment extends Fragment {

    private Button searchButton;
    private Controller controller;
    private List<Track> tracks;
    private RecyclerView list;
    private TrackAdapter listAdapter;
    private GridLayoutManager gridLayoutManager;
    private EditText artistField;
    private Toolbar toolbar;

    public SearchFragment(Controller controller) {
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Views
        searchButton = view.findViewById(R.id.searchButton);
        list = view.findViewById(R.id.list);
        artistField = view.findViewById(R.id.artist);
        toolbar = getActivity().findViewById(R.id.toolbar);

        // Init variables
        tracks = new ArrayList<>();

        gridLayoutManager = new GridLayoutManager(getContext(), 1);

        // Recycler View
        list.setHasFixedSize(true);
        list.setLayoutManager(gridLayoutManager);

        // specify an adapter (see also next example)
        listAdapter = new TrackAdapter(tracks, gridLayoutManager, controller);
        list.setAdapter(listAdapter);

        // Buttons
        searchButton.setOnClickListener(v -> {
            if (!artistField.getText().toString().equals("")) {
                tracks = new ArrayList<>();
                controller.searchTracks(artistField.getText().toString());
            } else {
                Toast.makeText(getActivity(), getString(R.string.empty_searchfield), Toast.LENGTH_SHORT).show();
            }
        });

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

    public List<Track> getTracks() {
        return tracks;
    }

    public void updateListAdapter() {
        listAdapter.updateItems(tracks);
        listAdapter.notifyDataSetChanged();
    }
}