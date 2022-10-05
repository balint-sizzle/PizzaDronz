package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Restaurant {

    @JsonProperty("name")
    public String name;
    @JsonProperty("longitude")
    public double longitude;
    @JsonProperty("latitude")
    public double latitude;
    @JsonProperty("menu")
    public Menu[] menu;

    public static void main(String[] args) throws Exception {
        Restaurant[] participants = getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net"));
        String[] order = {"Margarita", "Margarita", "Margarita"};
        System.out.println(Order.getDeliveryCost(participants, order));
    }

    public Menu[] getMenu(){
        return menu;
    }

    public static Restaurant[] getRestaurantsFromRestServer(URL serverBaseAddress){
        String basis;

        if (!serverBaseAddress.toString().endsWith("/")) {
            basis = "/restaurants";
        }else{
            basis = "restaurants";
        }

        try{
            URL url = new URL(serverBaseAddress.toString()+basis);
            Restaurant[] response = new ObjectMapper().readValue(url, Restaurant[].class);
            return response;
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }
}
