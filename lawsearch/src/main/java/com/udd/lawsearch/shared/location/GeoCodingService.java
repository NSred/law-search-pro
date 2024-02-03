package com.udd.lawsearch.shared.location;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeoCodingService {
    public double[] getCoordinates(String address) {
        try {
            String baseUrl = "https://nominatim.openstreetmap.org/search";
            String finalUrl = baseUrl + "?q=" + address.replace(" ", "+") + "&format=json";

            RestTemplate restTemplate = new RestTemplate();
            JsonNode[] response = restTemplate.getForObject(finalUrl, JsonNode[].class);

            if (response != null && response.length > 0) {
                double latitude = response[0].get("lat").asDouble();
                double longitude = response[0].get("lon").asDouble();
                return new double[]{latitude, longitude};
            } else {
                throw new RuntimeException("An error occurred while getting address");
            }
        } catch (Exception e) {
            throw new RuntimeException("Provided address is not valid!");
        }
    }
}
