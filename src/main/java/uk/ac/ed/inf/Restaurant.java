package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class to store participating restaurant's details and manage their active menus
 */
public class Restaurant {

    @JsonProperty("name")
    public String name;

    @JsonProperty("longitude")
    public double longitude;

    @JsonProperty("latitude")
    public double latitude;

    @JsonProperty("menu")
    public Menu[] menu;

    /**
     * @return list of items available for order
     */
    public Menu[] getMenu() {
        return menu;
    }

    /**
     * @param serverBaseAddress for the REST server
     * @return list of participating restaurants and their details including their menus
     */
    public static Restaurant[] getRestaurantsFromRestServer(URL serverBaseAddress) {
        String basis;

        if (!serverBaseAddress.toString().endsWith("/")) {
            basis = "/restaurants";
        } else {
            basis = "restaurants";
        }

        try {
            URL url = new URL(serverBaseAddress + basis);
            Restaurant[] response = new ObjectMapper().readValue(url, Restaurant[].class);
            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
