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
    private static final String accessToken = "BQCWIcgcS3PB7esPTFGCl3PFPzDmDj7L2BnFhnZgeh2-Y3EExeuHOfSTM2uafb0xgDVdVqqAfZ-ktavRjdTU8m4ibVHGr5PO-GAExdPe1ZYRA9NVxNpcA3ZsFOD8p85Y2-JcfRE97ivfNT417bAmoA49UvY40IYjH4SahOCem5BtbINoxxIpTpN-rE-ve-gOEYLKhQUHp_lO5PWBwAMtrUH4tAH4zzfd4HoY0rUmIiiNnMgMW9I2VM5XgeXpcMQoHKZ3UZKcqui56VDMnBWb4w" ;
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
        String query = key.replace(" ", "+");
        query = query.toLowerCase();

        StringBuilder http = new StringBuilder();
        http.append("https://api.spotify.com/v1/search?");
        http.append("q=" + query);
        http.append("&type=" + type);
        http.append("&limit=10");
        http.append("&offset=0");

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + accessToken);
        // System.out.println(http.toString());
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
}
