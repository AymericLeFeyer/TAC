package fr.leftac.listify;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import fr.leftac.listify.controller.ConnectivityStatusReceiver;
import fr.leftac.listify.model.DrawableViewModel;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    AppBarConfiguration appBarConfiguration;
    NavController navController;
    NavHostFragment navHostFragment;
    NavigationView navigationView;
    Toolbar toolbar;
    ConnectivityStatusReceiver connectivityStatusReceiver;
    DrawableViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }


        appBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_graph, R.id.nav_host_fragment).build();

        navigationView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Initialize Realm
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .allowWritesOnUiThread(true)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        viewModel = new ViewModelProvider(this).get(DrawableViewModel.class);

        connectivityStatusReceiver = new ConnectivityStatusReceiver();
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mode_view, menu);
        viewModel.getSelectedItem().observe(this, item -> {
            menu.getItem(0).setIcon(item);
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityStatusReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connectivityStatusReceiver != null) {
            // unregister receiver
            unregisterReceiver(connectivityStatusReceiver);
        }
    }
}