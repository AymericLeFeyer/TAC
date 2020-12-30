package fr.leftac.listify.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import java.util.List;

import fr.leftac.listify.R;
import fr.leftac.listify.controller.Controller;
import fr.leftac.listify.model.adapter.AlbumTracksAdapter;
import fr.leftac.listify.model.pojo.Album;
import fr.leftac.listify.model.pojo.Track;

public class AlbumFragment extends Fragment {
    private final Album album;
    private final Controller controller;
    private final ViewPager2 viewPager;

    private DataListener dataListener;

    public AlbumFragment(Album album, Controller controller, ViewPager2 viewPager) {
        this.album = album;
        this.controller = controller;
        this.viewPager = viewPager;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init
        ImageView image = view.findViewById(R.id.image);
        TextView name = view.findViewById(R.id.name);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        RecyclerView list = view.findViewById(R.id.tracks);
        list.setHasFixedSize(true);

        list.setLayoutManager(gridLayoutManager);

        List<Track> tracks = album.getTracks();
        AlbumTracksAdapter listAdapter = new AlbumTracksAdapter(tracks, viewPager, controller);
        list.setAdapter(listAdapter);

        // Set
        Glide.with(requireContext())
                .load(album.getImage())
                .into(image);

        name.setText(album.getName());


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dataListener = (DataListener) context;
        } catch (Exception ignored) {

        }
    }

    public interface DataListener {

        void onDataReceived(Track track);

    }


}
