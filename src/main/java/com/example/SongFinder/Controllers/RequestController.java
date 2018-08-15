package com.example.SongFinder.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
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
    public static <T> List<T> getRequest(String http, Class expectedClass, String[] root){
        HttpURLConnection connection = null;
        StringBuilder jsonResults = new StringBuilder();
        List<T> queryResults = null;


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
            ObjectMapper mapper = new ObjectMapper();

            // Old version of deserialization I'm keeping that code block just in case for now

            JSONObject subResults = null;

            for(int i = 0; i < root.length - 1; i++){
                subResults = jsonObject.getJSONObject(root[i]);
            }
            JSONArray results = subResults.getJSONArray(root[root.length - 1]);

            queryResults = new ArrayList<>();

            /*
            ObjectMapper unwrapperMapper = new ObjectMapper();
            unwrapperMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
            ObjectReader objectReader = mapper.reader(new TypeReference<List<T>>() {});
            List<T> someResult = objectReader.readValue(String.valueOf(jsonObject));
            System.out.println(someResult.size());
            */

            for(int i = 0; i < results.length(); i++){
                JSONObject result = results.getJSONObject(i);
                // System.out.println(result.toString());
                Object newObject = mapper.readValue(result.toString(), expectedClass);
                // System.out.println((T) newObject.toString());
                queryResults.add((T) newObject);
                /// https://www.baeldung.com/jackson-nested-values
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return queryResults;
    }

}
