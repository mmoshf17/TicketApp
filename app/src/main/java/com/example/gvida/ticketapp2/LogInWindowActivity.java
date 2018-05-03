package com.example.gvida.ticketapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by gvida on 21/03/2018.
 */


public class LogInWindowActivity extends AppCompatActivity



{

    TextView lblTest;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_window_activity);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    public void loginBt(View view)
    {


        new HttpClient().execute();




    }




    public void loginTest(View view) {
        lblTest = findViewById(R.id.lbltest);
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        String t  = sharedPref.getString("token", "");

        lblTest.setText(t);

    }


    public class HttpClient extends AsyncTask<String, Void, Void> {


        EditText user_login = (EditText)findViewById(R.id.log_in_name);
        EditText user_pass = (EditText)findViewById(R.id.log_in_password);
        TextView lblmsg = (TextView) findViewById(R.id.Lable);

        @Override
        protected Void doInBackground(String... params){
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("username", user_login.getText());
                postDataParams.put("password", user_pass.getText());


               // To use it locally.
               // url = new URL("http://ticketapplication1.azurewebsites.net/api/Account/Login");
                //url = new URL("http://10.0.2.2:61902/api/Account/Login");

                url = new URL("http://ticketapp.shiftbook.dk/api/Account/Login");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();
                //HTTP header
                //urlConnection.setRequestProperty("Authorization", "Bearer "+ token);

                int responseCode = urlConnection.getResponseCode();
                String responseMessage = urlConnection.getResponseMessage();

                if(responseCode == HttpURLConnection.HTTP_OK){


                    String responseString = readStream(urlConnection.getInputStream());
                    JSONObject obj = new JSONObject(responseString);

                    String loginCrd = responseString;


                    String kept = loginCrd.substring(17, loginCrd.indexOf(",") -1);

                    //String remainder = loginCrd.substring(loginCrd.indexOf(",")+1, loginCrd.length());

                    SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();

                    editor.putString("token", kept);
                    editor.apply();

                        Intent intent = new Intent(LogInWindowActivity.this, MainMenuActivity.class);
                        startActivity(intent);




                }else{
                        lblmsg.setText("Login failed. Please try again");
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(urlConnection != null)
                    urlConnection.disconnect();
            }
            return null;
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while(itr.hasNext()){

                String key= itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
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


}
