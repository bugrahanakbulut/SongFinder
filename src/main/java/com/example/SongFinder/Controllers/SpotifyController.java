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

    private static final String accessToken = "Bearer BQDoExkLtJnCqTrz6WnN7Z_-POr-zIeDnzfP8GZRDQGWzgUZsb5bv_3WfEmUOv54g773jc-9tNk8oImYNxw-hxQYwlV__57vln6l2yRkat7S8D2ef4RUOR5FEu_-zUoDdip0zg2ciTOIukvAsHpT2rT-Y5kOooMHXItDbbPmqiW7_CJMdwaoT2l85uXMtdFDlRLQhtqq_PF2v2PaH3BzwCJ7vTOsocIdf4Xa7CN0up7KerWgajh5gqAu8Ylzewwz6d_FX2OO_h5K3pQUTMk8fw" ;
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder().
                                                setAccessToken(accessToken).build();

    private String autToken;

    public String getAutToken() {
        return autToken;
    }

    public void setAutToken(String autToken) {
        this.autToken = autToken;
    }

    public SpotifyController(){}

    public SpotifyController(String autToken){
        this.autToken = autToken;
    }

    public SpotifyTrackList searchTrack(String key, String type){
        String query = key.replace(" ", "+");
        query = query.toLowerCase();

        StringBuilder http = new StringBuilder();
        http.append("https://api.spotify.com/v1/search?");
        http.append("q=" + query);
        http.append("&type=" + type);
        http.append("&limit=10");
        http.append("&offset=0");

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + this.getAutToken());
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
