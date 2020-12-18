package fr.leftac.listify.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import org.jetbrains.annotations.NotNull;

import fr.leftac.listify.R;
import fr.leftac.listify.controller.Controller;
import fr.leftac.listify.model.pojo.Track;

public class ViewPagerDetailsFragment extends DialogFragment {

    ViewPager2 viewPager;
    ViewPagerAdapter adapter;
    ImageButton close;
    Track track;
    Controller controller;
    DotsIndicator dotsIndicator;

    public ViewPagerDetailsFragment(Track track, Controller controller) {
        this.track = track;
        this.controller = controller;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_vp_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.pager);
        adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        dotsIndicator = view.findViewById(R.id.dots_indicator);

        dotsIndicator.setViewPager2(viewPager);

        close = view.findViewById(R.id.close);
        close.setOnClickListener(v -> this.dismiss());
    }


    class ViewPagerAdapter extends FragmentStateAdapter implements Controller.TrackCallbackListener {
        DetailsFragment details = new DetailsFragment(track, controller, viewPager);
        ArtistFragment artist = new ArtistFragment(track.getArtist());
        AlbumFragment album = new AlbumFragment(track.getAlbum(), new Controller(this), viewPager);


        public ViewPagerAdapter(ViewPagerDetailsFragment fa) {
            super(fa);
        }

        @NotNull
        @Override
        public Fragment createFragment(int position) {

            switch (position) {
                case 0:
                    return details;

                case 1:
                    return artist;

                default:
                    return album;


            }

        }

        @Override
        public int getItemCount() {
            return 3;
        }

        @Override
        public void onFetchProgress(Track track) {

        }

        @Override
        public void onFetchComplete() {

        }

        @Override
        public void onNewTrack(Track track) {
            details.onNewTrack(track);

        }
    }
}
