package fr.leftac.listify.model.pojo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

public class Track {
    private String id;
    private String name;
    private Artist artist;
    private Album album;
    private int popularity;
    private int duration;
    private boolean favorite;

    public Track() {
        this.id = "1";
        this.name = "";
        this.artist = null;
        this.album = null;
        this.popularity = 0;
        this.duration = 0;
        this.favorite = false;
    }

    // Generate getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public static Track jsonToTrack(JsonElement res) {
        Track t = new Track();

        // Get the ID
        String id = res.getAsJsonObject()
                .get("id")
                .toString()
                .replaceAll("\"", "");
        t.setId(id);

        // Get the name
        String name = res.getAsJsonObject()
                .get("name")
                .toString()
                .replaceAll("\"", "");
        t.setName(name);

        // Get the artist
        Artist artist = Artist.jsonToArtist(res);
        t.setArtist(artist);

        // Get the album
        Album album = Album.jsonToAlbum(res, artist);
        t.setAlbum(album);

        // Get the popularity
        int popularity = res.getAsJsonObject()
                .get("popularity")
                .getAsInt();
        t.setPopularity(popularity);

        // Get the popuplarity
        int duration = res.getAsJsonObject()
                .get("duration_ms")
                .getAsInt();
        t.setDuration(duration);



        return t;
    }
}
