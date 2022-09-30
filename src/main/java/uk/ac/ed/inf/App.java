package uk.ac.ed.inf;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

/**
 * Hello world!
 * https://ilp-rest.azurewebsites.net/
 */
public class App 
{
    public static void main(String[] args)
    {
        if (args.length != 2){
            System.err.println("TestClient Base URL Echo Parameter");
            System.err.println("Supply base address of ILP REST server ad a string to be echoed");
            System.exit(1);
        }

        try{
            String baseUrl = args[0];
            String central = "orders";
            if (!baseUrl.endsWith("/")){
                baseUrl+="/";
            }

            URL url = new URL(baseUrl + central);
            OrderResponse[] response = new ObjectMapper().readValue(
                    new URL(baseUrl + central), OrderResponse[].class);
            System.out.println("The server responded as JSON greeting: \n");
            for (int i=0; i<10; i++)
            {
                System.out.println(response[i].customer + " " + response[i].orderItems[0]);
            }
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
