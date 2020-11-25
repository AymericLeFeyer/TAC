package fr.leftac.listify.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.tabs.TabLayout;

import java.util.Set;

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
}