package fr.leftac.listify.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.leftac.listify.R;
import fr.leftac.listify.controller.Controller;
import fr.leftac.listify.model.pojo.Track;
import fr.leftac.listify.view.ViewPagerDetailsFragment;

public class AlbumTracksAdapter extends RecyclerView.Adapter<AlbumTracksAdapter.MyViewHolder> {
    private List<Track> listTracks;

    private Controller controller;
    private Context context;
    private FragmentManager fm;


    public AlbumTracksAdapter(List<Track> listTracks, Controller controller, FragmentManager fragmentManager) {
        this.listTracks = listTracks;

        this.controller = controller;
        this.fm = fragmentManager;
    }

    @NonNull
    @Override
    public AlbumTracksAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_album_list, parent, false);

        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Track t = listTracks.get(position);
        holder.track = t;
        holder.title.setText(t.getName());
    }

    @Override
    public int getItemCount() {
        return listTracks.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }

    public void openDetailsFragment(Track track) {

        // Create and show the dialog.
        DialogFragment newFragment = new ViewPagerDetailsFragment(track, controller);
        newFragment.show(fm, "dialog");

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public TextView title;
        public Track track;

        public MyViewHolder(View v) {
            super(v);
            view = v;
            title = view.findViewById(R.id.title);
        }
    }

}