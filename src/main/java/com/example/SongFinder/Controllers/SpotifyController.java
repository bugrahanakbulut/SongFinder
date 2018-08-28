package com.example.SongFinder.Controllers;

import com.example.SongFinder.Entities.SpotifyTrackList;
import com.example.SongFinder.Entities.TrackList;
import com.example.SongFinder.Exceptions.BadRequestException;
import com.example.SongFinder.Exceptions.UnauthorizedRequestException;
import com.google.gson.JsonObject;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.special.SearchResult;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.search.SearchItemRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpotifyController {
    private static final String accessToken = "Bearer BQCJFkrMHevEtruzziNoa4LZDyIEhgdAhsRW4jPOLQwiB6oLStTtFb_aFbLPIq-hVi1QeXabz2S1uFdKsLK8qPiy2hrUsKdB8G56B6l7Mn4YUA0itB2OUOda0mVgKJ7x04okm4dXmtp7vi9kXkhjJVvgAcCLIrYiJtlf_fsFjceNi14onInp20-5QZzItI5x5jxSGzuH5lYGy6_tN0OKRoJ_DVT3A-cj6TVoWscJE1kMnHTha61vJPfzviJ1i1FIb0I8tZRSNVy_gcQ3fk6i6A" ;
    private static final String type = ModelObjectType.TRACK.getType();

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder().
                                                setAccessToken(accessToken).build();


    public static SpotifyTrackList searchTrack(String key, String type){
        String query = key.replace(" ", "+");
        query = query.toLowerCase();

        StringBuilder http = new StringBuilder();
        http.append("https://api.spotify.com/v1/search?");
        http.append("q=" + query);
        http.append("&type=" + type);
        http.append("&limit=10");
        http.append("&offset=0");

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", accessToken);
        // System.out.println(http.toString());
        try {
            SpotifyTrackList trackList = RequestController.getRequest(http.toString(), SpotifyTrackList.class, header);
            return trackList;
        } catch (BadRequestException | UnauthorizedRequestException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
