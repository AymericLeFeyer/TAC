package fr.leftac.listify.model.pojo;

import com.google.gson.JsonElement;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Album extends RealmObject {
    public String id;
    private String name;
    private Artist artist;
    private RealmList<Track> tracks;
    private String image;

    public Album() {
        this.name = "";
        this.artist = null;
        this.tracks = null;
        this.image = "";
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

    public RealmList<Track> getTracks() {
        return tracks;
    }

    public void setTracks(RealmList<Track> tracks) {
        this.tracks = tracks;
    }

    public static Album jsonToAlbum(JsonElement res, Artist artist) {
        Album a = new Album();

        String name = res.getAsJsonObject()
                .get("album")
                .getAsJsonObject()
                .get("name")
                .toString()
                .replaceAll("\"", "");
        a.setName(name);

        a.setArtist(artist);

        String image = res.getAsJsonObject()
                .get("album")
                .getAsJsonObject()
                .getAsJsonArray("images")
                .get(0)
                .getAsJsonObject()
                .get("url")
                .toString()
                .replaceAll("\"", "");
        a.setImage(image);

        String id = res.getAsJsonObject()
                .get("album")
                .getAsJsonObject()
                .get("id")
                .toString()
                .replaceAll("\"", "");
        a.setId(id);


        return a;


    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setId(String id) {
        this.id = id;
    }
}
