package com.example.SongFinder.Controllers;

import com.example.SongFinder.Entities.Artist;
import com.example.SongFinder.Entities.Track;
import com.example.SongFinder.Repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/track")
public class TrackController {

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

    @RequestMapping(value = "/country={country}", method = RequestMethod.PUT)
    public void trackFinder(@PathVariable String country){
        String requesBase = "http://ws.audioscrobbler.com/2.0/?method=geo.gettoptracks&country=";
        requesBase += country;
        requesBase += "&api_key=56ad71507512a288c28c1fffb1be0a19";
        requesBase += "&limit=25";
        requesBase += "&format=json";
        String[] properties = {"tracks", "track"};
        // System.out.println(requesBase);
        List<Track> popularTracks = RequestController.getRequest(requesBase, Track.class, properties);
        for (Track t : popularTracks) {
            t.setCountry(country);
            this.trackList.add(t);
        }
        trackRepo.save(popularTracks);
    }
}
