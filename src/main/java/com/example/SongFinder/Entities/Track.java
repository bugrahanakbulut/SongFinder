package com.example.SongFinder.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

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
    private String trackName;
    private String country;
    private String spotifyUri;
    private int rank;

    public String getSpotifyUri() {
        return spotifyUri;
    }

    public void setSpotifyUri(String spotifyUri) {
        this.spotifyUri = spotifyUri;
    }

    public long getDbId() {
        return this.dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public String getIdSpotify() {
        return this.idSpotify;
    }

    public void setIdSpotify(String idSpotify) {
        this.idSpotify = idSpotify;
    }

    public String getArtistName() {
        return  this.artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getTrackName() {
        return this.trackName;
    }

    public void setTrackName(String name) {
        this.trackName = name;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getRank() {
        return this.rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        StringBuilder trackToString = new StringBuilder();
        trackToString.append("TRACK NAME: ");
        trackToString.append(this.getTrackName());
        trackToString.append("\n");

        trackToString.append("ARTIST:");
        trackToString.append(this.getArtistName());

        return trackToString.toString();
    }
}
