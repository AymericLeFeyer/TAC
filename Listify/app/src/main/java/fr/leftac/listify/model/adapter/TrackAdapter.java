package fr.leftac.listify.model.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.leftac.listify.R;
import fr.leftac.listify.model.pojo.Track;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.MyViewHolder> {
    private List<Track> listTracks;

    public TrackAdapter(List<Track> listTracks) {
        this.listTracks = listTracks;
    }

    @NonNull
    @Override
    public TrackAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_item, parent, false);
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

    public void updateItems(List<Track> items) {
        this.listTracks = items;
    }

}
