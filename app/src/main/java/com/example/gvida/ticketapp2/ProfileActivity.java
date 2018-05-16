package com.example.gvida.ticketapp2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ImageView addProfilePic;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);

        ListView listOfPosts1 = findViewById(R.id.list_of_posts_profile);
        ArrayList<String> arrayOfPosts1 = new ArrayList<>();
        arrayOfPosts1.addAll(Arrays.asList(getResources().getStringArray(R.array.list_of_posts_array)));

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                ProfileActivity.this,
                android.R.layout.simple_list_item_1,
                arrayOfPosts1);
        listOfPosts1.setAdapter(adapter1);

        drawerLayout = findViewById(R.id.profile_activity_drawer_layout);
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
            if (item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
            drawerLayout.closeDrawers();
            switch (item.getItemId()) {
                case R.id.Home:
                    Intent intent2 = new Intent(getApplicationContext(), MainMenuActivity.class);
                    startActivity(intent2);
                    break;

                case R.id.Account:

                    Intent intent3 = new Intent(getApplicationContext(), LogInActivity.class);
                    startActivity(intent3);
                    break;

                case R.id.request_your_ticket:
                    Intent intent = new Intent(getApplicationContext(), RequestActivity.class);
                    startActivity(intent);
                    break;

                case R.id.Sell_your_ticket:
                    Intent intent1 = new Intent(getApplicationContext(), SellActivity.class);
                    startActivity(intent1);
                    break;
            }
            return false;
        });

        addProfilePic = findViewById(R.id.profile_image);
        addProfilePic.setOnClickListener(e -> selectImage());
    }

    public void logOut(View view) {

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.clear();
        editor.apply();
       // finish();

        Intent intent5 = new Intent(getApplicationContext(), MainMenuActivity.class);
        startActivity(intent5);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return mToggle.onOptionsItemSelected(menuItem) || super.onOptionsItemSelected(menuItem);
    }

    private void selectImage() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Image");

        builder.setItems(items, (dialogInterface, i) -> {
            if (items[i].equals("Camera")) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);
            } else if (items[i].equals("Gallery")) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, SELECT_FILE);
            } else if (items[i].equals("Cancel")) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                if (ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},
                            1);
                }
                Bundle bundle = data.getExtras();
                Bitmap bmp = (Bitmap) bundle.get("data");
                addProfilePic.setImageBitmap(bmp);


            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                addProfilePic.setImageURI(selectedImageUri);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    // permission was granted, yay! Do the
                    // camera-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    Toast.makeText(this, "Permission denied to read SMS", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
