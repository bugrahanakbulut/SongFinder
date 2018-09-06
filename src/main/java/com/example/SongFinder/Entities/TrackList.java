package com.example.SongFinder.Entities;

import com.example.SongFinder.Entities.Track;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackList {
    private ArrayList<Track> trackList;

    public TrackList(){
        this.trackList = new ArrayList<>();
    }

    public ArrayList<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(ArrayList<Track> trackList) {
        this.trackList = trackList;
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("tracks")
    private void unwrapTrackList(Map<String, Object> response){
        this.trackList = new ArrayList<>();
        ArrayList<LinkedHashMap> jsonTrackList = (ArrayList<LinkedHashMap>) response.get("track");
        // System.out.println(jsonTrackList.get(0));
        for(LinkedHashMap map : jsonTrackList){
            Track track = new Track();
            track.setTrackName((String) map.get("name"));
            LinkedHashMap artist = (LinkedHashMap) map.get("artist");
            track.setArtistName((String) artist.get("name"));
            LinkedHashMap attr = (LinkedHashMap) map.get("@attr");
            this.getTrackList().add(track);
        }
    }

    @Override
    public String toString() {
        return ("SIZE = " + String.valueOf(this.trackList.size()));
    }
}
