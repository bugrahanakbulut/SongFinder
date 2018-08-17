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
    private static final String accessToken = "Bearer BQA5zFowfdaSlAPIOLPqAVmTBWBe6fHA5BZJbszQeQ3RUPlAcTk3hR_2Tkj06K-2ny6U1494vdxvSIV1BRZDFBDhnaCEHpeik73w7PKXxsljBudQYB-g16MoZ751t3DM07b0FcxT3KZXRHSWsv4bbQp4EQMvB2R3IYfgzWeHNTAlKPHT14LIWkuoVCjpX6ZzfcHKTniAnC5t3WwPfmsO9_gCgN92-KJbUA65utGFdHQYy-jxflIPmgaf5y0Y5YsijXQ4U6Sk3-aGI1MyIsMPFw" ;
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
