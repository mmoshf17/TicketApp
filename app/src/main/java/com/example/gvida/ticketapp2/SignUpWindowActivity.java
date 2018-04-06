package com.example.gvida.ticketapp2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by gvida on 21/03/2018.
 */

public class SignUpWindowActivity extends AppCompatActivity
{

    Button signUp;
    EditText etName, etPassword, etPassword2, etEmail, etEmail2;
    ImageButton profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_window_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        signUp = (Button) findViewById(R.id.sign_up_button_in_sign_up);
        etName = (EditText) findViewById(R.id.enter_name_field);
        etPassword = (EditText) findViewById(R.id.enter_password_field);
        etPassword2 = (EditText) findViewById(R.id.repeat_password_field);
        etEmail = (EditText) findViewById(R.id.enter_your_email_address);
        etEmail2 = (EditText) findViewById(R.id.repeat_your_email_address);
        profilePic = (ImageButton) findViewById(R.id.upload_photo_button);



    }
}
