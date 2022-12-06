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
    static final double TOLERANCE = 0.00015;
    static final double MOVE_SIZE = 0.00015;

    /**
     *
     * @param longitude longitude
     * @param latitude latitude
     */


    public LngLat(double longitude, double latitude){
        lng = longitude;
        lat = latitude;
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
     * @param other point to measure distance between
     * @return Euclidean distance between this and some other point
     */
    public double distanceTo(LngLat other){
        var d1 = other.lng - this.lng;
        var d2 = other.lat - this.lat;
        return Math.sqrt(d1*d1 + d2*d2);
    }

    /**
     *
     * @param point some point
     * @return true if some point is within threshold distance of this point
     */
    public boolean closeTo(LngLat point){
        return distanceTo(point)<= LngLat.TOLERANCE;
    }

    /**
     *
     * @param d one of 16 compass directions
     * @return point resulting from taking 1 step of movement in desired cardinalDirection
     */
    public LngLat nextPosition(Direction d){

        double newLong = Math.sin(d.angle())*LngLat.MOVE_SIZE;
        double newLat = Math.cos(d.angle())*LngLat.MOVE_SIZE;
        return new LngLat(newLong, newLat);
    }
}
