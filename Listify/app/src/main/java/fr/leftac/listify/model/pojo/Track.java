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
    private String duration;

    public Track(String name) {
        this.id = "1";
        this.name = name;
        this.artist = null;
        this.album = null;
        this.popularity = 0;
        this.duration = "";

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

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public static Track jsonToTrack(JsonElement res) {
        // Get the name
        String name = res.getAsJsonObject()
                .get("name")
                .toString()
                .replaceAll("\"", "");

        Track t = new Track(name);

        // Get the artist
        Artist artist = Artist.jsonToArtist(res);
        t.setArtist(artist);

        // Get the album
        Album album = Album.jsonToAlbum(res, artist);
        t.setAlbum(album);

        return t;
    }
}
