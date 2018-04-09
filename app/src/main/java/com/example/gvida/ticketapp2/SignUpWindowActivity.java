package com.example.gvida.ticketapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

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

      //  signUp = (Button) findViewById(R.id.sign_up_button_in_sign_up);
      //  etName = (EditText) findViewById(R.id.enter_name_field);
       // etPassword = (EditText) findViewById(R.id.enter_password_field);
      //  etPassword2 = (EditText) findViewById(R.id.repeat_password_field);
      //  etEmail = (EditText) findViewById(R.id.enter_your_email_address);
      //  etEmail2 = (EditText) findViewById(R.id.repeat_your_email_address);
      //  profilePic = (ImageButton) findViewById(R.id.upload_photo_button);



    }

    public void signupBt(View view) {
        new HttpClient1().execute();
    }

    public class HttpClient1 extends AsyncTask<String, Void, Void> {

        EditText enterName = (EditText)findViewById(R.id.enter_name_field);
        EditText enterEmail = (EditText)findViewById(R.id.enter_your_email_address);
        EditText repeatEmail = (EditText)findViewById(R.id.repeat_your_email_address);
        EditText enterPass = (EditText)findViewById(R.id.enter_password_field);
        EditText repeatPass = (EditText)findViewById(R.id.repeat_password_field);


        TextView lblError = (TextView) findViewById(R.id.signupError);

        @Override
        protected Void doInBackground(String... params){
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("Name", enterName.getText());
                postDataParams.put("Email", enterEmail.getText());
                postDataParams.put("RepeatEmail", repeatEmail.getText());
                postDataParams.put("Password", enterPass.getText());
                postDataParams.put("ConfirmPassword", repeatPass.getText());


                url = new URL("http://ticketapp.shiftbook.dk/api/Account/Register");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();
                //HTTP header
                //urlConnection.setRequestProperty("Authorization", "Bearer "+ token);

                int responseCode = urlConnection.getResponseCode();
                String responseMessage = urlConnection.getResponseMessage();

                if(responseCode == HttpURLConnection.HTTP_OK){


                    Intent intent = new Intent(SignUpWindowActivity.this, MainMenuActivity.class);
                    startActivity(intent);

                    String responseString = readStream(urlConnection.getInputStream());
                    String books = responseString;


                }else{
                    lblError.setText("Sign-up failed. Please try again");
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(urlConnection != null)
                    urlConnection.disconnect();
            }
            return null;
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while(itr.hasNext()){

                String key= itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }

        private String readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuffer response = new StringBuffer();
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return response.toString();
        }

        protected void onPostExecute(Void result){
            super.onPostExecute(result);

        }
    }


}

