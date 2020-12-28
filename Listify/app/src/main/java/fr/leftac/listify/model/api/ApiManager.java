package fr.leftac.listify.model.api;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private SpotifyApi spotifyApi;

    public SpotifyApi getSpotifyApi() {
        if (spotifyApi == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(String.class, new StringDeserializer());

            String BASE_URL = "https://api.spotify.com/";
            spotifyApi = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(SpotifyApi.class);
        }
        return spotifyApi;
    }
}
