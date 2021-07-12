package com.example.weatherzoneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Stories extends AppCompatActivity {

    ImageButton btnMain;
    TextView txtDt, txtHst;
    private final String url1="http://api.openweathermap.org/data/2.5/onecall/timemachine";
    //private final String url="http://api.openweathermap.org/data/2.5/weather";
    private final String appid="969f06b6c0988a3f4c5aee2ab1977ce1";
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        btnMain = (ImageButton) findViewById(R.id.btnMain);
        txtDt = findViewById(R.id.txtDt);
        //txtHst = findViewById(R.id.txtHst);
        //*********SHOW STORY ***********************

        Intent intent2 = getIntent();
        String city = intent2.getStringExtra("cityName");
        double longitude = intent2.getDoubleExtra("longitude",0.0);
        double latitude = intent2.getDoubleExtra("latitude",0.0);
        long date =intent2.getLongExtra("date",1485722804) ;
        String tempurl1= url1 + "?lat=" + latitude +"&lon="+ longitude + "&dt=" + date + "&appid=" + appid;
        String malon =""+longitude;
        Log.d("response",malon);


        //******************Historical Sercice***********************
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempurl1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
                String output = "";
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("hourly");
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject item1 = jsonArray.getJSONObject(i);
                        Date date = new Date(item1.getLong("dt")*1000);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy'T'HH:mm");
                        String dateString = sdf.format(date);
                        double temp = item1.getDouble("temp")-273.15;
                        float pressure = item1.getInt("pressure");
                        int humidity = item1.getInt("humidity");
                        JSONArray jsonArrayWeather = item1.getJSONArray("weather");

                        JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");
                        String mood = jsonObjectWeather.getString("main");

                        txtDt.setTextColor(Color.WHITE);

                        output += "\n"+dateString
                                + "\n Temp: " + df.format(temp) + " °C"
                                +"\n Humidity: " + humidity + "%"
                                +"\n Climat: "+ mood
                                +"\n Description: " + description
                                +"\n Pressure: " + pressure + "hpa\n"
                                +"\n---------------------------------\n";

                        String i1=""+date;
                        Log.d("mon index",dateString);

                    }
                    txtDt.setText(output);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString().trim(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    public void goHome(View view) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void goFavories(View view) {

        Intent intent = new Intent(getApplicationContext(), ListFavorite.class);
        startActivity(intent);
    }
}