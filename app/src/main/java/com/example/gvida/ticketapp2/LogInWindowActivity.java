package com.example.gvida.ticketapp2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by gvida on 21/03/2018.
 */

public class LogInWindowActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_window_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
