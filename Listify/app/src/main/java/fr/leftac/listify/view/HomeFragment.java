package fr.leftac.listify.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.tabs.TabLayout;

import fr.leftac.listify.R;

public class HomeFragment extends Fragment {

    ViewPager2 viewPager;
    ScreenSlidePagerAdapter adapter;
    TabLayout tabLayout;

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




        return view;
    }

    class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(HomeFragment fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {

            return (position == 0) ? new SearchFragment() : new FavoritesFragment();
        }

        @Override
        public int getItemCount() {
            return 2;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("destroy", "destroy");
    }
}