package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

/**
 * Class which represents a point in a flat version of real space
 */
public class LngLat{

    @JsonProperty("longitude")
    double lng;

    @JsonProperty("latitude")
    double lat;
    HashMap<String, Double> compassToAngle = new HashMap<>();

    /**
     *
     * @param longitude longitude
     * @param latitude latitude
     */


    public LngLat(double longitude, double latitude){
        lng = longitude;
        lat = latitude;
        compassToAngle.put("N", 0.0);
        compassToAngle.put("E", 90.0);
        compassToAngle.put("S", 180.0);
        compassToAngle.put("W", 270.0-360);
        compassToAngle.put("NE", 45.0);
        compassToAngle.put("SE", 135.0);
        compassToAngle.put("SW", 360-225.0-360);
        compassToAngle.put("NW", 325.0-360);
        compassToAngle.put("NNE", 22.5);
        compassToAngle.put("ENE", 67.5);
        compassToAngle.put("ESE", 112.5);
        compassToAngle.put("SSE", 157.5);
        compassToAngle.put("SSW", 202.5-360);
        compassToAngle.put("WSW", 247.5-360);
        compassToAngle.put("WNW", 295.5-360);
        compassToAngle.put("NNW", 337.5-360);

    }
    public LngLat(){ }

    /**
     *
     * @return true if this point is within area defined as the central area fly zone
     */
    public boolean inCentralArea(){
        LngLat[] markers = CentralClient.getINSTANCE().getCentralMarkers();

        return GeometryUtil.isInside(markers, markers.length, this);
    }

    /**
     *
     * @param point point to measure distance between
     * @return Euclidean distance between this and some other point
     */
    public double distanceTo(LngLat point){
        return Math.sqrt(Math.pow((point.lng - lng), 2)+Math.pow((point.lat - lat),2));
    }

    /**
     *
     * @param point some point
     * @return true if some point is within threshold distance of this point
     */
    public boolean closeTo(LngLat point){
        return distanceTo(point)<0.00015;
    }

    /**
     *
     * @param cardinalDirection one of 16 compass directions
     * @return point resulting from taking 1 step of movement in desired cardinalDirection
     */
    public LngLat nextPosition(String cardinalDirection){

        double newLong = Math.sin(compassToAngle.get(cardinalDirection))*0.00015;
        double newLat = Math.cos(compassToAngle.get(cardinalDirection))*0.00015;
        return new LngLat(newLong, newLat);
    }
}
