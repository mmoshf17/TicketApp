package com.example.gvida.ticketapp2;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

/**
 * Created by Rehan Mir on 17-04-2018.
 */

public class SellActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener{

    //private TextView displayDate;
    //private DatePickerDialog.OnDateSetListener selectDateListener;

    Button btn_SelectDateTime;
    TextView resultOfDateTime;
    //EditText from;
    //EditText to;
    int day, month, year, hour, minute;
    int day_x, month_x, year_x, hour_x, minute_x;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_activity);

        Toolbar toolbar = findViewById(R.id.toolbar_sell_activity);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout_sell_Activity);
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
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView nv = findViewById(R.id.nav_id_sell);
        nv.setNavigationItemSelectedListener(item -> {
            if(item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
            drawerLayout.closeDrawers();
            switch (item.getItemId())
            {
                case R.id.Home:
                    Intent intent2 = new Intent(getApplicationContext(), MainMenuActivity.class);
                    startActivity(intent2);
                    break;

                case R.id.Account:

                    Intent intent3 = new Intent(getApplicationContext(), LogInActivity.class);
                    startActivity(intent3);
                    break;

                case R.id.request_your_ticket:
                    Intent intent = new Intent(getApplicationContext(), RequestActivity.class);
                    startActivity(intent);
                    break;

                case R.id.Sell_your_ticket:
                    Intent intent1 = new Intent(getApplicationContext(), SellActivity.class);
                    startActivity(intent1);
                    break;
            }
            return false;
        });

        //For Ticket category
        Spinner spinner = findViewById(R.id.dropDownCatagory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //from = findViewById(R.id.fromTicket);
        //to = findViewById(R.id.toTicket);


        //For From flights category dropdown
        Spinner fromFlightSpinner = findViewById(R.id.fromTicket);

        ArrayAdapter<CharSequence> fromFlightsAdapter = ArrayAdapter.createFromResource(this,
                R.array.flighs_destinations, android.R.layout.simple_spinner_dropdown_item);
        fromFlightsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromFlightSpinner.setAdapter(fromFlightsAdapter);



        //For To Flights category dropdown
        Spinner toFlightSpinner = findViewById(R.id.toTicket);

        ArrayAdapter<CharSequence> toFlightsAdapter = ArrayAdapter.createFromResource(this,
                R.array.flighs_destinations, android.R.layout.simple_spinner_dropdown_item);
        toFlightsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toFlightSpinner.setAdapter(toFlightsAdapter);






                //For Date & Time
        btn_SelectDateTime = findViewById(R.id.selectDateTime);
        resultOfDateTime = findViewById(R.id.dateTimeResult);

        btn_SelectDateTime.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             Calendar c = Calendar.getInstance();

             year = c.get(Calendar.YEAR);
             month = c.get(Calendar.MONTH);
             day = c.get(Calendar.DAY_OF_MONTH);



             DatePickerDialog datePickerDialog = new DatePickerDialog(SellActivity.this , SellActivity.this, year, month, day);
             datePickerDialog.show();

          }
          });
    }


    public void upload(View view) {
        new HttpClient3().execute();
    }

    //Date & Time
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        year_x = year;
        month_x = month;
        day_x = dayOfMonth;

        Calendar c = Calendar.getInstance();

        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(SellActivity.this , SellActivity.this, hour, minute, true);
        timePickerDialog.show();


    }

    //DateTime
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        hour_x = hourOfDay;
        minute_x = minute;

        String hourString;
        if (hour_x < 10)
            hourString = "0" + hour_x;
        else
            hourString = "" +hour_x;

        String minuteString;
        if (minute_x < 10)
            minuteString = "0" + minute_x;
        else
            minuteString = "" + minute_x;

        String monthString;
        if (month_x < 10)
            monthString = "0" + month_x;
        else
            monthString = "" + month_x;

        String dayString;
        if (day_x < 10)
            dayString = "0" + day_x;
        else
            dayString = "" + day_x;

        resultOfDateTime.setText(year_x + "-" + monthString + "-" + dayString + "T" + hourString + ":" + minuteString);
       // resultOfDateTime.setText(year_x + "-" + month_x + "-" + day_x + "-" + " " + hour_x + ":" + minute_x);
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String spinner = parent.getItemAtPosition(position).toString();

        TextView fromFlightText = findViewById(R.id.fromTicketTextVeiw);
        Spinner fromFlightSpinner = findViewById(R.id.fromTicket);

        Spinner toFlightSpinner = findViewById(R.id.toTicket);
        TextView toFlightText = findViewById(R.id.toTicketTextView);

        if (spinner.toString().equals("Flights")){

            fromFlightSpinner.setVisibility(View.VISIBLE);
            fromFlightText.setVisibility(View.VISIBLE);

            toFlightSpinner.setVisibility(View.VISIBLE);
            toFlightText.setVisibility(View.VISIBLE);

        }

        else{

            fromFlightSpinner.setVisibility(View.GONE);
            fromFlightText.setVisibility(View.GONE);

            toFlightSpinner.setVisibility(View.GONE);
            toFlightText.setVisibility(View.GONE);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }






    //Selling a Ticket
    public class HttpClient3 extends AsyncTask<String, Void, Void> {


        EditText ticketName = (EditText) findViewById(R.id.ticketName);
        EditText ticketPrice = (EditText) findViewById(R.id.ticketPrice);
        EditText ticketDescription = (EditText) findViewById(R.id.ticketDescription);
        //RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio);
        Spinner spinner = findViewById(R.id.dropDownCatagory);
        Spinner fromSpinner = findViewById(R.id.fromTicket);
        Spinner toSpinner = findViewById(R.id.toTicket);
        //RadioButton radioBuy = (RadioButton)findViewById(R.id.radioBuy);
        //RadioButton radioAuction = (RadioButton)findViewById(R.id.radioAuction);

        @Override
        protected Void doInBackground(String... params) {

            URL url;
            HttpURLConnection urlConnection = null;

            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("name", ticketName.getText());
                postDataParams.put("startingDate", resultOfDateTime.getText());
                postDataParams.put("category", spinner.getSelectedItem().toString());
                postDataParams.put("price", ticketPrice.getText());
                postDataParams.put("description", ticketDescription.getText());
                postDataParams.put("FromFlights", fromSpinner.getSelectedItem().toString());
                postDataParams.put("ToFlights", toSpinner.getSelectedItem().toString());


                SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                String token = sharedPref.getString("token", "");

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

                if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_ACCEPTED) {


                    Intent intent = new Intent(SellActivity.this, MainMenuActivity.class);
                    startActivity(intent);

                    String responseString = readStream(urlConnection.getInputStream());
                    String books = responseString;


                } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED){

                    Intent intentLogin = new Intent(SellActivity.this, LogInActivity.class);
                    startActivity(intentLogin);

                    Toast.makeText(getApplicationContext(), "Please login/signup, to sell a ticket.",
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
