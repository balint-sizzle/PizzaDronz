package uk.ac.ed.inf;

import com.google.gson.Gson;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Polygon;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class IOUtil {
    public static void main(String[] args) throws IOException {
        readGeoJson("no-fly-zones.geojson");
    }
    public void writeToFile(String filename, String json) throws IOException {
        Files.write(Path.of(filename), json.getBytes());
    }

    public static String readGeoJson(String filename) throws IOException {
        String jsonString = new String (Files.readAllBytes(Path.of(filename)));
        return jsonString;
    }
}
