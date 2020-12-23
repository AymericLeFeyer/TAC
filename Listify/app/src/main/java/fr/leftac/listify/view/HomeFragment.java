package fr.leftac.listify.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

import fr.leftac.listify.MainActivity;
import fr.leftac.listify.R;
import fr.leftac.listify.controller.Controller;
import fr.leftac.listify.model.DrawableViewModel;
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
                boolean updateSearchFragment = false, updateFavFragment = false;
                int sortMethod = 0;
                switch(item.getItemId()){
                    case R.id.action_modeview:
                        switchLayout();
                        switchIcon(item);
                        break;
                    case R.id.action_search_sort1:
                        updateSearchFragment = true;
                        sortMethod = 1;
                        break;
                    case R.id.action_search_sort2:
                        updateSearchFragment = true;
                        sortMethod = 2;
                        break;
                    case R.id.action_search_sort3:
                        updateSearchFragment = true;
                        sortMethod = 3;
                        break;
                    case R.id.action_search_sort4:
                        updateSearchFragment = true;
                        sortMethod = 4;
                        break;

                    case R.id.action_fav_sort1:
                        updateFavFragment = true;
                        sortMethod = 1;
                        break;
                    case R.id.action_fav_sort2:
                        updateFavFragment = true;
                        sortMethod = 2;
                        break;
                    case R.id.action_fav_sort3:
                        updateFavFragment = true;
                        sortMethod = 3;
                        break;

                    default:
                        break;
                }

                if(searchFragment != null && updateSearchFragment && sortMethod != 0){
                    if(searchFragment.getTracks() != null && !searchFragment.getTracks().isEmpty()){
                        searchFragment.sort(sortMethod);
                        searchFragment.updateListAdapter();
                        Toast.makeText(getActivity(), "Tri par " + item.getTitle().toString().toLowerCase() + " réalisé", Toast.LENGTH_SHORT).show();
                    }
                }

                if(favoritesFragment != null && updateFavFragment && sortMethod != 0){
                    if(favoritesFragment.getTracks() != null && !favoritesFragment.getTracks().isEmpty()) {
                        favoritesFragment.sort(sortMethod);
                        favoritesFragment.updateListAdapter();
                        Toast.makeText(getActivity(), "Tri par " + item.getTitle().toString().toLowerCase() + " réalisé", Toast.LENGTH_SHORT).show();
                    }
                }

                return false;
            });
        }

        controller = new Controller(this);

        return view;
    }

    @Override
    public void onFetchProgress(Track track) {
        if(controller.isFavorite(track)){
            track.setFavorite(true);
        }
        searchFragment.getTracks().add(track);
    }

    @Override
    public void onFetchComplete() {
        searchFragment.updateListAdapter();
        if (favoritesFragment != null) {
            favoritesFragment.getSavedTracks();
            favoritesFragment.updateListAdapter();
        }
    }

    @Override
    public void onNewTrack(Track track) {
        //Log.e("home fragment", track.getName());

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void switchIcon(MenuItem item) {
        Context context = getContext();
        if (searchFragment != null && context != null) {
            if (searchFragment.getGridLayoutManager().getSpanCount() == 3) {
                Drawable d = context.getDrawable(R.drawable.ic_list);
                item.setIcon(d);
                DrawableViewModel viewModel = new ViewModelProvider(requireActivity()).get(DrawableViewModel.class);
                viewModel.setDrawableToShow(d);
            } else {
                Drawable d = context.getDrawable(R.drawable.ic_grid);
                item.setIcon(d);
                DrawableViewModel viewModel = new ViewModelProvider(requireActivity()).get(DrawableViewModel.class);
                viewModel.setDrawableToShow(d);
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