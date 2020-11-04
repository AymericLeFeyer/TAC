package fr.leftac.listify.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import fr.leftac.listify.R;


public class MainActivity extends AppCompatActivity {

    //TODO: use fragments instead of activities
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}