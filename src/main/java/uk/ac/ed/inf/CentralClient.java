package uk.ac.ed.inf;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class CentralClient {
    String urlForCentral = "https://ilp-rest.azurewebsites.net/centralarea";
    ArrayList<double[]> markers = new ArrayList<>();
    public ArrayList<double[]> getCentralMarkers(){
        try{
            URL url = new URL(urlForCentral);
            CentralAreaResponse[] response = new ObjectMapper().readValue(url, CentralAreaResponse[].class);
            for (int i=0; i< response.length; i++){
                double[] coords = {response[i].longitude, response[i].latitude};
                markers.add(coords);
            }
            return markers;
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
