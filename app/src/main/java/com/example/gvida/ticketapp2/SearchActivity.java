package com.example.gvida.ticketapp2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by gvida on 20/03/2018.
 */

public class SearchActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
