package fr.leftac.listify.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import fr.leftac.listify.R;
import fr.leftac.listify.controller.Controller;
import fr.leftac.listify.model.pojo.Track;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.MyViewHolder> {
    private List<Track> listTracks;
    private GridLayoutManager layoutManager;
    private Controller controller;

    private static final int SEARCH_VIEW_TYPE = 0;
    private static final int FAVORITES_VIEW_TYPE = 1;
    private static final int VIEW_TYPE_ROW = 1;
    private static final int VIEW_TYPE_COLUMN = 2;

    public TrackAdapter(List<Track> listTracks, GridLayoutManager layoutManager, Controller controller) {
        this.listTracks = listTracks;
        this.layoutManager = layoutManager;
        this.controller = controller;
    }

    @NonNull
    @Override
    public TrackAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;

        if (viewType == VIEW_TYPE_ROW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_item_list, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_item_grid, parent, false);
        }

        MyViewHolder vh = new MyViewHolder(view, controller);
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        int spanCount = layoutManager.getSpanCount();
        if (spanCount == 1) {
            return VIEW_TYPE_ROW;
        } else {
            return VIEW_TYPE_COLUMN;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Track t = listTracks.get(position);
        holder.track = t;
        holder.title.setText(t.getName());
        holder.artist.setText(t.getArtist().getName());
        Glide.with(holder.itemView.getContext())
                .load(t.getAlbum().getImage())
                .into(holder.album);

        if(t.isFavorite()){
            holder.favButton.setImageResource(R.drawable.ic_baseline_star_24);
            holder.favButton.setOnClickListener(a -> {
                t.setFavorite(false);
                controller.removeTrackFromBDD(t);
                holder.favButton.setImageResource(R.drawable.ic_baseline_star_border_24);
            });
        }
        else {
            holder.favButton.setImageResource(R.drawable.ic_baseline_star_border_24);
            holder.favButton.setOnClickListener(a -> {
                t.setFavorite(true);
                controller.saveTrackToBDD(t);
                holder.favButton.setImageResource(R.drawable.ic_baseline_star_24);
            });
        }

    }

    @Override
    public int getItemCount() {
        return listTracks.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public TextView title;
        public TextView artist;
        public ImageView album;
        public ImageButton favButton;
        public Track track;

        public MyViewHolder(View v, Controller controller) {
            super(v);
            view = v;
            title = view.findViewById(R.id.title);
            artist = view.findViewById(R.id.artist);
            album = view.findViewById(R.id.album);
            favButton = view.findViewById(R.id.favButton);
        }
    }

    public void updateItems(List<Track> items) {
        this.listTracks = items;
    }

}