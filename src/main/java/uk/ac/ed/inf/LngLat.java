package uk.ac.ed.inf;

public class LngLat {
    public double lng;
    public double lat;

    public boolean inCentralArea(){
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
