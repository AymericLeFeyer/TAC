package fr.leftac.listify.view;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.tabs.TabLayout;

import fr.leftac.listify.R;
import fr.leftac.listify.controller.Controller;
import fr.leftac.listify.model.api.TokenManager;
import fr.leftac.listify.model.pojo.Track;

public class HomeFragment extends Fragment implements Controller.TrackCallbackListener {

    ViewPager2 viewPager;
    ScreenSlidePagerAdapter adapter;
    TabLayout tabLayout;
    private SearchFragment searchFragment;
    private FavoritesFragment favoritesFragment;
    private Controller controller;
    private Toolbar toolbar;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = view.findViewById(R.id.pager);
        adapter = new HomeFragment.ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(adapter);
        tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(position == 0 ? "Recherche" : "Favoris")
        ).attach();

        // Toolbar
        toolbar = getActivity().findViewById(R.id.toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switchLayout();
                switchIcon(item);
                return false;
            }
        });

        TokenManager.generateToken();
        controller = new Controller(this);

        return view;
    }

    @Override
    public void onFetchProgress(Track track) {
        searchFragment.getTracks().add(track);
        searchFragment.updateListAdapter();
    }

    @Override
    public void onFetchComplete() {
        searchFragment.updateListAdapter();
        if(favoritesFragment != null){
            favoritesFragment.updateListAdapter();
        }
    }

    class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(HomeFragment fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            if(position == 0){
                searchFragment = new SearchFragment(controller);
                return searchFragment;
            } else {
                favoritesFragment = new FavoritesFragment(controller);
                return favoritesFragment;
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

    private void switchLayout() {
        if(searchFragment != null){
            if (searchFragment.getGridLayoutManager().getSpanCount() == 1) {
                searchFragment.getGridLayoutManager().setSpanCount(3);
            } else {
                searchFragment.getGridLayoutManager().setSpanCount(1);
            }
            searchFragment.getListAdapter().notifyItemRangeChanged(0,  searchFragment.getListAdapter().getItemCount());
        }
        if(favoritesFragment != null){
            if (favoritesFragment.getGridLayoutManager().getSpanCount() != searchFragment.getGridLayoutManager().getSpanCount()) {
                favoritesFragment.getGridLayoutManager().setSpanCount( searchFragment.getGridLayoutManager().getSpanCount());
                favoritesFragment.getListAdapter().notifyItemRangeChanged(0,  favoritesFragment.getListAdapter().getItemCount());
            }
        }
    }

    private void switchIcon(MenuItem item) {
        Context context = getContext();
        if(searchFragment != null){
            if (searchFragment.getGridLayoutManager().getSpanCount() == 3) {
                item.setIcon(context.getDrawable(R.drawable.ic_list));
            } else {
                item.setIcon(context.getDrawable(R.drawable.ic_grid));
            }
        }
        if(favoritesFragment != null){
            if (favoritesFragment.getGridLayoutManager().getSpanCount() == 3) {
                item.setIcon(context.getDrawable(R.drawable.ic_list));
            } else {
                item.setIcon(context.getDrawable(R.drawable.ic_grid));
            }
        }
    }
}