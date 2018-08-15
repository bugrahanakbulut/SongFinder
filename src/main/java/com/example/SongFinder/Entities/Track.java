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
@JsonRootName("tracks")
public class Track {
    @Id
    @GeneratedValue
    private long dbId;
    private String idSpotify;
    private String artistName;
    private String trackName;
    private String country;
    private int rank;


    @SuppressWarnings("unchecked")
    @JsonProperty("artist")
    private void unpackArtisObject(Map<String,Object> artist){
        this.setArtistName((String) artist.get("name"));
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("name")
    private void getTrackNameJson(String trackName){
        this.setTrackName(trackName);
    }
    @SuppressWarnings("unchecked")
    @JsonProperty("@attr")
    private void getTrackRank(Map<String, Object> trackAttr){
        this.setRank(Integer.valueOf((String) (trackAttr.get("rank"))));
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

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String name) {
        this.trackName = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getRank() {
        return rank;
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
