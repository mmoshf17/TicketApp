package com.example.gvida.ticketapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Muheb.moshfiq on 5/28/2018.
 */

public class Payment extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);



    }

    public void btnPay(View view) {

        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
        finish();

        Toast.makeText(getApplicationContext(), "Payment completed!.",
                Toast.LENGTH_LONG).show();
    }
}