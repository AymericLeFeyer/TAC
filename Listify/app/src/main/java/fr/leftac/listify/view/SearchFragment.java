package fr.leftac.listify.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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
    private RecyclerView list;
    private TrackAdapter listAdapter;
    private EditText artistField;
    private GridLayoutManager gridLayoutManager;

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
        list = view.findViewById(R.id.list);
        artistField = view.findViewById(R.id.artist);

        // Init variables
        if (tracks == null) tracks = new ArrayList<>();


        // Recycler View
        gridLayoutManager = new GridLayoutManager(getContext(), 1);
        list.setHasFixedSize(true);
        list.setLayoutManager(gridLayoutManager);

        // specify an adapter (see also next example)
        listAdapter = new TrackAdapter(tracks, gridLayoutManager, controller, getFragmentManager());
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

        return view;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void updateListAdapter() {
        listAdapter.updateItems(tracks);
        listAdapter.notifyDataSetChanged();
    }

    public TrackAdapter getListAdapter() {
        return listAdapter;
    }

    public GridLayoutManager getGridLayoutManager() {
        return gridLayoutManager;
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
}