package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.HashMap;

public class LngLat {
    private HashMap<String, Integer> compassToAngle = new HashMap<>();
    public double lng;
    public double lat;

    public LngLat(double longitude, double latitude){
        lng = longitude;
        lat = latitude;
        compassToAngle.put("N", 0);
        compassToAngle.put("E", 90);
        compassToAngle.put("S", 180);
        compassToAngle.put("W", -90);
    }

    public boolean inCentralArea(){
        CentralClient client = new  CentralClient();
        ArrayList<double[]> markers = client.getCentralMarkers();
        ArrayList<GeometryUtil.Point> centralPolygon = new ArrayList<>();
        GeometryUtil.Point p = new GeometryUtil.Point(lng,lat);
        for (int i=0; i<markers.size(); i++){
            centralPolygon.add(new GeometryUtil.Point(markers.get(i)[0], markers.get(i)[1]));
        }

        return GeometryUtil.isInside(centralPolygon.toArray(new GeometryUtil.Point[markers.size()]), markers.size(), p);
    }

    public double distanceTo(LngLat c){
        return Math.sqrt(Math.pow((c.lng-lng), 2)+Math.pow((c.lat-lat),2));
    }

    public boolean closeTo(LngLat c){
        return distanceTo(c)<0.00015;
    }

    public LngLat nextPosition(String cardinalDirection){
        double newLong = Math.sin(compassToAngle.get(cardinalDirection))*0.00015;
        double newLat = Math.cos(compassToAngle.get(cardinalDirection))*0.00015;
        return new LngLat(newLong, newLat);
    }
}
