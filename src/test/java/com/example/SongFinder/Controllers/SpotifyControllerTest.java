package com.example.SongFinder.Controllers;

import com.example.SongFinder.Entities.SpotifyTrackList;
import com.example.SongFinder.Entities.Track;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpotifyControllerTest {

    @Test
    public void searchTrackTest0() {
        SpotifyController controller = new SpotifyController("invalid_token");
        SpotifyTrackList list = controller.searchTrack("batsin bu dunya", "track");
        for(Track t : list.getTrackList())
            System.out.println(t.toString());
    }

    @Test
    public void searchTrackTest1() {
        SpotifyController controller = new SpotifyController("invalid_token");
        SpotifyTrackList list = controller.searchTrack("birthday", "track");
        for(Track t : list.getTrackList())
            System.out.println(t.toString());
    }

    @Test
    public void searchTrackTest2() {
        SpotifyController controller = new SpotifyController("invalid_token");
        SpotifyTrackList list = controller.searchTrack("daydreaming", "track");
        for(Track t : list.getTrackList())
            System.out.println(t.toString());
    }
}