package fr.leftac.listify.model.pojo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.Getter;
import lombok.Setter;

// Generate getters and setters with Lombok
@Getter
@Setter
public class Artist {
    private String name;
    private String image;

    public Artist(String name) {
        this.name = name;
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
        String artistName = res.getAsJsonObject()
                .getAsJsonArray("artists")

                .get(0)
                .getAsJsonObject()
                .get("name")
                .toString()
                .replaceAll("\"", "");

        return new Artist(artistName);
    }
}

