package com.example.matt.lifxnight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    TextView response_text;
    static final String apiKey = "ca9dc5544104e8ac5d56444926865ae5e6f890daa58d3bc2ce5a79e7381ab069";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        response_text = (TextView) findViewById(R.id.response_text);


        Button queryButton = (Button) findViewById(R.id.button_API_test);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RetrieveFeedTask().execute();
            }
        });
    }




class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

    private Exception exception;

    protected void onPreExecute() {

        response_text.setText("");
    }

    protected String doInBackground(Void... urls) {


        try {
            URL url = new URL("https://api.lifx.com/v1/lights/all");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + apiKey);
            urlConnection.setRequestMethod("GET");
            System.out.println("Hello");
            try {
                System.out.println("Hello");
                /*BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();*/ //i think this will work if i have bulbs

                /*urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                wr.write("power=on");
                wr.flush();



                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();*/

                return urlConnection.getHeaderFields().toString(); // works well

            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String response) {
        if (response == null) {
            response = "THERE WAS AN ERROR";
        }

        Log.i("INFO", response);
        response_text.setText(response);
    }
}}