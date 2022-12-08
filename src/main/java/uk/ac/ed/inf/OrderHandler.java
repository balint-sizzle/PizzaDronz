package uk.ac.ed.inf;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

public class OrderHandler {

    private String defaultEndpoint = "https://ilp-rest.azurewebsites.net";

    /**
     * dynamically get and validate the orders for a given day
     *
     * @param baseUrl
     * @param date
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public Order[] pullAndValidateOrdersForDay(URL baseUrl, String date) {
        String endpoint = "";

        if (!baseUrl.toString().endsWith("/")) {
            endpoint = baseUrl + "/orders/" + date;
        } else {
            endpoint = baseUrl + "orders/" + date;
        }

        try {
            URL url = new URL(endpoint);
            Order[] orders = new ObjectMapper().readValue(url, Order[].class);
            for (Order o : orders) {
                o.validateOrder();
            }
            return orders;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;


    }

    /**
     * @param baseUrlStr
     * @param date
     * @return
     */
    public Order[] parseOrders(String baseUrlStr, String date) {
        URL baseUrl;
        if (baseUrlStr.equals("")) {
            baseUrlStr = defaultEndpoint;
        }
        try {
            baseUrl = new URL(baseUrlStr);
            Order[] rawOrders = pullAndValidateOrdersForDay(baseUrl, date);
            Restaurant[] restaurants = Restaurant.getRestaurantsFromRestServer(baseUrl);

        } catch (MalformedURLException e) {

            e.printStackTrace();
        }

        return null;
    }
}
