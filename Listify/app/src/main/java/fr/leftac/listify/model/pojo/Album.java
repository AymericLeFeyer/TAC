package fr.leftac.listify.model.pojo;

import com.google.gson.JsonElement;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

// Generate getters and setters with Lombok
@Getter
@Setter
public class Album {
    private String name;
    private Artist artist;
    private List<Track> tracks;
    private String image;

    public Album(String name, Artist artist, String image) {
        this.name = name;
        this.artist = artist;
        this.tracks = null;
        this.image = image;
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

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static Album jsonToAlbum(JsonElement res, Artist artist) {
        String albumName = res.getAsJsonObject()
                .get("album")
                .getAsJsonObject()
                .get("name")
                .toString()
                .replaceAll("\"", "");

        String albumImage = res.getAsJsonObject()
                .get("album")
                .getAsJsonObject()
                .getAsJsonArray("images")
                .get(0)
                .getAsJsonObject()
                .get("url")
                .toString()
                .replaceAll("\"", "");

        Album a = new Album(albumName, artist, albumImage);

        return a;


    }
}
