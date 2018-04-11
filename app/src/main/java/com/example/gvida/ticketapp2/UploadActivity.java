package com.example.gvida.ticketapp2;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by gvida on 20/03/2018.
 */

public class UploadActivity extends AppCompatActivity
{
    //this is for the side menu
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_activity);

        Toolbar toolbar = findViewById(R.id.toolbar_upload);
        setSupportActionBar(toolbar);

        //Side menu stuff
        drawerLayout = findViewById(R.id.drawerLayout_upload);
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
                    Toast.makeText(getApplicationContext(), "this", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.request_your_ticket:
                    Intent intent = new Intent(getApplicationContext(), RequestActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "this", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.Sell_your_ticket:
                    Intent intent1 = new Intent(getApplicationContext(), UploadActivity.class);
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
