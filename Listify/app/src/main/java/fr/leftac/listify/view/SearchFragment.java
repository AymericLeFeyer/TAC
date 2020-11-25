package fr.leftac.listify.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import fr.leftac.listify.R;
import fr.leftac.listify.controller.Controller;
import fr.leftac.listify.model.adapter.RecyclerItemClickListener;
import fr.leftac.listify.model.adapter.TrackAdapter;
import fr.leftac.listify.model.api.TokenManager;
import fr.leftac.listify.model.pojo.Track;

public class SearchFragment extends Fragment implements Controller.TrackCallbackListener {

    private Button tokenButton, searchButton;
    private Controller controller;
    private List<Track> tracks;
    private RecyclerView list;
    private TrackAdapter listAdapter;
    private GridLayoutManager gridLayoutManager;
    private EditText artistField;
    private Toolbar toolbar;




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
        tokenButton = view.findViewById(R.id.tokenButton);
        searchButton = view.findViewById(R.id.searchButton);
        list = view.findViewById(R.id.list);
        artistField = view.findViewById(R.id.artist);
        toolbar = getActivity().findViewById(R.id.toolbar);

        // Init variables
        if (tracks == null) tracks = new ArrayList<>();


        gridLayoutManager = new GridLayoutManager(getContext(), 1);


        // Recycler View
        list.setHasFixedSize(true);
        list.setLayoutManager(gridLayoutManager);
        list.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), list, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                openDetailsFragment(tracks.get(position));
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));


        // specify an adapter (see also next example)
        listAdapter = new TrackAdapter(tracks, gridLayoutManager);
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
            search();


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

    public void openDetailsFragment(Track track) {
//        DetailsFragment detailsFragment = new DetailsFragment(t);
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, detailsFragment).addToBackStack(null).commit();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_details, null);
        dialogBuilder.setView(dialogView);

        //        Init

        ImageView image = dialogView.findViewById(R.id.image);
        TextView name = dialogView.findViewById(R.id.name);
        TextView artist = dialogView.findViewById(R.id.artist);
        TextView album = dialogView.findViewById(R.id.album);
        TextView duration = dialogView.findViewById(R.id.duration);
        TextView popularity = dialogView.findViewById(R.id.popularity);

//        Set

        Glide.with(getContext())
                .load(track.getAlbum().getImage())
                .into(image);

        name.setText(track.getName());
        artist.setText(track.getArtist().getName());
        album.setText(track.getAlbum().getName());
        int durationValue = track.getDuration() / 1000;
        String durationText = durationValue / 60 + ":" + (durationValue % 60 < 10 ? "0" : "") + durationValue % 60;
        duration.setText(durationText);
        String popularityText = track.getPopularity()+" %";
        popularity.setText(popularityText);


        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();


    }

    public void search() {
        if (!artistField.getText().toString().equals("")) {
            tracks = new ArrayList<>();
            controller = new Controller(this);
            controller.searchTracks(artistField.getText().toString());
        } else {
            Toast.makeText(getActivity(), getString(R.string.empty_searchfield), Toast.LENGTH_SHORT).show();
        }
    }

}