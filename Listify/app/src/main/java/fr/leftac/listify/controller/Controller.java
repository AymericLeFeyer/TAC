package fr.leftac.listify.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.leftac.listify.model.api.ApiManager;
import fr.leftac.listify.model.api.TokenManager;
import fr.leftac.listify.model.pojo.Album;
import fr.leftac.listify.model.pojo.Artist;
import fr.leftac.listify.model.pojo.Track;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Controller {
    private TrackCallbackListener trackCallbackListener;
    private ApiManager apiManager;
    private JsonParser parser;
    private boolean isFav;

    public Controller(TrackCallbackListener listener) {
        trackCallbackListener = listener;
        apiManager = new ApiManager();
        parser = new JsonParser();
    }

    public void searchTracks(String q) {
        // Call the API
        apiManager.getSpotifyApi().searchTracks(TokenManager.getToken(), q, "track").enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                if (response.body() != null) {
                    // Parse the response
                    JsonObject responseBody = parser.parse(new Gson().toJson(response.body())).getAsJsonObject();

                    // Get the Json Array
                    JsonArray res = responseBody.getAsJsonObject("tracks").getAsJsonArray("items");

                    for (int i = 0; i < res.size(); i++) {

                        Track t = Track.jsonToTrack(res.get(i));
                        updateArtist(t.getArtist());
                        updateAlbum(t.getAlbum());

                        trackCallbackListener.onFetchProgress(t);
                    }

                } else {
                    Log.e("searchError", "response.body is null");
                }

                // Tell the view the query is over
                trackCallbackListener.onFetchComplete();

            }

            @Override
            public void onFailure(@NotNull Call<Object> call, @NotNull Throwable t) {

            }
        });
    }

    public void changeTrackDetails(Track track) {
        trackCallbackListener.onNewTrack(track);
    }

    public void updateArtist(Artist artist) {
        apiManager.getSpotifyApi().getArtist(TokenManager.getToken(), artist.getId()).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                if (response.body() != null) {
                    // Parse the response
                    JsonObject responseBody = parser.parse(new Gson().toJson(response.body())).getAsJsonObject();

                    // Update image
                    JsonArray images = responseBody.getAsJsonArray("images");

                    if (images.size() != 0) {
                        JsonObject img = images.get(0).getAsJsonObject();
                        artist.setImage(img.get("url").toString().replaceAll("\"", ""));
                    } else artist.setImage(null);


                    // Update popularity
                    JsonPrimitive popularity = responseBody.getAsJsonPrimitive("popularity");
                    artist.setPopularity(popularity.getAsInt());

                    // Update followers
                    JsonObject followers = responseBody.getAsJsonObject("followers");
                    artist.setFollowers(followers.getAsJsonPrimitive("total").getAsInt());

                    // Update genres
                    JsonArray genres = responseBody.getAsJsonArray("genres");
                    //TODO : Remplacer RealmList par List
                    RealmList<String> g = new RealmList<>();
                    for (JsonElement genre : genres) {
                        g.add(genre.getAsString());
                    }
                    artist.setGenres(g);


                } else {
                    Log.e("searchError", "response.body is null");
                }


            }

            @Override
            public void onFailure(@NotNull Call<Object> call, @NotNull Throwable t) {

            }
        });
    }

    public void updateAlbum(Album album) {
        apiManager.getSpotifyApi().getAlbum(TokenManager.getToken(), album.getId()).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                if (response.body() != null) {
                    // Parse the response
                    JsonObject responseBody = parser.parse(new Gson().toJson(response.body())).getAsJsonObject();

                    // Update tracks
                    JsonObject tracks = responseBody.getAsJsonObject("tracks");
                    JsonArray items = tracks.getAsJsonArray("items");

                    //TODO : Remplacer RealmList par List
                    RealmList<Track> g = new RealmList<>();
                    for (JsonElement t : items) {
                        JsonObject track = t.getAsJsonObject();
                        Track newTrack = new Track();
                        newTrack.setId(track.getAsJsonPrimitive("id").getAsString());
                        newTrack.setName(track.getAsJsonPrimitive("name").getAsString());
                        newTrack.setArtist(album.getArtist());
                        newTrack.setAlbum(album);
                        g.add(newTrack);
                    }
                    album.setTracks(g);


                } else {
                    Log.e("searchError", "response.body is null");
                }

            }

            @Override
            public void onFailure(@NotNull Call<Object> call, @NotNull Throwable t) {

            }
        });
    }

    public void updateTrack(Track track) {
        apiManager.getSpotifyApi().getTrack(TokenManager.getToken(), track.getId()).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                if (response.body() != null) {
                    // Parse the response
                    JsonObject responseBody = parser.parse(new Gson().toJson(response.body())).getAsJsonObject();

                    // Update tracks
                    JsonObject tracks = responseBody.getAsJsonObject("tracks");
                    JsonArray items = tracks.getAsJsonArray("items");


                } else {
                    Log.e("searchError", "response.body is null");
                }

            }

            @Override
            public void onFailure(@NotNull Call<Object> call, @NotNull Throwable t) {

            }
        });

    }


    public void saveTrackToBDD(Track track) {
        Log.e("controller", track.getName());

        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(realm1 -> {
                Track alreadyIn = realm1.where(Track.class).equalTo("id", track.getId()).equalTo("favorite", true).findFirst();
                if (alreadyIn == null) {
                    track.setFavDate(new Date());
                    realm1.copyToRealm(track);
                }
            });
        }
        trackCallbackListener.onFetchComplete();
    }

    public void removeTrackFromBDD(Track track) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(realm1 -> {
                Track result = realm1.where(Track.class).equalTo("id", track.getId()).equalTo("favorite", true).findFirst();
                if (result != null) {
                    track.setFavorite(false);
                    track.setFavDate(null);
                    result.deleteFromRealm();
                }
            });
        }
        trackCallbackListener.onFetchComplete();
    }

    public ArrayList<Track> getSavedTracks() {
        ArrayList<Track> savedTracks = new ArrayList<>();
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(realm1 -> {
                RealmResults<Track> results = realm1.where(Track.class).equalTo("favorite", true).findAll();


                for (Track t : results) {
                    if (!isAlreadyIn(t, savedTracks)) {

                        savedTracks.add(realm1.copyFromRealm(t));

                    }
                }

            });
        }
        return savedTracks;
    }

    public boolean isAlreadyIn(Track t, List<Track> list) {
        for (Track track : list) {
            if (track.getId().equals(t.getId())) {
                return true;
            }
        }
        return false;

    }

    public boolean isFavorite(Track t) {
        isFav = false;
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(realm1 -> {
                Track result = realm1.where(Track.class).equalTo("id", t.getId()).equalTo("favorite", true).findFirst();
                if (result != null) isFav = true;
            });
        }
        return isFav;
    }

    public interface TrackCallbackListener {
        void onFetchProgress(Track track);

        void onFetchComplete();

        void onNewTrack(Track track);
    }
}
