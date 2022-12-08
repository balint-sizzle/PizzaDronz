package uk.ac.ed.inf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;

import java.net.URL;
import java.util.ArrayList;

public class App {
    public static void main(String args[]) throws Exception {
        var date = args[0];
        var baseUrlStr = args[1];
        var seed = args[2];
        OrderHandler handler = new OrderHandler();
        PathPlanner planner = new PathPlanner();
        IOUtil io = new IOUtil();

        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL(baseUrlStr));

        Order[] sortedOrders = handler.parseOrders(baseUrlStr, date);
        LngLat Appleton = new LngLat(-3.1869, 55.9445);
        LngLat dominos = new LngLat(-3.1839, 55.9445);
        LngLat villa = new LngLat(-3.2025, 55.9433);
        LngLat civerinos = new LngLat(-3.1913, 55.9455);
        LngLat sodeberg = new LngLat(-3.1940, 55.9439);
        LngLat testB = new LngLat(-3.1870, 55.9428);

        Feature[] paths = new Feature[4];
        paths[0] = planner.generateFlightPath( Appleton, civerinos);
        paths[1] = planner.generateFlightPath(villa, Appleton);
        paths[2] = planner.generateFlightPath(dominos, Appleton);
        paths[3] = planner.generateFlightPath(sodeberg, Appleton);

        FeatureCollection fc = FeatureCollection.fromFeatures(paths);
        io.writeToFile("test.geojson", fc.toJson());
        // get orders for the day
        // validate orders
        // match orders to restaurant
        // sort orders

    }
}
