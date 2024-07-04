package com.otunba.hng_proj1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.Provider;

@Service
public class GeoLocationService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    public String getGeoLocation(String ip) {
        String url = "http://ip-api.com/json/" +ip;
        String reponse = restTemplate.getForObject(url, String.class);
        try {
            JsonNode jsonNode = objectMapper.readTree(reponse);
            return jsonNode.get("city").asText();
        }catch (Exception e){
            e.printStackTrace();
            return "Error parsing the response";
        }
    }
}
