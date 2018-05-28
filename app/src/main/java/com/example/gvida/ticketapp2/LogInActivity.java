package com.example.gvida.ticketapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by gvida on 20/03/2018.
 */

public class LogInActivity extends AppCompatActivity
{

    Button logIn, signUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_selection_window);

       //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Introduced the 2 buttons, which are in this Activity
        logIn = (Button) findViewById(R.id.log_in_selection_window);
        signUp = (Button) findViewById(R.id.sign_up_selection_window);

        // When we click on login button, it goes to the next activity (LoginWindowActivity)
        logIn.setOnClickListener((view -> {
            Intent intent = new Intent(LogInActivity.this, LogInWindowActivity.class);
            startActivity(intent);
            finish();
        }));

        // When we click on signUp button, it goes to the next activity (SignUpWindowActivity)
        signUp.setOnClickListener((view -> {
            Intent intent = new Intent(LogInActivity.this, SignUpWindowActivity.class);
            startActivity(intent);
            finish();
        }));
    }
}
