package uk.ac.ed.inf;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException ;

public class Download {
    public static void main(String[] args)
    {
        if (args.length != 2){
            System.err.println("Download Base URL Filename");
            System.err.println("suply base address of the ilp rest service");
            System.exit(1);
        }
        URL finalUrl = null;
        String baseUrl = args[0];
        String filenameToLoad = args[1];
        if (!baseUrl.endsWith("/")){
            baseUrl+="/";
        }

        try{
            finalUrl = new URL(baseUrl + filenameToLoad);
        }catch (MalformedURLException e){
            System.err.println("URL uis invalid: " + baseUrl + filenameToLoad);
        }
        try (
                BufferedInputStream in = new BufferedInputStream(finalUrl.openStream());
                FileOutputStream fileOutputStream = new FileOutputStream(filenameToLoad, false)) {
            byte[] dataBuffer = new byte[4096];
            int bytesRead;

            while((bytesRead = in.read(dataBuffer, 0, 1024)) != -1){
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            System.out.println(" F i l e was written : " + filenameToLoad);
        } catch (
                IOException e) {
            System.err.format(" Error loading f i l e : %s from %s −> %s ",
                    filenameToLoad, finalUrl, e);
        }
    }
}
