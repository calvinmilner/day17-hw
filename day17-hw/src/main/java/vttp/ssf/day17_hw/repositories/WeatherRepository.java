package vttp.ssf.day17_hw.repositories;

import java.io.StringReader;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.ssf.day17_hw.models.Information;

@Repository
public class WeatherRepository {

    @Autowired
    @Qualifier("redis-0")
    private RedisTemplate<String, Object> template;

    // redis-cli
    // set city "{ .... }"
    // expire city 600
    public void save(String city, Information info) {
        JsonObject json = info.toJson();
        ValueOperations<String, Object> valueOps = template.opsForValue();
        valueOps.set(city, json.toString());
        template.expire(city, 60, TimeUnit.SECONDS);
    }

    // get city "{ .... }"
    public Information get(String city) {
    ValueOperations<String, Object> valueOps = template.opsForValue();
    Object value = valueOps.get(city);
    
    if (value == null || !(value instanceof String)) {
        return null; // No data or invalid format
    }

    String jsonString = (String) value;

    try (JsonReader reader = Json.createReader(new StringReader(jsonString))) {
        // Parse JSON string back to JsonObject
        JsonObject jsonObject = reader.readObject();

        // Reconstruct Information object
        return Information.fromJson(jsonObject);
    }
}
}
