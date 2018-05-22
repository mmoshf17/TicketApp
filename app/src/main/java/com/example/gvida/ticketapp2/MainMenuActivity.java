package com.example.gvida.ticketapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.RequiresPermission;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
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
import java.util.List;
import java.util.Objects;

public class MainMenuActivity extends AppCompatActivity {

private Tickets tickets;
    @Override
    protected void onStart() {
        //Shows only events on the beginning of the activity
        super.onStart();
        ReadTask task3 = new ReadTask();


        task3.execute("http://ticketapp.shiftbook.dk//api/GetTicket/GetTicketByCategory/?category=" + events);
    }

    private String events = "events";

    // RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiobtns);

    //this is only for the list of items in the main screen activity
    //ArrayAdapter<String> adapter;

    //this is for the side menu
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    private SearchView searchView;
    private EditText editText;
    private ListView listView;
    private String selectedCat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

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
        //int selectedId = radio.getCheckedRadioButtonId();
        RadioButton radioEvent = findViewById(R.id.radioButton2);
        RadioButton radioSport = findViewById(R.id.radioButton);
        RadioButton radioFlights = findViewById(R.id.radioButton3);

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = radio.findViewById(checkedId);
                int index = radio.indexOfChild(radioButton);

                // radio.check(radioEvent.getId());
                // Add logic here

                switch (index) {
                    case 0: // first button

                        String radioEventValue = radioEvent.getText().toString();

                       selectedCat = radioEventValue;


                        break;
                    case 1: // secondbutton

                        String radioSportValue = radioSport.getText().toString();
                        selectedCat = radioSportValue;
                        break;

                    case 2: // secondbutton

                        String radioFlightsValue = radioFlights.getText().toString();
                        selectedCat = radioFlightsValue;
                        break;
                }
            }
        });
    }


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


        try {

           CharSequence rws =  task4.execute("http://ticketapp.shiftbook.dk/api/GetTicket/GetSearchTicket/?name=" + editText.getText().toString()
                   + "&categoryId=" + selectedCat).get();


        }

        catch (Exception ex){

            listView.setAdapter(null);
            Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_SHORT).show();
            Exception dd = ex;
        }



       // if (!(tickets.getName().equals(  String.valueOf(editText))))


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
                    //String isAuction = obj.getString("IsAuction");


                    //Created object of the Ticket class, to access the class & Passing values to the constructor
                    Tickets tkt = new Tickets(ticketId, user, category, startingDate, email, name, price, description);
                    //Tickets tkt = new Tickets(name);
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


    /*private class ReadTask extends ReadHttpTask {
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
                                // boolean isAution = obj.getBoolean("isAuction");
                                // String userId = obj.getString("userId");
                                //Created object of the Ticket class, to access the class & Passing values to the constructor
                                Tickets tkt = new Tickets(ticketId, user, category, startingDate, email, name, price, description);
                                //Tickets tkt = new Tickets(name);
                                //Adding values to the list
                    tkt1.add(tkt);




                }

                ListView listView = findViewById(R.id.list_view_posts);
                ArrayAdapter<Tickets> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, tkt1);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
                    Intent intent = new Intent(getBaseContext(), TicketDetails.class);
                    Tickets tkt = (Tickets) parent.getItemAtPosition(position);
                    intent.putExtra("Tickets",tkt);

                    startActivity(intent);
                });
            } catch (JSONException ex) {
                messageTextView.setText(ex.getMessage());
                Log.e("Tickets", ex.getMessage());
            }


        }
    }*/


}
