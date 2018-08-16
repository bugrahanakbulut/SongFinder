package com.example.SongFinder.Controllers;

import com.example.SongFinder.Entities.SpotifyTrackList;
import com.example.SongFinder.Entities.Track;
import com.example.SongFinder.Entities.TrackList;
import com.example.SongFinder.Repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/track")
public class TrackController {

    public static class FindPopularSongsRequest {
        private String country;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    private ArrayList<Track> trackList;
    private TrackRepository trackRepo;

    @Autowired
    public TrackController(TrackRepository trackRepo){
        this.trackRepo = trackRepo;
        this.trackList = new ArrayList<>();
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Track> findAllTracks(){
        return trackRepo.findAll();
    }

    @RequestMapping(value = "/al", method = RequestMethod.GET)
    public ArrayList<Track> getArrayList(){
        for(Track t : trackList)
            System.out.println(t.toString());
        return trackList;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void trackFinder(@RequestBody FindPopularSongsRequest requestBody){
        String requesBase = "http://ws.audioscrobbler.com/2.0/?method=geo.gettoptracks&country=";
        requesBase += requestBody.getCountry();
        requesBase += "&api_key=56ad71507512a288c28c1fffb1be0a19";
        requesBase += "&limit=10";
        requesBase += "&format=json";
        // System.out.println(requesBase);
        TrackList popularTracks = RequestController.getRequest(requesBase, TrackList.class, null);
        for (Track t : popularTracks.getTrackList()) {
            t.setCountry(requestBody.getCountry());
            this.trackList.add(t);
        }

        trackRepo.save(popularTracks.getTrackList());
    }

    public void searchTracks(TrackList trackList2Search){
        ArrayList<Track> founded = new ArrayList<>();
        for (Track searchItem : trackList2Search.getTrackList()){
            SpotifyTrackList searchResult = SpotifyController.searchTrack(searchItem.getTrackName(), "track");
            for(Track result : searchResult.getTrackList()){
                if(result.getArtistName().equals(searchItem.getArtistName())
                        && result.getTrackName().equals(searchItem.getTrackName())){
                    founded.add(result);
                    searchResult.getTrackList().remove(result);
                }
            }

        }
    }
}


