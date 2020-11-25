package fr.leftac.listify.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import fr.leftac.listify.model.api.ApiManager;
import fr.leftac.listify.model.api.TokenManager;
import fr.leftac.listify.model.pojo.Album;
import fr.leftac.listify.model.pojo.Artist;
import fr.leftac.listify.model.pojo.Track;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Controller {
    private static final String TAG = Controller.class.getSimpleName();
    private TrackCallbackListener trackCallbackListener;
    private ApiManager apiManager;
    private JsonParser parser;

    public Controller(TrackCallbackListener listener) {
        trackCallbackListener = listener;
        apiManager = new ApiManager();
        parser = new JsonParser();
    }

    public void searchTracks(String q) {
        // Call the API
        apiManager.getSpotifyApi().searchTracks(TokenManager.getToken(), q, "track").enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(response.body() != null){
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

                    // TODO: Give back the array list to the activity (with act argument)

                } else {
                    Log.e("searchError", "response.body is null");
                }

                // Tell the view the query is over
                trackCallbackListener.onFetchComplete();

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    public void saveTrackToBDD(Track track){
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Track alreadyIn = realm.where(Track.class).equalTo("id", track.getId()).findFirst();
                    if(alreadyIn == null){
                        realm.copyToRealm(track);
                    }
                }
            });
        } finally {
            realm.close();
        }
        trackCallbackListener.onFetchComplete();
    }

    public void removeTrackFromBDD(Track track){
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Track result = realm.where(Track.class).equalTo("id", track.getId()).findFirst();
                    if(result != null){
                        track.setFavorite(false);
                        result.deleteFromRealm();
                    }
                }
            });
        } finally {
            realm.close();
        }
        trackCallbackListener.onFetchComplete();
    }

    public ArrayList<Track> getSavedTracks(){
        ArrayList<Track> savedTracks = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<Track> results = realm.where(Track.class).findAll();
                    savedTracks.addAll(realm.copyFromRealm(results));
                }
            });
        } finally {
            realm.close();
        }
        return savedTracks;
    }

    public interface TrackCallbackListener {
        void onFetchProgress(Track track);

        void onFetchComplete();
    }
}
