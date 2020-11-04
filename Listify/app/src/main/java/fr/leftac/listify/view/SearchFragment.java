package fr.leftac.listify.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import fr.leftac.listify.R;
import fr.leftac.listify.controller.Controller;
import fr.leftac.listify.model.api.TokenManager;
import fr.leftac.listify.model.pojo.Track;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements Controller.TrackCallbackListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button tokenButton, searchButton;
    private Controller controller;
    private List<Track> tracks;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Views
        tokenButton = view.findViewById(R.id.tokenButton);
        searchButton = view.findViewById(R.id.searchButton);

        // Init variables
        tracks = new ArrayList<>();

        // Buttons
        if (TokenManager.getToken() == null) {
            searchButton.setEnabled(false);
        }

        tokenButton.setOnClickListener(v -> {
            TokenManager.generateToken();
            searchButton.setEnabled(true);
        });

        searchButton.setOnClickListener(v -> {
            tracks = new ArrayList<>();
            controller = new Controller(this);
            controller.searchTracks("orelsan");
        });

        return view;
    }

    @Override
    public void onFetchProgress(Track track) {
        tracks.add(track);
    }

    @Override
    public void onFetchComplete() {
        StringBuilder msg = new StringBuilder();
        for (int i = 0; i < tracks.size(); i++) {
            msg.append("Track n°").append(i + 1).append(" : ").append(tracks.get(i).getName()).append('\n');
        }
        Toast.makeText(getActivity(), msg.toString(), Toast.LENGTH_LONG).show();
    }
}