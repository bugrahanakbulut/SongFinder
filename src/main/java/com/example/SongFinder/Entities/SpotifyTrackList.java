package com.example.SongFinder.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyTrackList {
    private ArrayList<Track> trackList;

    public SpotifyTrackList() {
        this.trackList = new ArrayList<>();
    }

    public ArrayList<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(ArrayList<Track> trackList) {
        this.trackList = trackList;
    }

    @JsonProperty("tracks")
    public void updateTrackList(Map<String, Object> response){
        this.trackList = new ArrayList<>();
        ArrayList<LinkedHashMap> mapTrackList = (ArrayList<LinkedHashMap>) response.get("items");
        for(Map map : mapTrackList){
            Track track = new Track();
            track.setTrackName((String) map.get("name"));

            ArrayList<LinkedHashMap> artists = (ArrayList<LinkedHashMap>) map.get("artists");
            for (LinkedHashMap artist : artists){
                // WATCH OUT IT OVERWRITES
                track.setArtistName((String) artist.get("name"));
            }
            this.getTrackList().add(track);
            //System.out.println(track.toString());
        }
    }

    @Override
    public String toString() {
        return "SIZE : " + this.getTrackList().size();
    }
}
