package vttp.ssf.day17_hw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.ssf.day17_hw.models.Information;
import vttp.ssf.day17_hw.services.WeatherService;

@Controller
@RequestMapping
public class WeatherController {

    @Value("${appid}")
    private String apiKey;

    @Autowired
    private WeatherService weatherServ;

    @GetMapping("/weather")
    public String getWeather(@RequestParam String city, Model model) {
        String formattedCity = city.replace(" ", "+");
        if (weatherServ.getInformation(formattedCity) == null) {
            Information cityInfo = weatherServ.search(apiKey, formattedCity);
            weatherServ.save(formattedCity, cityInfo);
            model.addAttribute("city", city);
            model.addAttribute("info", cityInfo);
            model.addAttribute("cache", Boolean.FALSE);
        } else {
            Information cacheInfo = weatherServ.getInformation(formattedCity);
            model.addAttribute("city", city);
            model.addAttribute("info", cacheInfo);
            model.addAttribute("cache", Boolean.TRUE);
        }

        return "display";
    }
}
