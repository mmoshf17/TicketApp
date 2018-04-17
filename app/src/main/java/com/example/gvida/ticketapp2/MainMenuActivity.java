package com.example.gvida.ticketapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    Button search, upload, request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        upload = (Button) findViewById(R.id.upload_button);
        search = (Button) findViewById(R.id.searching_button);
        request = (Button)
        indViewById(R.id.request_buttons);

        search.setOnClickListener((view) -> {
            Intent intent = new Intent(MainMenuActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        upload.setOnClickListener((view -> {
            Intent intent = new Intent(MainMenuActivity.this, UploadActivity.class);
            startActivity(intent);
        }));

        request.setOnClickListener((view -> {
            Intent intent = new Intent(MainMenuActivity.this, RequestActivity.class);
            startActivity(intent);
        }));
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_actionbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.log_in:
                startActivity(new Intent(this, LogInActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

}
