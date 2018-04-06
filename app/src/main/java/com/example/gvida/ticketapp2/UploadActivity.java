package com.example.gvida.ticketapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * Created by gvida on 20/03/2018.
 */

public class UploadActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
