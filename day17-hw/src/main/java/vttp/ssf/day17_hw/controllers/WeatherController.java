package vttp.ssf.day17_hw.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.servlet.ModelAndView;

import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import vttp.ssf.day17_hw.models.Information;
// import vttp.ssf.day17_hw.models.SearchParams;
import vttp.ssf.day17_hw.services.WeatherService;

@Controller
@RequestMapping
public class WeatherController {

    @Value("${appid}")
    private String apiKey;
    // private String apiKey = "57d20f387b064e49d66ba75a393780a0";

    @Autowired
    private WeatherService weatherServ;

    @GetMapping("/weather")
    public String getWeather(@RequestParam String city, Model model) {
        
        // List<JsonValue> content = weatherServ.search(apiKey, city);
        // Information info = weatherServ.search(apiKey, city);
        // model.addAttribute("content", content);
        // model.addAttribute("info", info);
        String result = weatherServ.search(apiKey, city);
        model.addAttribute("result", result);
        return "display";
    }
}