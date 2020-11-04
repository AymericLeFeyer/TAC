package fr.leftac.listify.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fr.leftac.listify.model.api.ApiManager;
import fr.leftac.listify.model.api.TokenManager;
import fr.leftac.listify.model.pojo.Artist;
import fr.leftac.listify.model.pojo.Track;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Controller {
    private static final String TAG = Controller.class.getSimpleName();
    private TrackCallbackListener trackCallbackListener;
    private ApiManager apiManager;
    private JsonParser parser;
    private String token;

    public Controller(TrackCallbackListener listener) {
        trackCallbackListener = listener;
        apiManager = new ApiManager();
        parser = new JsonParser();
        token = "Bearer " + TokenManager.getToken();
    }

    public void searchTracks(String q) {
        // Call the API
        apiManager.getSpotifyApi().searchTracks(token, q, "track").enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                // Parse the response
                Log.i(TAG, response.toString());
                JsonObject responseBody = parser.parse(new Gson().toJson(response.body())).getAsJsonObject();

                // Get the Json Array
                JsonArray res = responseBody.getAsJsonObject("tracks").getAsJsonArray("items");

                // TODO: Search if we can increase the number of results (more than 20)
                for (int i = 0; i < res.size(); i++) {

                    Track t = Track.jsonToTrack(res.get(i));

                    trackCallbackListener.onFetchProgress(t);
                }

                // Tell the view the query is over
                trackCallbackListener.onFetchComplete();

                // TODO: Give back the array list to the activity (with act argument)

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });


    }

    public interface TrackCallbackListener {
        void onFetchProgress(Track track);

        void onFetchComplete();
    }
}
