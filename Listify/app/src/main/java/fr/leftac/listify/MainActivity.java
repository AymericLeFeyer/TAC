package fr.leftac.listify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import fr.leftac.listify.controllers.ApiManager;
import fr.leftac.listify.controllers.TokenManager;


public class MainActivity extends AppCompatActivity {
    private Button tokenButton, searchButton;
    private TextView tv;

    //TODO: use fragments instead of activities
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tokenButton = findViewById(R.id.tokenButton);
        searchButton = findViewById(R.id.searchButton);
        tv = findViewById(R.id.token);

        tokenButton.setOnClickListener(v -> {
            TokenManager.generateToken(this);
        });

        searchButton.setOnClickListener(v -> {
            ApiManager api = new ApiManager();
            api.searchTracks("orelsan", "track", this);
        });

    }

    public void updateTokenField(String s) {
        tv.setText(s);
    }
}