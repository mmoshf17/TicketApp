package com.example.gvida.ticketapp2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.util.Calendar;
import java.util.Iterator;
import java.util.Objects;

/**
 * Created by gvida on 20/03/2018.
 */

public class RequestActivity extends AppCompatActivity
{



    //this is for the side menu
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_activity);

        Toolbar toolbar = findViewById(R.id.toolbar_request);
        setSupportActionBar(toolbar);

        //Side menu stuff
        drawerLayout = findViewById(R.id.drawerLayout_request);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                drawerView.bringToFront();
                drawerView.requestLayout();
            }
        };


        //drawer layout
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView nv = findViewById(R.id.nav_id);
        nv.setNavigationItemSelectedListener(item -> {
            if (item.isChecked()) item.setChecked(false);
            else item.setChecked(true);

            SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

            String token = sharedPref.getString("token", "");

            drawerLayout.closeDrawers();
            switch (item.getItemId()) {
                case R.id.Home:
                    Intent intent2 = new Intent(getApplicationContext(), MainMenuActivity.class);
                    startActivity(intent2);
                    break;

                case R.id.Account:
                    if (Objects.equals(token, "")) {

                        Intent intent3 = new Intent(getApplicationContext(), LogInActivity.class);
                        startActivity(intent3);
                        break;
                    } else if (!Objects.equals(token, "")) {
                        Intent intent4 = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent4);
                        break;
                    }
                    break;

                case R.id.request_your_ticket:
                    Intent intent = new Intent(getApplicationContext(), RequestActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "this", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.Sell_your_ticket:
                    Intent intent1 = new Intent(getApplicationContext(), SellActivity.class);
                    startActivity(intent1);
                    break;

            }
            return false;
        });

    }

    public void requestBtn(View view) {
        new HttpClient3().execute();
    }



    public class HttpClient3 extends AsyncTask<String, Void, Void> {


        EditText editTextName = (EditText) findViewById(R.id.editTextName);

        @Override
        protected Void doInBackground(String... params) {

            URL url;
            HttpURLConnection urlConnection = null;

            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("Name", editTextName.getText());



                SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                String token = sharedPref.getString("token", "");

                //String token = "pLTT7b4-9Tm_oVioiEFYGJpT25sFTvtBddeM2eDCXdbuEopv7yAC0tXfD50e3lQDvdBYGj70AFJu7n32BK5cncX214Jbpft-YI4gMBykv8JvZccAHNqk22i0XSTjtA5LHii7F8dFoMymz9MYqfipBJ_FQFVkFYfwD1ewvQ-eQ6Rka6yyJqvHr2IGkgkqYbkGYnuHgaOE3RJn6xgevnlKzxUS6b5zY22rRc2DCE7CVf-gm1AHi1PgdMiPfmotdud98xwhPkddUcwJZl3-KxU3EoRFOMsFHdE3IsQhRdMH0QLvqx_SOpTl-DU9zWGPEHKz9oqyLGVoTlra2H_FjhHgj_amlFRY92XLiUemxdJeCX1B4KPOWqZ4C74KYXPR8bnMAmdVNrrEk3igBp5WnQavFCSpc-mtZk7aiqQJQ9zX32eIE98sUDo-K77iINUhkSHjADnnEuWh95T8gydAPvxroyh_TKbyzDiY080rGV7mxUU6Nbxhi5-Olihd-tsNsgLo";

                String test = token;


                //TO use locally
                //url = new URL("http://10.0.2.2:61902/api/Ticket/TicketReg");

                url = new URL("http://ticketapp.shiftbook.dk/api/Ticket/TicketRequest");
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

                if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_ACCEPTED) {


                    Intent intent = new Intent(RequestActivity.this, MainMenuActivity.class);
                    startActivity(intent);

                    String responseString = readStream(urlConnection.getInputStream());
                    String books = responseString;


                } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED){

                    Intent intentLogin = new Intent(RequestActivity.this, LogInActivity.class);
                    startActivity(intentLogin);

                    Toast.makeText(getApplicationContext(), "Please login/signup, to request a ticket.",
                            Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return null;
        }
        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
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

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }


    }
}
