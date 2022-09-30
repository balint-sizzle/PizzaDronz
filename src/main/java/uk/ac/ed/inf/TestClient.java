package uk.ac.ed.inf;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class TestClient {
    public static void main(String[] args)
    {
        if (args.length != 2){
            System.err.println("TestClient Base URL Echo Parameter");
            System.err.println("Supply base address of ILP REST server ad a string to be echoed");
            System.exit(1);
        }

        try{
            String baseUrl = args[0];
            String echoBasis = args[1];
            if (!baseUrl.endsWith("/")){
                baseUrl+="/";
            }

            URL url = new URL(baseUrl + "test/" + echoBasis);
            TestResponse response = new ObjectMapper().readValue(
                    new URL(baseUrl + "test/" + echoBasis), TestResponse.class);

            //if (!response.greeting.endsWith(echoBasis)){
            //    throw new RuntimeException("wrong echo returned");
            //}

            System.out.println("The server responded as JSON greeting: \n\n"+response.greeting);
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
