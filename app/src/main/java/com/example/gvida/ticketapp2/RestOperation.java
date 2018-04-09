package com.example.gvida.ticketapp2;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Muheb.moshfiq on 4/7/2018.
 */

public class RestOperation extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... params){
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL("http://canvas.api.shiftbook.dk/GetSampleImages");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            //HTTP header
            //urlConnection.setRequestProperty("Authorization", "Bearer "+ token);

            int responseCode = urlConnection.getResponseCode();
            String responseMessage = urlConnection.getResponseMessage();

            if(responseCode == HttpURLConnection.HTTP_OK){
                String responseString = readStream(urlConnection.getInputStream());
                String books = responseString;


            }else{

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }
        return null;
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

    protected void onPostExecute(Void result){
        super.onPostExecute(result);
    }
}

