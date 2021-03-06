package com.example.SongFinder.Controllers;

import com.example.SongFinder.Entities.Track;
import com.example.SongFinder.Entities.TrackList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.*;

public class RequestControllerTest {
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Person{
        @JsonProperty("name")
        String name;
        @JsonProperty("surname")
        String surname;

        Insurance insurance;

        @JsonProperty("insurance")
        public void setIns(Map<String, Object> object){
            this.insurance = new Insurance((String) object.get("type"));
        }

        @Override
        public String toString() {
            String output = "";
            output += "NAME : " + this.name;
            output += "\t";
            output += "SURNAME : " + this.surname;
            if(insurance != null){
                output += ("\t" + "INSURANCE : " + this.insurance.type);
            }
            return output;
        }
    }

    private static class Insurance{
        String type;
        public Insurance(String type){
            this.type = type;
        }
    }

    @Test
        public void jsonDeserializer() throws IOException, JSONException {
            String json = "{\"name\":\"qwerty\"," +
                    "\"surname\":\"qwertyasdfg\"," +
                    "\"insurance\":{" +
                    "\"type\" : \"sssss\"} }";
            Person asd = RequestController.jsonDeserializer(json, Person.class);
            System.out.println(asd.toString());
    }

    @Test
    public void jsonDeserializerTest1() throws IOException, JSONException {
        String json = "{\"name\":\"qwerty\"," +
                "\"surname\":\"qwertyasdfg\"}";
        Person asd = RequestController.jsonDeserializer(json, Person.class);
        System.out.println(asd.toString());
    }

    @Test
    public void restfulGetRequestTest0(){
        String url = "http://ws.audioscrobbler.com/2.0/?method=geo.gettoptracks&country=turkey" +
                "&api_key=56ad71507512a288c28c1fffb1be0a19&limit=10&format=json";

        TrackList tl = RequestController.restfulGetRequest(url, TrackList.class, null);
        for(Track t : tl.getTrackList()){
            System.out.println(t.toString());
        }
    }
}