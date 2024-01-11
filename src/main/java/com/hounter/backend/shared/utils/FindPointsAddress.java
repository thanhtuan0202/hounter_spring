package com.hounter.backend.shared.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class FindPointsAddress {
    @Value("${mapquest.api.key}")
    private String mapQuestApiKey;
    @Getter
    @Setter
    public static class Results{
        private List<Location> results;
    }
    @Getter
    @Setter
    public static class Location {
        private ProvidedLocation providedLocation;
        private List<LocationDetails> locations;

        @Override
        public String toString() {
            return "Location{" +
                    "providedLocation=" + providedLocation +
                    ", locations=" + locations +
                    '}';
        }
    }
    @Getter
    @Setter
    public static class ProvidedLocation {
        private String location;
    }
    @Getter
    @Setter
    public static class LocationDetails {
        private String adminArea5; // City
        private String adminArea4; // County
        private String adminArea3; // State
        private String adminArea1; // Country
        private LatLng latLng;
    }
    @Getter
    @Setter
    public static class LatLng {
        private double lat;
        private double lng;

        @Override
        public String toString() {
            return "LatLng{" +
                    "lat=" + lat +
                    ", lng=" + lng +
                    '}';
        }
    }

    public LatLng getAddressPoints(String address) throws IOException {

        String urlStr = "https://www.mapquestapi.com/geocoding/v1/address?"
                + "inFormat=kvp&outFormat=json&key=" + this.mapQuestApiKey + "&location="+ URLEncoder.encode(address, StandardCharsets.UTF_8);;

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
        Results results = objectMapper.readValue(response.toString(), Results.class);
        if (results != null) {
            Location location = results.getResults().get(0);
            LocationDetails locationDetails = location.getLocations().get(0);
            LatLng latLng = locationDetails.getLatLng();

            double latitude = latLng.getLat();
            double longitude = latLng.getLng();

            // Do something with latitude and longitude
            System.out.println("Latitude: " + latitude + ", Longitude: " + longitude);
            return latLng;
        }
        conn.disconnect();
        return null;
    }
}
