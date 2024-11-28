package vttp.ssf.day17_hw.services;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
// import vttp.ssf.day17_hw.models.SearchParams;
import jakarta.json.JsonValue;
import vttp.ssf.day17_hw.models.Information;
import vttp.ssf.day17_hw.repositories.WeatherRepository;

@Service
public class WeatherService {

        @Autowired
        private WeatherRepository weatherRepo;

        public static final String GET_URL = "https://api.openweathermap.org/data/2.5/weather";

        // public String search(String apiKey, String city) {
        public Information search(String apiKey, String city) {

                // https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}

                String url = UriComponentsBuilder
                                .fromUriString(GET_URL)
                                .queryParam("q", city)
                                .queryParam("appid", apiKey)
                                .toUriString();

                System.out.printf("URL generated: %s\n\n", url);

                RequestEntity<Void> req = RequestEntity
                                .get(url)
                                .accept(MediaType.APPLICATION_JSON)
                                .build();
                RestTemplate template = new RestTemplate();
                ResponseEntity<String> resp = template.exchange(req, String.class);

                String payload = resp.getBody();
                System.out.printf("%s\n\n", payload);
                Information cityInfo = Information.toInformation(payload);

                return cityInfo;
        }

        public void save(String city, Information info) {
                weatherRepo.save(city, info);
        }

        public Information getInformation(String city) {
                return weatherRepo.get(city);
        }
        // public Optional<Information> get(String city) {
        // if(weatherRepo.get(city).isPresent())
        // weatherRepo.get(city);
        // return;
        // }
}
