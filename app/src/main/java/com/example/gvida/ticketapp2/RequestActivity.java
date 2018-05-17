package com.example.gvida.ticketapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.Objects;

/**
 * Created by gvida on 20/03/2018.
 */

public class RequestActivity extends AppCompatActivity
{

    Button requestButton;

    //this is for the side menu
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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

        requestButton = findViewById(R.id.request_button_in_request_page);
        requestButton.setOnClickListener(e -> {
            Toast.makeText(getApplicationContext(), "this button is pressed", Toast.LENGTH_SHORT).show();
        });

        //drawer layout
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView nv = findViewById(R.id.nav_id);
        nv.setNavigationItemSelectedListener(item -> {
            if(item.isChecked()) item.setChecked(false);
            else item.setChecked(true);

            SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

            String token = sharedPref.getString("token", "");

            drawerLayout.closeDrawers();
            switch (item.getItemId())
            {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        if(mToggle.onOptionsItemSelected(menuItem))
        {
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }


}
