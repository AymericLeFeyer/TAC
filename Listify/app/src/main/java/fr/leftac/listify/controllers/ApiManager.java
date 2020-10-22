package fr.leftac.listify.controllers;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import fr.leftac.listify.MainActivity;
import fr.leftac.listify.api.models.Track;
import fr.leftac.listify.api.services.SpotifyService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {
    private Retrofit retrofit;
    private SpotifyService client;
    private JsonParser parser;

    public ApiManager() {
        // Initialize the retrofit object
        this.retrofit = new Retrofit.Builder()
                // TODO: Hide this URL in a git ignored file
                .baseUrl("https://api.spotify.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Initialize the service
        this.client = this.retrofit.create(SpotifyService.class);

        // Initialize the parser
        this.parser = new JsonParser();
    }

    public void searchTracks(String search, String type, MainActivity act) {
        // Preparing the call
        Call<Object> call = client.track("Bearer " + TokenManager.token, search, type);
        Log.i("test", "Bearer " + TokenManager.token);

        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                // Parse the response
                JsonObject responseBody = parser.parse(new Gson().toJson(response.body())).getAsJsonObject();

                // Get the Json Array
                JsonArray res = responseBody.getAsJsonObject("tracks").getAsJsonArray("items");

                // TODO: For earch elements (20), create a Track object in the track arraylist
                // TODO: Search if we can increase the number of results (more than 20)
                ArrayList tracks = new ArrayList();

                // Get the name
                Log.i("searchAPI",res.get(0)
                                .getAsJsonObject()
                                .get("name")
                                .toString()
                                .replaceAll("\"", ""));

                // TODO: Give back the array list to the activity (with act argument)
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                t.printStackTrace();
                Log.i("searchAPI", "Erreur sur l'API");

            }
        });





    }
}
