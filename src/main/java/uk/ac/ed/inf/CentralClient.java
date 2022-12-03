package uk.ac.ed.inf;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Singleton access point to REST server for fetching the border markers of the central area fly zone
 */
public final class CentralClient {
    String urlForCentral = "https://ilp-rest.azurewebsites.net/centralarea";
    private static CentralClient INSTANCE;

    /**
     *
     * @return Unordered list of all markers from REST server as LngLat objects
     */
    public LngLat[] getCentralMarkers(){
        try{
            URL url = new URL(urlForCentral);
            // configuration which allows LngLat object to be mapped to area markers even though LngLat has no "name" field
            LngLat[] response = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(url, LngLat[].class);
            return response;
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return CentralClient instance
     */
    public static CentralClient getINSTANCE(){
        if (INSTANCE==null){
            INSTANCE = new CentralClient();
        }
        return INSTANCE;
    }
}
