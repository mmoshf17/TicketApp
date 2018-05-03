package com.example.gvida.ticketapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MainMenuActivity extends AppCompatActivity {

    //this is only for the list of items in the main screen activity
    ArrayAdapter<String> adapter;

    //this is for the side menu
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //toolbar stuff
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Everything here is for the list that shows in the main screen activity
        ListView listOfPosts = findViewById(R.id.list_view_posts);
        ArrayList<String> arrayOfPosts = new ArrayList<>();
        arrayOfPosts.addAll(Arrays.asList(getResources().getStringArray(R.array.list_of_posts_array)));

        adapter = new ArrayAdapter<String>(
                MainMenuActivity.this,
                android.R.layout.simple_list_item_1,
                arrayOfPosts);
        listOfPosts.setAdapter(adapter);

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

                    if (Objects.equals(token, "")){

                    Intent intent3 = new Intent(getApplicationContext(), LogInActivity.class);
                    startActivity(intent3);
                    break;}

                    else if (!Objects.equals(token, "")){
                        Intent intent4 = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent4);
                        break;
                    }


                case R.id.request_your_ticket:
                    Intent intent = new Intent(getApplicationContext(), RequestActivity.class);
                    startActivity(intent);
                    break;

                case R.id.Sell_your_ticket:

                    if (Objects.equals(token, "")){

                        Intent intent7 = new Intent(getApplicationContext(), LogInActivity.class);
                        startActivity(intent7);

                        Toast.makeText(getApplicationContext(), "Please login/signup, to sell a ticket.",
                                    Toast.LENGTH_LONG).show();

                        break;
                    }

                    else if (!Objects.equals(token, "")){

                        Intent intent1 = new Intent(getApplicationContext(), SellActivity.class);
                        startActivity(intent1);
                        break;
                    }

            }
            return false;
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        if(mToggle.onOptionsItemSelected(menuItem))
        {
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_actionbar, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }



}
