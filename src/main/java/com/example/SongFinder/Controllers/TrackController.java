package com.example.SongFinder.Controllers;

import com.example.SongFinder.Entities.SpotifyTrackList;
import com.example.SongFinder.Entities.Track;
import com.example.SongFinder.Entities.TrackList;
import com.example.SongFinder.Exceptions.BadRequestException;
import com.example.SongFinder.Exceptions.UnauthorizedRequestException;
import com.example.SongFinder.Repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO TEST : JSON DESERIALIZER
 *      TEST : APP
 * TODO WEB INTERFACE
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/track")
public class TrackController {

    public static class FindPopularSongsRequest {
        private String country;
        private String autKey;

        public String getAutKey(){
            return autKey;
        }

        public void setAutKey(String autKey){
            this.autKey = autKey;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    private ArrayList<Track> trackList;
    private TrackRepository trackRepo;

    public TrackController(){
        this.trackList = new ArrayList<>();
    }

    @Autowired
    public TrackController(TrackRepository trackRepo){
        this.trackRepo = trackRepo;
        this.trackList = new ArrayList<>();
        // this.trackDAO = trackDAO;
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
    public List<Track> trackFinder(@RequestBody FindPopularSongsRequest requestBody){
        String requesBase = "http://ws.audioscrobbler.com/2.0/?method=geo.gettoptracks&country=";
        requesBase += requestBody.getCountry();
        requesBase += "&api_key=56ad71507512a288c28c1fffb1be0a19";
        requesBase += "&limit=10";
        requesBase += "&format=json";
        // System.out.println(requesBase);
        try {
            TrackList popularTracks = RequestController.getRequest(requesBase, TrackList.class, null);
            for (Track t : popularTracks.getTrackList()) {
                t.setCountry(requestBody.getCountry());
                this.trackList.add(t);
            }
            ArrayList<Track> results = searchTracks(popularTracks, requestBody.getAutKey());

            for(Track t : results){
                List<Track> duplicate = trackRepo.findByIdSpotify(t.getIdSpotify());
                if(duplicate.size() == 0)
                    trackRepo.save(t);
            }


        } catch (BadRequestException | UnauthorizedRequestException e){
            e.printStackTrace();
        }

        return trackRepo.findByCountry(requestBody.getCountry());
    }


    // FOR UNIT TESTING
    public ArrayList<Track> trackFinder(String country, String autToken){
        String requesBase = "http://ws.audioscrobbler.com/2.0/?method=geo.gettoptracks&country=";
        requesBase += country;
        requesBase += "&api_key=56ad71507512a288c28c1fffb1be0a19";
        requesBase += "&limit=10";
        requesBase += "&format=json";
        try {
            TrackList popularTracks = RequestController.getRequest(requesBase, TrackList.class, null);
            for (Track t : popularTracks.getTrackList()) {
                t.setCountry(country);
                this.trackList.add(t);
                System.out.println(t.toString());
            }
            ArrayList<Track> results = searchTracks(popularTracks, autToken);
            return results;
        } catch (BadRequestException | UnauthorizedRequestException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Track> searchTracks(TrackList trackList2Search, String autToken){
        ArrayList<Track> founded = new ArrayList<>();
        // System.out.println("trackList2Search.getTrackList().size() : " + trackList2Search.getTrackList().size());
        for (Track searchItem : trackList2Search.getTrackList()){
            // System.out.println(searchItem.toString());
            SpotifyController controllerS = new SpotifyController(autToken);
            SpotifyTrackList searchResult = controllerS.searchTrack(searchItem.getTrackName(), "track");
            // System.out.println("searchResult.getTrackList().size()" + searchResult.getTrackList().size());
            if(searchResult != null){
                for(Track result : searchResult.getTrackList()){
                    if(result.getArtistName().toLowerCase().equals(searchItem.getArtistName().toLowerCase())
                            && result.getTrackName().toLowerCase().equals(searchItem.getTrackName().toLowerCase())){
                        result.setCountry(searchItem.getCountry());
                        founded.add(result);
                        // searchResult.getTrackList().remove(result);
                        break;
                    }
                }
            }
        }
        return founded;
    }
}


