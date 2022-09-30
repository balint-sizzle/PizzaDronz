package uk.ac.ed.inf;

import java.util.ArrayList;

public class LngLat {
    public double lng;
    public double lat;

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
        return 0;
    }

    public boolean closeTo(LngLat c){
        return false;
    }

    public LngLat nextPosition(String cardinalDirection){
        return null;
    }
}
