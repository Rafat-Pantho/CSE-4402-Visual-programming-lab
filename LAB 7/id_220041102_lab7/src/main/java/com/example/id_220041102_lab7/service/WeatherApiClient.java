package com.example.id_220041102_lab7.service;

import com.google.gson.*;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WeatherApiClient {

    private static final String BASE = "https://api.weatherapi.com/v1";
    private static final String API_KEY = "e09486a4195443fdb38111616252008";

    private final HttpClient http = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    private JsonObject get(String pathWithQuery) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(pathWithQuery))
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> resp = http.send(request, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() >= 200 && resp.statusCode() < 300) {
            return JsonParser.parseString(resp.body()).getAsJsonObject();
        } else {
            String errText = resp.body();
            throw new IOException("HTTP " + resp.statusCode() + ": " + errText);
        }
    }

    private static String enc(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }

    public JsonObject fetchCurrentAndForecast(String city) throws IOException, InterruptedException {
        String url = BASE + "/forecast.json?key=" + API_KEY +
                "&q=" + enc(city) + "&days=3&aqi=yes&alerts=no";
        return get(url);
    }

    public List<JsonObject> fetch7DayHistory(String city) throws IOException, InterruptedException {
        List<JsonObject> days = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            LocalDate dt = LocalDate.now().minusDays(i);
            String url = BASE + "/history.json?key=" + API_KEY +
                    "&q=" + enc(city) + "&dt=" + dt;
            try {
                days.add(get(url));
            } catch (IOException ex) {
                if (days.isEmpty()) throw ex;
            }
        }
        return days;
    }

    public static String optString(JsonObject obj, String... path) {
        JsonElement cur = obj;
        for (String p : path) {
            if (!(cur instanceof JsonObject) || !((JsonObject) cur).has(p)) return "";
            cur = ((JsonObject) cur).get(p);
        }
        if (cur == null || cur.isJsonNull()) return "";
        if (cur.isJsonPrimitive()) return cur.getAsJsonPrimitive().getAsString();
        return cur.toString();
    }

    public static double optDouble(JsonObject obj, String... path) {
        String s = optString(obj, path);
        if (s.isBlank()) return Double.NaN;
        try { return Double.parseDouble(s); } catch (Exception e) { return Double.NaN; }
    }

    public static int optInt(JsonObject obj, String... path) {
        String s = optString(obj, path);
        if (s.isBlank()) return 0;
        try { return Integer.parseInt(s.split("\\.")[0]); } catch (Exception e) { return 0; }
    }

    public static String toHttpsIcon(String iconUrlMaybeProtocolLess) {
        if (iconUrlMaybeProtocolLess == null) return "";
        String url = iconUrlMaybeProtocolLess.trim();
        if (url.startsWith("//")) return "https:" + url;
        if (!url.startsWith("http")) return "https://" + url;
        return url;
    }
}
