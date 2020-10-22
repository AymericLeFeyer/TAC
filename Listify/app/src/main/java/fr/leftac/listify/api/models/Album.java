package fr.leftac.listify.api.models;

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
}
