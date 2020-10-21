package fr.leftac.listify;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Base64;

import fr.leftac.listify.api.SpotifyService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    static final String CLIENT_ID = "3a4b3e391c6c458e8523e701c5becbd8";
    static final String CLIENT_SECRET = "35cd4fc0704d4bfbbde061e7c7ea4838";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://accounts.spotify.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SpotifyService api = retrofit.create(SpotifyService.class);

        byte[] credentials = (CLIENT_ID+":"+CLIENT_SECRET).getBytes();
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString(credentials);

        Call call = api.token("client_credentials", basicAuth);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                JsonParser parser = new JsonParser();
                JsonObject responseBody = parser.parse(new Gson().toJson(response.body())).getAsJsonObject();
                JsonElement token = responseBody.get("access_token");

                Log.i("response", token.toString());
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                t.printStackTrace();
                Log.e("error", "log");
            }
        });
    }
}