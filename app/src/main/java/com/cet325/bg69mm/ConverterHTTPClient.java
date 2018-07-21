package com.cet325.bg69mm;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jamessunley on 04/01/2018.
 */

//access the API for the converter and retrieve the input stream
public class ConverterHTTPClient {

    //URL for the API
    //using fixer.io
    private static String BASE_URL = "https://api.fixer.io/latest";

    //method to get the converter data
    public String getConverterData(String converter) {

        //create the connection
        HttpURLConnection con = null ;
        //create the input stream
        InputStream is = null;

        try {

            //create URL object
            URL obj = new URL(BASE_URL);

            //open the connection
            con = (HttpURLConnection) obj.openConnection();

            //get response code
            int response = con.getResponseCode();
            //check if the response is ok
            if (response == HttpURLConnection.HTTP_OK) {
                //read the response
                StringBuilder buffer = new StringBuilder();
                //populate input stream
                is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                    Log.d("JSON-line",line);
                    buffer.append(line + "\r\n");
                }
                //close the connection
                is.close();
                con.disconnect();
                Log.d("JSON",buffer.toString());
                return buffer.toString();
            }
            else {
                //error message if it wont connect to the URL
                Log.d("HttpURLConnection","Unable to connect");
                return null;
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Exception e) {}
            try { con.disconnect(); } catch(Exception e) {}
        }

        return null;
    }
}
