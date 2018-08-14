package com.example.SongFinder.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RequestController {
    public static <T> ArrayList<T> getRequest(String http, Class expectedClass, String[] properties){
        HttpURLConnection connection = null;
        StringBuilder jsonResults = new StringBuilder();
        ArrayList<T> queryResults = null;


        try {
            URL url = new URL(http);
            connection = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(connection.getInputStream());

            char[] buffer = new char[1024];
            int read = 0;
            while ((read = in.read(buffer)) != -1) {
                jsonResults.append(buffer, 0, read);
            }

            JSONObject jsonObject = new JSONObject(jsonResults.toString());
            //System.out.println(jsonObject.toString());

            JSONObject subResults = null;

            for(int i = 0; i < properties.length - 1; i++){
                subResults = jsonObject.getJSONObject(properties[i]);
            }
            JSONArray results = subResults.getJSONArray(properties[properties.length - 1]);

            queryResults = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for(int i = 0; i < results.length(); i++){
                JSONObject result = results.getJSONObject(i);
                // System.out.println(result.toString());
                Object newObject = mapper.readValue(result.toString(), expectedClass);
                queryResults.add((T) newObject);



            }


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return queryResults;
    }

}
