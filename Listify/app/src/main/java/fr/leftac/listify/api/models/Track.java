package fr.leftac.listify.api.models;

import lombok.Getter;
import lombok.Setter;

// Generate getters and setters with Lombok
@Getter
@Setter
public class Track {
    private String id;
    private String name;
    private Artist artist;
    private Album album;
    private int popularity;
    private String duration;

}
