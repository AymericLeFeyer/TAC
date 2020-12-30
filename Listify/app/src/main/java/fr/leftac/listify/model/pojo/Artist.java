package fr.leftac.listify.model.pojo;

import com.google.gson.JsonElement;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Artist extends RealmObject {
    public String id;
    private String name;
    private String image;
    private int followers;
    private RealmList<String> genres;
    private int popularity;

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public RealmList<String> getGenres() {
        return genres;
    }

    public void setGenres(RealmList<String> genres) {
        this.genres = genres;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public Artist() {
        this.name = "";
        this.image = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static Artist jsonToArtist(JsonElement res) {
        Artist a = new Artist();

        String name = res.getAsJsonObject()
                .getAsJsonArray("artists")

                .get(0)
                .getAsJsonObject()
                .get("name")
                .toString()
                .replaceAll("\"", "");
        a.setName(name);

        String id = res.getAsJsonObject()
                .getAsJsonArray("artists")

                .get(0)
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

    public void setId(String id) {
        this.id = id;
    }
}

