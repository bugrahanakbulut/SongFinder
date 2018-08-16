package com.example.SongFinder.Controllers;

import com.example.SongFinder.Entities.Track;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class RequestControllerTest {
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Person{
        @JsonProperty("name")
        String name;
        @JsonProperty("surname")
        String surname;

        @Override
        public String toString() {
            String output = "";
            output += "NAME : " + this.name;
            output += "\t";
            output += "SURNAME : " + this.surname;
            return output;
        }
    }

    @Test
    public void jsonDeserializer() throws IOException, JSONException {
        String json = "{\"name\":\"qwerty\"," +
                      "\"surname\":\"qwertyasdfg\"}";
        Person asd = RequestController.jsonDeserializer(json, Person.class);
        System.out.println(asd.toString());
    }
}