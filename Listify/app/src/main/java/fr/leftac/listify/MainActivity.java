package fr.leftac.listify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;


import fr.leftac.listify.api.SpotifyService;
import fr.leftac.listify.models.TracksPager;
import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.helloButton);

        button.setOnClickListener(v -> {

            String token = "BQBhaHocMZoZOAhrq_RQSPPVBzdy70akBupF2nJUmcuX8cVoub8HqiinEuiDgS6_x-Yyg3EDYIqhSA4rdkY";


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.spotify.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            SpotifyService client = retrofit.create(SpotifyService.class);

            Call<TracksPager> call = client.track("Bearer " + token, "macarena", "track");


            call.enqueue(new Callback<TracksPager>() {

                @SneakyThrows
                @Override
                public void onResponse(Call<TracksPager> call, Response<TracksPager> response) {
                    TracksPager tp = response.body();
                    Log.i("api", tp.tracks.items.size() + "");
                }

                @Override
                public void onFailure(Call<TracksPager> call, Throwable t) {
                    t.printStackTrace();

                }
            });


        });

    }
}