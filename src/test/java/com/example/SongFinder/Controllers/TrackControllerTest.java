package com.example.SongFinder.Controllers;

import com.example.SongFinder.Entities.Track;
import com.example.SongFinder.Repositories.TrackRepository;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.*;

public class TrackControllerTest {

    @Test
    public void trackFinderTest0() {
        TrackController tc = new TrackController();
        tc.trackFinder("turkey");
    }

    @Test
    public void trackFinderTest1() {
        TrackController tc = new TrackController();
        tc.trackFinder("spain");
    }
}