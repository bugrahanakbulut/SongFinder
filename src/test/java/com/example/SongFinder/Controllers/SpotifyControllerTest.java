package com.example.SongFinder.Controllers;

import com.example.SongFinder.Entities.SpotifyTrackList;
import com.example.SongFinder.Entities.Track;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpotifyControllerTest {

    @Test
    public void searchTrackTest0() {
        SpotifyTrackList list = SpotifyController.searchTrack("batsin bu dunya", "track");
        for(Track t : list.getTrackList())
            System.out.println(t.toString());
    }

    @Test
    public void searchTrackTest1() {
        SpotifyTrackList list = SpotifyController.searchTrack("birthday", "track");
        for(Track t : list.getTrackList())
            System.out.println(t.toString());
    }

    @Test
    public void searchTrackTest2() {
        SpotifyTrackList list = SpotifyController.searchTrack("daydreaming", "track");
        for(Track t : list.getTrackList())
            System.out.println(t.toString());
    }
}