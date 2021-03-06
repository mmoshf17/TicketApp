package com.example.gvida.ticketapp2;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Muheb.moshfiq on 5/23/2018.
 */

public class FlightTicketsDetails extends AppCompatActivity{

    private FlightTickets flightTickets;
    private TextView email;
    private TextView user;
    private TextView price;
    private TextView description;
    private TextView from;
    private TextView to;
    //private TextView isAuction;
    //private EditText bid;
    //private ImageView imageView;

    @Override
    protected void onStart() {
        super.onStart();
        ReadTask task = new ReadTask();
        //task.execute("http://10.0.2.2:61902/api/GetTicket/GetComments/?ticketId=" + ticket.getTicketId());
        task.execute("http://ticketapp.shiftbook.dk/api/GetTicket/GetComments/?ticketId=" + flightTickets.getTicketId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flightdetails);

        Toolbar toolbar = findViewById(R.id.toolbar_flight_ticket_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("name of the ticket");


        Intent intent = getIntent();
        flightTickets = (FlightTickets) intent.getSerializableExtra("FlightTickets");


        user = findViewById(R.id.ownerFlight);
        user.setText(flightTickets.getUser());

        email = findViewById(R.id.email_txtFlight);
        email.setText(flightTickets.getEmail());

        price = findViewById(R.id.price_txtFlight);
        price.setText( "US $" + flightTickets.getPrice());

        description = findViewById(R.id.description_txtFlight);
        description.setText(flightTickets.getDescription());

        from = findViewById(R.id.fromtktdetailsFlight);
        from.setText(flightTickets.getFromFlights());

        to = findViewById(R.id.totktdetailsFlight);
        to.setText(flightTickets.getToFlights());



    }

    public void SendCommentButton(View view) {

        new HttpClient9().execute();
    }

    public void onFlightBuyBtn(View view) {

        Intent intentBuy = new Intent(this, Payment.class);
        startActivity(intentBuy);
    }

    private class ReadTask extends ReadHttpTask {
        @Override
        protected void onPostExecute(CharSequence jsonString) {
            TextView messageTextView = findViewById(R.id.show_list);


            //Gets the data from database and show all tickets into list by using loop
            final List<Comment> comm1 = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(jsonString.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);

                    //String ticketId = obj.getString("TicketId");
                    String userId = obj.getString("UserId");
                    String comment = obj.getString("Comment");


                    Comment comm = new Comment(userId, comment);


                    comm1.add(comm);


                }


                ListView listView = findViewById(R.id.list_view_postsFlight);
                ArrayAdapter<Comment> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, comm1);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {

                });
            } catch (JSONException ex) {
                messageTextView.setText(ex.getMessage());
                Log.e("FlightTickets", ex.getMessage());
            }


        }
    }


    //Save comments to database
    public class HttpClient9 extends AsyncTask<String, Void, Void> {


        EditText c = (EditText) findViewById(R.id.CommentBoxFlight);




        @Override
        protected Void doInBackground(String... params){
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("ticketId", flightTickets.getTicketId());
                postDataParams.put("comment", c.getText());


                SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                String token = sharedPref.getString("token", "");



                String test = token;

                url = new URL("http://ticketapp.shiftbook.dk/api/Ticket/SaveComment");
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

                //urlConnection.setRequestProperty("Authorization", "Bearer "+ "pLTT7b4-9Tm_oVioiEFYGJpT25sFTvtBddeM2eDCXdbuEopv7yAC0tXfD50e3lQDvdBYGj70AFJu7n32BK5cncX214Jbpft-YI4gMBykv8JvZccAHNqk22i0XSTjtA5LHii7F8dFoMymz9MYqfipBJ_FQFVkFYfwD1ewvQ-eQ6Rka6yyJqvHr2IGkgkqYbkGYnuHgaOE3RJn6xgevnlKzxUS6b5zY22rRc2DCE7CVf-gm1AHi1PgdMiPfmotdud98xwhPkddUcwJZl3-KxU3EoRFOMsFHdE3IsQhRdMH0QLvqx_SOpTl-DU9zWGPEHKz9oqyLGVoTlra2H_FjhHgj_amlFRY92XLiUemxdJeCX1B4KPOWqZ4C74KYXPR8bnMAmdVNrrEk3igBp5WnQavFCSpc-mtZk7aiqQJQ9zX32eIE98sUDo-K77iINUhkSHjADnnEuWh95T8gydAPvxroyh_TKbyzDiY080rGV7mxUU6Nbxhi5-Olihd-tsNsgLo");

                int responseCode = urlConnection.getResponseCode();
                String responseMessage = urlConnection.getResponseMessage();

                if(responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_ACCEPTED) {


                    finish();
                    startActivity(getIntent());

                }

                    else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {

                        Intent intent7 = new Intent(getApplicationContext(), LogInActivity.class);
                        startActivity(intent7);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getBaseContext(), "Please login/signup, to comment!", Toast.LENGTH_LONG).show();
                            }
                        });
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
