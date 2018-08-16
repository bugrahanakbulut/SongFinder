package com.example.SongFinder.Controllers;

import com.example.SongFinder.Entities.SpotifyTrackList;
import com.example.SongFinder.Entities.TrackList;
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
    private static final String accessToken = "BQDIFGDTKUIk6f9jGjahqbshCJFvOHYKhOtLEf_N-2tU9qSwSPhd2CgfP1gXK1oSjQvFXPgrFSJMb2BnaHceP-87qNMK8QUrgyUonbhDZ97Fl3zpV8A8gv1pThKkRSYOuS2avwRr7d0CCkifxfXcYxWYeN5MZIndoJ8PAqdvtdHxCUJwWkRwBkmEShLp64jtuNZVQNNu6aoX4y70qVLAUQFFhCPsSZJz_NX8xVvTnT4AoEPv4tQd9_lNfRkgPLshWvTTMbLGKGL-dZLsIwYSaw" ;
    private static final String type = ModelObjectType.TRACK.getType();

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder().
                                                setAccessToken(accessToken).build();

    public static void searchitem(String key){
        SearchItemRequest searchItemRequest = spotifyApi.searchItem(key, type).limit(20).offset(0).build();
        try{
            SearchResult searchResult =  searchItemRequest.execute();
            System.out.println("Total tracks: " + searchResult.getTracks().getTotal());
            Track[] tracks = searchResult.getTracks().getItems();
            for(Track t : tracks)
                System.out.println(t.getName());
        } catch (SpotifyWebApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SpotifyTrackList searchTrack(String key, String type){
        StringBuilder http = new StringBuilder();
        http.append("https://api.spotify.com/v1/search?");
        http.append("q=" + key);
        http.append("&type=" + type);
        http.append("&limit=10");
        http.append("&offset=0");

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + accessToken);

        SpotifyTrackList trackList = RequestController.getRequest(http.toString(), SpotifyTrackList.class, header);
        return trackList;

       /*
        try {
            URL url = new URL(http.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
            connection.setRequestMethod("GET");
            InputStreamReader in = new InputStreamReader(connection.getInputStream());

            char[] buffer = new char[1024];
            int read = 0;
            while ((read = in.read(buffer)) != -1) {
                jsonResults.append(buffer, 0, read);
            }

            JSONObject jsonObject = new JSONObject(jsonResults.toString());
            System.out.println(jsonObject.toString());
            JSONObject items = jsonObject.getJSONObject("tracks");
            JSONArray itemList = items.getJSONArray("items");
            for(int i = 0; i < itemList.length(); i++){
                JSONObject smpObj = itemList.getJSONObject(i);
                System.out.println(smpObj.getString("name"));
                JSONArray artArr = smpObj.getJSONArray("artists");
                for(int x = 0; x < artArr.length(); x++)
                    System.out.println("ART: " + artArr.getJSONObject(x).get("name"));
            }

            System.out.println(items.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        */
    }

    public static void main(String[] args){
        searchTrack("Biliyorum", "track");
    }
}
