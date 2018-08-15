package com.example.SongFinder.Entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@RequestMapping(value = "/artists")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist {
    @Id
    @GeneratedValue
    private long dbId;
    private String idSpotify;
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder artistToString = new StringBuilder();
        artistToString.append("ARTIST NAME: ");
        artistToString.append(this.getName());
        artistToString.append("\n");
        return artistToString.toString();
    }
}
