package com.hounter.backend.shared.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
@Service
public class FindPointMapbox {
    @Getter
    @Setter
    public static class Result{
        private List<Feature> features;
    }
    @Getter
    @Setter
    static
    public class Feature{
        private double[] center;
    }

    public FindPointsAddress.LatLng getAddressPoints(String address) throws IOException {
        String urlStr = "https://api.mapbox.com/geocoding/v5/mapbox.places/" + URLEncoder.encode(address, StandardCharsets.UTF_8) + ".json?access_token=pk.eyJ1IjoidGhhbmh0dWFuY3NrMjAiLCJhIjoiY2xyZGRnZHYxMGZqazJqbzNiY3pjNTdzdiJ9.lCNHVLuQsMyIx41kAZGE7g";

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // Reading the response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        System.out.println(response.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Result results = objectMapper.readValue(response.toString(), Result.class);
        if (results != null) {
            Feature feature = results.getFeatures().get(0);
            double[] coordinates = feature.getCenter();

            double latitude = coordinates[1];
            double longitude = coordinates[0];

            // Do something with latitude and longitude
            System.out.println("Latitude: " + latitude + ", Longitude: " + longitude);
            return new FindPointsAddress.LatLng(latitude,longitude);
        }
        conn.disconnect();
        return null;
    }
}
