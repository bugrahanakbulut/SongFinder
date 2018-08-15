package com.example.SongFinder.Controllers;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.special.SearchResult;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.search.SearchItemRequest;

import java.io.IOException;

public class SpotifyController {
    private static final String accessToken = "BQB88vz6EvxhW8nhZBpTQLoZBXT9RDGrOxudO863rdOnPo6Uh6Up63NYqctChpP-VSgzDcstGQgIslIQzlmsVOIjCSlg0Nxe6CpxVMeQIpdiSHXYjPZqYABAQWv0rQKlE2etq8Btz53JSgf4-Xlf18aWHpywtAkqjJZWEknSW3_Xs3JCgMlcUxXgJMziJSDjg4qOjp_1ZFoqV6IAYXtpjrxiIioDyUVJ6OTnmObfkgrKTkL3QXG8nN9slvMpiQF1Z1cg6elPVCSTfzWTq2repQ" ;
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

    public static void main(String[] args){
        searchitem("Hello,Adele");
    }
}
