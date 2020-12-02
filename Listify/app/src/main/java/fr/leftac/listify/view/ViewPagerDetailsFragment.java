package fr.leftac.listify.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import fr.leftac.listify.R;
import fr.leftac.listify.controller.Controller;
import fr.leftac.listify.model.pojo.Track;

public class ViewPagerDetailsFragment extends DialogFragment {

    ViewPager2 viewPager;
    ViewPagerAdapter adapter;
    TabLayout tabLayout;
    Track track;
    Controller controller;

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
    }

    class ViewPagerAdapter extends FragmentStateAdapter {
        public ViewPagerAdapter(ViewPagerDetailsFragment fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new DetailsFragment(track, controller);
            } else {
                return new ArtistFragment(track.getArtist(), controller);
            }

        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}
