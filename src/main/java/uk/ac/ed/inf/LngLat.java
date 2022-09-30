package uk.ac.ed.inf;

import java.util.ArrayList;

public class LngLat {
    public double lng;
    public double lat;

    public boolean inCentralArea(){
        CentralClient client = new  CentralClient();
        ArrayList<double[]> markers = client.getCentralMarkers();

        return false;
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
