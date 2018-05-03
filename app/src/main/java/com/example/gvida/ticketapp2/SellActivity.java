package com.example.gvida.ticketapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
import java.util.Base64;
import java.util.Iterator;
import java.util.Objects;

/**
 * Created by Rehan Mir on 17-04-2018.
 */

public class SellActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_activity);



    }


    public void upload(View view)
    {
            new HttpClient3().execute();
    }



    public class HttpClient3 extends AsyncTask<String, Void, Void> {


        EditText ticketName = (EditText)findViewById(R.id.ticketName);
        EditText ticketPrice = (EditText)findViewById(R.id.ticketPrice);
        EditText ticketDescription = (EditText)findViewById(R.id.ticketDescription);
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radio);
        RadioButton radioBuy = (RadioButton)findViewById(R.id.radioBuy);
        RadioButton radioAuction = (RadioButton)findViewById(R.id.radioAuction);

        @Override
        protected Void doInBackground(String... params){

            URL url;
            HttpURLConnection urlConnection = null;

            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("name", ticketName.getText());
                postDataParams.put("price", ticketPrice.getText());
                postDataParams.put("description", ticketDescription.getText());

                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(selectedId);
                String bla = radioButton.getText().toString();
                boolean isAuction =  !new String(bla.toString()).equals("Buy it now");


                postDataParams.put("isAuction", "True");


                SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                String token = sharedPref.getString("token", "");

               //String token = "pLTT7b4-9Tm_oVioiEFYGJpT25sFTvtBddeM2eDCXdbuEopv7yAC0tXfD50e3lQDvdBYGj70AFJu7n32BK5cncX214Jbpft-YI4gMBykv8JvZccAHNqk22i0XSTjtA5LHii7F8dFoMymz9MYqfipBJ_FQFVkFYfwD1ewvQ-eQ6Rka6yyJqvHr2IGkgkqYbkGYnuHgaOE3RJn6xgevnlKzxUS6b5zY22rRc2DCE7CVf-gm1AHi1PgdMiPfmotdud98xwhPkddUcwJZl3-KxU3EoRFOMsFHdE3IsQhRdMH0QLvqx_SOpTl-DU9zWGPEHKz9oqyLGVoTlra2H_FjhHgj_amlFRY92XLiUemxdJeCX1B4KPOWqZ4C74KYXPR8bnMAmdVNrrEk3igBp5WnQavFCSpc-mtZk7aiqQJQ9zX32eIE98sUDo-K77iINUhkSHjADnnEuWh95T8gydAPvxroyh_TKbyzDiY080rGV7mxUU6Nbxhi5-Olihd-tsNsgLo";

                String test = token;


                //TO use locally
                //url = new URL("http://10.0.2.2:61902/api/Ticket/TicketReg");

                url = new URL("http://ticketapp.shiftbook.dk/api/Ticket/TicketReg");
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestProperty("Authorization", "Bearer " + token);

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
                //urlConnection.setRequestProperty("Authorization", "Bearer "+ "pLTT7b4-9Tm_oVioiEFYGJpT25sFTvtBddeM2eDCXdbuEopv7yAC0tXfD50e3lQDvdBYGj70AFJu7n32BK5cncX214Jbpft-YI4gMBykv8JvZccAHNqk22i0XSTjtA5LHii7F8dFoMymz9MYqfipBJ_FQFVkFYfwD1ewvQ-eQ6Rka6yyJqvHr2IGkgkqYbkGYnuHgaOE3RJn6xgevnlKzxUS6b5zY22rRc2DCE7CVf-gm1AHi1PgdMiPfmotdud98xwhPkddUcwJZl3-KxU3EoRFOMsFHdE3IsQhRdMH0QLvqx_SOpTl-DU9zWGPEHKz9oqyLGVoTlra2H_FjhHgj_amlFRY92XLiUemxdJeCX1B4KPOWqZ4C74KYXPR8bnMAmdVNrrEk3igBp5WnQavFCSpc-mtZk7aiqQJQ9zX32eIE98sUDo-K77iINUhkSHjADnnEuWh95T8gydAPvxroyh_TKbyzDiY080rGV7mxUU6Nbxhi5-Olihd-tsNsgLo");

                int responseCode = urlConnection.getResponseCode();
                String responseMessage = urlConnection.getResponseMessage();

                if(responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_ACCEPTED){


                    Intent intent = new Intent(SellActivity.this, MainMenuActivity.class);
                    startActivity(intent);

                    String responseString = readStream(urlConnection.getInputStream());
                    String books = responseString;


                }else{
                    Toast.makeText(getApplicationContext(), "Try again.",
                            Toast.LENGTH_LONG).show();
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
