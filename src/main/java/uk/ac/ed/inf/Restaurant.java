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


    public static void main(String[] args) throws Exception {
        LngLat p = new LngLat(-3.1884, 55.9447);

        Restaurant[] participants = getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net"));
        Order[] orders = new ObjectMapper().readValue(new URL("https://ilp-rest.azurewebsites.net/orders"), Order[].class);
        String[] order = {"Margarita", "Margarita", "Margarita"};
        System.out.println(orders[0].customer);
        System.out.println(p.inCentralArea());
        System.out.println(Order.getDeliveryCost(participants, order));
    }

    /**
     *
     * @return list of items available for order
     */
    public Menu[] getMenu(){
        return menu;
    }

    /**
     *
     * @param serverBaseAddress for the REST server
     * @return list of participating restaurants and their details including their menus
     */
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
