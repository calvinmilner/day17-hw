package vttp.ssf.day17_hw.models;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Information {

    public static final String ICON_URL = "https://openweathermap.org/img/wn/";

    private int id;
    private String main;
    private String description;
    private String icon;
    private List<Information> listInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Information> getListInfo() {
        return listInfo;
    }

    public void setListInfo(List<Information> listInfo) {
        this.listInfo = listInfo;
    }

    // Information to JSON Object
    public JsonObject toJson() {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (Information li : this.listInfo) {
            JsonObject j = Json.createObjectBuilder()
                    .add("id", li.getId())
                    .add("main", li.getMain())
                    .add("description", li.getDescription())
                    .add("icon", li.getIcon())
                    .build();
            arrBuilder.add(j);
        }
        return Json.createObjectBuilder()
                .add("weather", arrBuilder.build())
                .build();
    }

    // JSON string -> Information
    public static Information toInformation(String json) {

        Information info = new Information();

        // Convert the string to JSON
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject jsonObj = reader.readObject();

        List<Information> listInfo = new LinkedList<>();
        JsonArray jsonArr = jsonObj.getJsonArray("weather");
        for (int i = 0; i < jsonArr.size(); i++) {
            Information li = new Information();
            JsonObject j = jsonArr.getJsonObject(i);
            String icon = ICON_URL + j.getString("icon") + "@2x.png";
            li.setId(j.getInt("id"));
            li.setMain(j.getString("main"));
            li.setDescription(j.getString("description"));
            li.setIcon(icon);
            listInfo.add(li);
        }
        info.setListInfo(listInfo);
        return info;
    }

    public static Information fromJson(JsonObject jsonObject) {
        JsonArray weatherArray = jsonObject.getJsonArray("weather");
        List<Information> listInfo = new ArrayList<>();

        for (int i = 0; i < weatherArray.size(); i++) {
            JsonObject item = weatherArray.getJsonObject(i);
            Information info = new Information();
            info.setId(item.getInt("id"));
            info.setMain(item.getString("main"));
            info.setDescription(item.getString("description"));
            info.setIcon(item.getString("icon"));
            listInfo.add(info);
        }

        // Assuming Information has a constructor or method to set the list
        Information result = new Information();
        result.setListInfo(listInfo);

        return result;
    }
}
