package fr.leftac.listify.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import fr.leftac.listify.R;
import fr.leftac.listify.controller.Controller;
import fr.leftac.listify.model.pojo.Track;

public class HomeFragment extends Fragment implements Controller.TrackCallbackListener {

    ViewPager2 viewPager;
    ScreenSlidePagerAdapter adapter;
    TabLayout tabLayout;
    private SearchFragment searchFragment;
    private FavoritesFragment favoritesFragment;
    private Controller controller;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = view.findViewById(R.id.pager);
        adapter = new HomeFragment.ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(adapter);
        tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(position == 0 ? "Recherche" : "Favoris")
        ).attach();

        // Toolbar
        if (getActivity() != null) {
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            toolbar.setOnMenuItemClickListener(item -> {
                switchLayout();
                switchIcon(item);
                return false;
            });
        }

        controller = new Controller(this);

        return view;
    }

    @Override
    public void onFetchProgress(Track track) {
        searchFragment.getTracks().add(track);
    }

    @Override
    public void onFetchComplete() {
        searchFragment.updateListAdapter();
        if (favoritesFragment != null) {
            favoritesFragment.updateListAdapter();
        }
    }

    @Override
    public void onNewTrack(Track track) {
        Log.e("home fragment", track.getName());

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void switchIcon(MenuItem item) {
        Context context = getContext();
        if (searchFragment != null && context != null) {
            if (searchFragment.getGridLayoutManager().getSpanCount() == 3) {
                item.setIcon(context.getDrawable(R.drawable.ic_list));
            } else {
                item.setIcon(context.getDrawable(R.drawable.ic_grid));
            }
        }
    }

    private void switchLayout() {
        if (searchFragment != null) {
            if (searchFragment.getGridLayoutManager().getSpanCount() == 1) {
                searchFragment.getGridLayoutManager().setSpanCount(3);
            } else {
                searchFragment.getGridLayoutManager().setSpanCount(1);
            }
            searchFragment.getListAdapter().notifyItemRangeChanged(0, searchFragment.getListAdapter().getItemCount());
            if (favoritesFragment != null && favoritesFragment.getGridLayoutManager().getSpanCount() != searchFragment.getGridLayoutManager().getSpanCount()) {
                favoritesFragment.getGridLayoutManager().setSpanCount(searchFragment.getGridLayoutManager().getSpanCount());
                favoritesFragment.getListAdapter().notifyItemRangeChanged(0, favoritesFragment.getListAdapter().getItemCount());
            }
        }
    }

    class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(HomeFragment fa) {
            super(fa);
        }

        @NotNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                searchFragment = new SearchFragment(controller);
                return searchFragment;
            } else {
                favoritesFragment = new FavoritesFragment(controller, searchFragment.getGridLayoutManager().getSpanCount());
                return favoritesFragment;
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}