package fr.leftac.listify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import fr.leftac.listify.api.SpotifyService;
import okhttp3.ResponseBody;
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

            String token = "BQCrOtXIDOJa-SpbHeov7qWT9em1KMh9vh_D2VD5bhzroOs4jxNiAdLUW2zBYh4RazuKaUzJG6iiqLLtl5y8Hy3KH9GwQJZeDmoqmz7W3j-35mFdCmKDgbdISGM7INwP3NK_GtGR_4tkyluVPJyJHnjVjYh4Qs7BYFOIQ68ls-g_55UfNzC_FAZfxk6jBNffCmCFMHETrA9p";


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.spotify.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            SpotifyService client = retrofit.create(SpotifyService.class);

            Call<ResponseBody> call = client.track(token, "macarena", "artist");

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.i("coucou", response.toString());
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();

                }
            });
        });




    }
}