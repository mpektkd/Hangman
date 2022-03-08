package com.example.hangman.apis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.*;
import org.json.*;

public class Api {
    private static final Logger log = Logger.getLogger(Api.class.getName());
    private static HttpURLConnection conn;
    private BufferedReader reader;
    private String line;
    private StringBuilder responseContent = new StringBuilder();
    private URL url;

    public Api(URL url) {
        this.url = url;
    }
    public String response () {
        try{
            conn = (HttpURLConnection) url.openConnection();

            // Request setup
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);// 5000 milliseconds = 5 seconds
            conn.setReadTimeout(5000);

            // Test if the response from the server is successful
            int status = conn.getResponseCode();

            if (status >= 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }
        return (responseContent.toString());

    }

    public void parse(String responseBody) {
        JSONObject book = new JSONObject(responseBody);
        JSONObject description = book.getJSONObject("description");
        String value = description.getString("value");
        System.out.println(value);
    }

}