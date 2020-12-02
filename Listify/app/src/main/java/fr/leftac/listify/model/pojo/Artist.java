package fr.leftac.listify.model.pojo;

import com.google.gson.JsonElement;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

// Generate getters and setters with Lombok
@Getter
@Setter
public class Artist extends RealmObject {
    public String id;
    private String name;
    private String image;

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

