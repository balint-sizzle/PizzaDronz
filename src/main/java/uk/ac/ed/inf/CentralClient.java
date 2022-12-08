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
    private static final String DEFAULT_URL = "https://ilp-rest.azurewebsites.net/centralarea";
    private static CentralClient instance;
    private URL instanceUrl;

    public CentralClient(URL baseUrl){
        this.instanceUrl = baseUrl;
    }
    /**
     *
     * @return Unordered list of all markers from REST server as LngLat objects
     */
    public LngLat[] getCentralMarkers(){
        try{
            // configuration which allows LngLat object to be mapped to area markers even though LngLat has no "name" field
            LngLat[] response = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(this.instanceUrl, LngLat[].class);
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
    public static CentralClient getINSTANCE(String baseUrlStr){

        if (instance ==null){
            String endpoint = CentralClient.DEFAULT_URL;
            if (!baseUrlStr.equals("")){
                endpoint = baseUrlStr;
            }
            if (!endpoint.endsWith("/")) {
                endpoint +="/centralarea";
            }else{
                endpoint += "centralarea";
            }
            try{

                URL baseUrl = new URL(endpoint);
                instance = new CentralClient(baseUrl);
            } catch (MalformedURLException e){
                e.printStackTrace();
            }

        }
        return instance;
    }
}
