package com.example.gvida.ticketapp2;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class MainMenuActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{
    @Override
    protected void onStart() {
        //Shows only events on the beginning of the activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.onStart();
        ReadTask task3 = new ReadTask();


        task3.execute("http://ticketapp.shiftbook.dk//api/GetTicket/GetTicketByCategory/?category=" + events);
    }

    private String events = "events";


    //this is only for the list of items in the main screen activity

    int day, month, year;
    int day_x, month_x, year_x;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    private SearchView searchView;
    private EditText editText;
    private ListView listView;
    private String selectedCat;
    private String selectFlightCat;
    private TextView resultDate;
    private Button selectedDeparture;
    private EditText toFlightsBox;
    private EditText fromFlightsBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        fromFlightsBox = findViewById(R.id.search_view);
        toFlightsBox = findViewById(R.id.search_view2);

        //toolbar stuff
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView listHeader = new TextView(this);
        listHeader.setText("Tickets");
        listHeader.setTextAppearance(this, android.R.style.TextAppearance_Large);
        ListView listView = findViewById(R.id.list_view_posts);
        listView.addHeaderView(listHeader);




        //Side menu stuff
        drawerLayout = findViewById(R.id.DrawerLayout);
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

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        String token = sharedPref.getString("token", "");

        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView nv = findViewById(R.id.nav_id);
        nv.setNavigationItemSelectedListener(item -> {
            if (item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
            drawerLayout.closeDrawers();
            switch (item.getItemId()) {
                case R.id.Home:
                    Intent intent2 = new Intent(getApplicationContext(), MainMenuActivity.class);
                    startActivity(intent2);
                    finish();
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


                case R.id.request_your_ticket:
                    Intent intent = new Intent(getApplicationContext(), RequestActivity.class);
                    startActivity(intent);
                    break;
//
                case R.id.Sell_your_ticket:

                    if (Objects.equals(token, "")) {

                        Intent intent7 = new Intent(getApplicationContext(), LogInActivity.class);
                        startActivity(intent7);

                        Toast.makeText(getApplicationContext(), "Please login/signup, to sell a ticket.",
                                Toast.LENGTH_LONG).show();

                        break;
                    } else if (!Objects.equals(token, "")) {

                        Intent intent1 = new Intent(getApplicationContext(), SellActivity.class);
                        startActivity(intent1);
                        break;
                    }

            }
            return false;
        });


        final RadioGroup radio = findViewById(R.id.radiobtns);
        RadioButton radioEvent = findViewById(R.id.radioButton2);
        RadioButton radioSport = findViewById(R.id.radioButton);
        RadioButton radioFlights = findViewById(R.id.radioButton3);
        fromFlightsBox = findViewById(R.id.search_view);

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = radio.findViewById(checkedId);
                int index = radio.indexOfChild(radioButton);

                switch (index) {
                    case 0: // first button

                        String radioEventValue = radioEvent.getText().toString();

                       selectedCat = radioEventValue;

                        if (radioEvent.isChecked()){

                            //toFlightsBox = findViewById(R.id.search_view2);
                            toFlightsBox.setVisibility(View.GONE);
                            fromFlightsBox.setHint("Search Ticket");
                            selectedDeparture.setVisibility(View.GONE);
                            resultDate.setVisibility(View.GONE);
                        }
                        break;

                    case 1: // secondbutton

                        String radioSportValue = radioSport.getText().toString();
                        selectedCat = radioSportValue;

                        if (radioSport.isChecked()){

                            //toFlightsBox = findViewById(R.id.search_view2);
                            toFlightsBox.setVisibility(View.GONE);
                            fromFlightsBox.setHint("Search Ticket");
                            selectedDeparture.setVisibility(View.GONE);
                            resultDate.setVisibility(View.GONE);

                        }
                        break;

                    case 2: // secondbutton

                        String radioFlightsValue = radioFlights.getText().toString();
                        selectFlightCat = radioFlightsValue;

                        if (radioFlights.isChecked()){

                            //toFlightsBox = findViewById(R.id.search_view2);
                            toFlightsBox.setVisibility(View.VISIBLE);
                            fromFlightsBox.setHint("From");
                            selectedDeparture.setVisibility(View.VISIBLE);
                            resultDate.setVisibility(View.VISIBLE);

                        }
                        break;

                }
            }
        });

        resultDate = findViewById(R.id.resultDeparture);
        selectedDeparture = findViewById(R.id.departure_date);

        selectedDeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();

                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);



                DatePickerDialog datePickerDialog = new DatePickerDialog(MainMenuActivity.this, MainMenuActivity.this, year, month, day);
                datePickerDialog.show();

            }
        });
    }

