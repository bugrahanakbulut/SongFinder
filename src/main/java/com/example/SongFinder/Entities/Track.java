package com.example.SongFinder.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Map;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Track {
    @Id
    @GeneratedValue
    private long dbId;
    private String idSpotify;
    private String artistName;
    private String name;

    @SuppressWarnings("unchecked")
    @JsonProperty("artist")
    private void unpackNestedObjects(Map<String,Object> artist){
        this.setArtistName((String) artist.get("name"));
    }

    public long getDbId() {
        return dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public String getIdSpotify() {
        return idSpotify;
    }

    public void setIdSpotify(String idSpotify) {
        this.idSpotify = idSpotify;
    }

    public String getArtistName() {
        return  artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    @Override
    public String toString() {
        StringBuilder trackToString = new StringBuilder();
        trackToString.append("TRACK NAME: ");
        trackToString.append(this.getName());
        trackToString.append("\n");

        trackToString.append("ARTIST:");
        trackToString.append(this.getArtistName());
        trackToString.append("\n");

        return trackToString.toString();
    }
}
