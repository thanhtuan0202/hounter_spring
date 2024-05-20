package com.hounter.backend.shared.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

@Service
public class GoongUtils {
    private String url;
    private String apiKey;
    private String detailUrl;

    @Getter
    @Setter
    public static class ResultPredictions {
        private List<Prediction> predictions;
    }

    @Getter
    @Setter
    public static class Prediction {
        private String place_id;
    }

    @Getter
    @Setter
    public static class ResultDetail {
        private Detail result;
    }

    @Getter
    @Setter
    public static class Detail{
        private String place_id;
        private String formatted_address;
        private Geometry geometry;
    }

    @Getter
    @Setter
    public static class Geometry{
        private Location location;
    }

    @Getter
    @Setter
    public static class Location{
        private double lat;
        private double lng;
    }

    public GoongUtils() {
        this.url = "https://rsapi.goong.io/Place/AutoComplete?api_key=%s&input=%s";
        this.apiKey = "qpFQmvMz1is1jrm5JgfJVbJNkzKRC6TCT4YxbD9x";
        this.detailUrl = "https://rsapi.goong.io/Place/Detail?api_key=%s&place_id=%s";
    }

    public StringBuilder getResponse(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        return response;
    }

    public Prediction getPrediction(String address) throws IOException {
        String urlStr = String.format(this.url, this.apiKey, URLEncoder.encode(address, StandardCharsets.UTF_8));
        StringBuilder response = getResponse(urlStr);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ResultPredictions results = objectMapper.readValue(response.toString(), ResultPredictions.class);
        if (results != null) {
            Prediction prediction = results.getPredictions().get(0);
            return prediction;
        }
        return null;
    }

    public Location getAddressLngLat(String q) throws IOException {
        Prediction prediction = this.getPrediction(q);
        String placeId = prediction.getPlace_id();

        String urlStr = String.format(this.detailUrl, this.apiKey, placeId);
        StringBuilder response = getResponse(urlStr);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ResultDetail results = objectMapper.readValue(response.toString(), ResultDetail.class);
        if (results != null) {
            Detail detail = results.getResult();
            Location location = detail.getGeometry().getLocation();
            return location;
        }
        return null;
    }
}