//3 lines (Hamburger icon)
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (mToggle.onOptionsItemSelected(menuItem)) {
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void btnSearch(View view) {


        editText = findViewById(R.id.search_view);
        ReadTask task4 = new ReadTask();
        ReadTask1 task5 = new ReadTask1();

        RadioButton radioEvent = findViewById(R.id.radioButton2);
        RadioButton radioSport = findViewById(R.id.radioButton);

        try {
            if (radioEvent.isChecked() || radioSport.isChecked())
            {
           task4.execute("http://ticketapp.shiftbook.dk/api/GetTicket/GetSearchTicket/?name=" + editText.getText().toString()
                   + "&categoryId=" + selectedCat).get();
            }

            else {
                task5.execute("http://ticketapp.shiftbook.dk/api/GetTicket/GetFlightTicket?origin="
                        + fromFlightsBox.getText().toString() + "&dest=" + toFlightsBox.getText().toString()
                        + "&categoryId=" + selectFlightCat).get();

            }
        }

        catch (Exception ex){

            listView.setAdapter(null);
            Toast.makeText(getApplicationContext(), "Sorry, ticket not found, But you can request for the ticket and you will get a notification, whenever the ticket is available", Toast.LENGTH_LONG).show();
            Exception dd = ex;
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        year_x = year;
        month_x = month;
        day_x = dayOfMonth;


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


        resultDate.setText(year_x + "-" + monthString + "-" + dayString);

    }



    private class ReadTask extends ReadHttpTask {
        @Override
        protected void onPostExecute(CharSequence jsonString) {

            TextView messageTextView = findViewById(R.id.show_list);


            //Gets the data from database and show all tickets into list by using loop
            final List<Tickets> tkt1 = new ArrayList<>();

            try {
                JSONArray array = new JSONArray(jsonString.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    //Get the following data from Database
                    int ticketId = obj.getInt("TicketId");
                    String name = obj.getString("Name");
                    String category = obj.getString("Category");
                    String startingDate = obj.getString("StartingDate");
                    String price = obj.getString("Price");
                    String description = obj.getString("Description");
                    String user = obj.getString("User");
                    String email = obj.getString("Email");

                    //Created object of the Ticket class, to access the class & Passing values to the constructor
                    Tickets tkt = new Tickets(ticketId, user, category, startingDate, email, name, price, description);

                    //Adding values to the list
                    tkt1.add(tkt);

                }


                listView = findViewById(R.id.list_view_posts);

                ArrayAdapter<Tickets> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, tkt1);


                listView.setAdapter(adapter);




                listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
                    Intent intent = new Intent(getBaseContext(), TicketDetails.class);
                    Tickets tkt = (Tickets) parent.getItemAtPosition(position);
                    intent.putExtra("Tickets", tkt);

                    startActivity(intent);

                });
            } catch (JSONException ex) {
                messageTextView.setText(ex.getMessage());
                Log.e("Tickets", ex.getMessage());
            }


        }

    }


    private class ReadTask1 extends ReadHttpTask {
        @Override
        protected void onPostExecute(CharSequence jsonString) {

            TextView messageTextView = findViewById(R.id.show_list);


            //Gets the data from database and show all tickets into list by using loop
            final List<FlightTickets> tkt1 = new ArrayList<>();

            try {
                JSONArray array = new JSONArray(jsonString.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    //Get the following data from Database
                    int ticketId = obj.getInt("TicketId");
                    String name = obj.getString("Name");
                    String category = obj.getString("Category");
                    String startingDate = obj.getString("StartingDate");
                    String price = obj.getString("Price");
                    String description = obj.getString("Description");
                    String user = obj.getString("User");
                    String email = obj.getString("Email");
                    String fromFlights = obj.getString("FromFlights");
                    String toFlights = obj.getString("ToFlights");


                    //Created object of the Ticket class, to access the class & Passing values to the constructor
                    FlightTickets tkt = new FlightTickets(ticketId, user, category, startingDate, email, name, price, description, fromFlights, toFlights);

                    //Adding values to the list
                    tkt1.add(tkt);

                }


                listView = findViewById(R.id.list_view_posts);

                ArrayAdapter<FlightTickets> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, tkt1);


                listView.setAdapter(adapter);


                listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
                    Intent intent = new Intent(getBaseContext(), FlightTicketsDetails.class);
                    FlightTickets tkt = (FlightTickets) parent.getItemAtPosition(position);
                    intent.putExtra("FlightTickets", tkt);

                    startActivity(intent);

                });
            } catch (JSONException ex) {
                messageTextView.setText(ex.getMessage());
                Log.e("FlightTickets", ex.getMessage());
            }


        }




}}
