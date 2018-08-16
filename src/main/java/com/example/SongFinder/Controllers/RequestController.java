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
import java.util.Map;

public class RequestController {
    public static <T> T getRequest(String http, Class expectedClass, Map<String, String> header){
        HttpURLConnection connection = null;
        StringBuilder jsonResults = new StringBuilder();
        T newObj = null;

        try {
            URL url = new URL(http);
            connection = (HttpURLConnection) url.openConnection();
            if(header != null){
                for(Map.Entry<String, String> entry : header.entrySet()){
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            InputStreamReader in = new InputStreamReader(connection.getInputStream());

            char[] buffer = new char[1024];
            int read = 0;
            while ((read = in.read(buffer)) != -1) {
                jsonResults.append(buffer, 0, read);
            }

            JSONObject jsonObject = new JSONObject(jsonResults.toString());
            ObjectMapper mapper = new ObjectMapper();
            newObj = (T) mapper.readValue(jsonObject.toString(), expectedClass);

            // System.out.println(newObj.toString());

            /*
            System.out.println(jsonObject.toString());
            root unwrapper created by me
            ObjectMapper mapper = new ObjectMapper();

            JSONObject subResults = null;


            for(int i = 0; i < root.length - 1; i++){
                subResults = jsonObject.getJSONObject(root[i]);
            }
            JSONArray results = subResults.getJSONArray(root[root.length - 1]);


            queryResults = new ArrayList<>();


            ObjectMapper unwrapperMapper = new ObjectMapper();
            unwrapperMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
            ObjectReader objectReader = unwrapperMapper.reader(new TypeReference<List<T>>() {});
            List<T> someResult = objectReader.readValue(jsonObject.toString());
            System.out.println(someResult.size());



            for(int i = 0; i < results.length(); i++){
                JSONObject result = results.getJSONObject(i);
                // System.out.println(result.toString());
                Object newObject = mapper.readValue(result.toString(), expectedClass);
                // System.out.println((T) newObject.toString());
                queryResults.add((T) newObject);
                /// https://www.baeldung.com/jackson-nested-values
            }*/

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return newObj;
    }

}
