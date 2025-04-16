package com.example.naturelink.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class WeatherService {
    private final String API_KEY = "bd5e378503939ddaee76f12ad7a97608";
    private final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";

    public String getCurrentSeason1(String city) {
        String url = String.format(API_URL, city, API_KEY);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.getBody());

                int month = LocalDate.now().getMonthValue();

                if (month >= 3 && month <= 5) return "Printemps";
                if (month >= 6 && month <= 8) return "Été";
                if (month >= 9 && month <= 11) return "Automne";
                return "Hiver";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "Inconnu";
    }
    public String getCurrentSeason(String city) {
        // Le paramètre 'city' n'est plus utilisé mais conservé pour compatibilité
        int month = LocalDate.now().getMonthValue();

        if (month >= 3 && month <= 5) return "Printemps";
        if (month >= 6 && month <= 8) return "Été";
        if (month >= 9 && month <= 11) return "Automne";
        return "Hiver";
    }
}
