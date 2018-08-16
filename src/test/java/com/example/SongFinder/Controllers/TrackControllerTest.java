package com.example.SongFinder.Controllers;

import com.example.SongFinder.Entities.Track;
import com.example.SongFinder.Repositories.TrackRepository;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrackControllerTest {

    @Test
    public void trackFinder() {
        //AUTOWIRED REPO
        TrackRepository tr = null;
        TrackController tc = new TrackController(tr);
        TrackController.FindPopularSongsRequest request = new TrackController.FindPopularSongsRequest();
        request.setCountry("turkey");
        tc.trackFinder(request);
    }
}