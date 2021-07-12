package com.example.weatherzoneapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class ShowZOne extends AppCompatActivity {

    String city;
    Button btnStory;
    RelativeLayout myWeather;
    ImageButton btnSchearch, btnAddFav;
    ImageView imgMain;
    FavoriteCityDAO d;
    TextView tvResult, txtMyDate, txtMyTemp, txtMyHumidity, txtMyPressure, txtMyCloud;
    DecimalFormat df = new DecimalFormat("#.##");
    private double longitude, latitude;
    private long date;
    private final String url="http://api.openweathermap.org/data/2.5/weather";
    private final String appid="969f06b6c0988a3f4c5aee2ab1977ce1";

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_z_one);

        //Get intent Params

        Intent intent = getIntent();
        city = intent.getStringExtra("search");
        Log.d("Ma Villlllllle",city);

        tvResult = findViewById(R.id.tvResult);
        txtMyDate = findViewById(R.id.txtMyDate);
        txtMyTemp = findViewById(R.id.txtMyTemp);
        btnStory =(Button) findViewById(R.id.btnStory);
        imgMain = (ImageView)findViewById(R.id.imgMain);
        myWeather = (RelativeLayout) findViewById(R.id.myWeather);
        txtMyHumidity = findViewById(R.id.txtMyHumidity);
        txtMyPressure = findViewById(R.id.txtMyPressure);
        txtMyCloud = findViewById(R.id.txtMyCloud);
        btnAddFav = findViewById(R.id.btnAddFav);


        //**********Consuming API OPEN WEATHER*******************

        String tempurl = "";
        //String city = editVille.getText().toString().trim();
        if(city.equals("") || city.isEmpty()){
            Log.d("City Can not be empty",city);
            tvResult.setText("City field can not be empty");
            btnStory.setActivated(false);
        }
        else {
            tempurl = url + "?q=" + city + "&appid=" + appid;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempurl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("response",response);
                    String output = "";
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                        JSONObject jsonObjectCoord = jsonResponse.getJSONObject("coord");
                        setLongitude(jsonObjectCoord.getDouble("lon"));
                        setLatitude(jsonObjectCoord.getDouble("lat"));
                        Long dt = jsonResponse.getLong("dt");
                        setDate(jsonResponse.getLong("dt"));
                        Date date = new Date(dt*1000);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy'T'HH:mm");
                        String dateString = sdf.format(date);

                        JSONObject jsonObjectweather = jsonArray.getJSONObject(0);
                        String description = jsonObjectweather.getString("description");
                        String mood = jsonObjectweather.getString("main");
                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp")-273.15;
                        double feelsLike = jsonObjectMain.getDouble("feels_like")-273.15;
                        float pressure = jsonObjectMain.getInt("pressure");
                        int humidity = jsonObjectMain.getInt("humidity");
                        JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                        String wind = jsonObjectWind.getString("speed");
                        JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                        String clouds = jsonObjectClouds.getString("all");
                        JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                        String countryName = jsonObjectSys.getString("country");
                        String cityName = jsonResponse.getString("name");
                        tvResult.setTextColor(Color.WHITE);

                        output += "Current weather of " + cityName + "(" +countryName + ")"
                                + "\n Temp: " + df.format(temp) + " °C"
                                +"\n Feels Like: " + df.format(feelsLike) + " °C"
                                +"\n Humidity: " + humidity + "%"
                                +"\n Climat: "+ mood
                                +"\n Description: " + description
                                +"\n Wind Speed: " + wind + "m/s (meters per second)"
                                +"\n Cloudiness: " + clouds + "%"
                                +"\n Pressure: " + pressure + "hpa";
                        txtMyCloud.setText("Cloudiness\n" + clouds + "%");
                        txtMyPressure.setText("Pressure\n" + pressure + "hpa");
                        txtMyHumidity.setText("Humidity\n" + humidity + "%");
                        tvResult.setText(cityName);
                        txtMyDate.setText(dateString);
                        txtMyTemp.setText("" + df.format(temp) + " °C");

                        switch (mood){
                            case "Clear" :{
                                myWeather.setBackgroundResource(R.drawable.clear_sky);
                                imgMain.setImageResource(R.drawable.sunshine);
                                break;}
                            case "Clouds" :{
                                myWeather.setBackgroundResource(R.drawable.few_clouds);
                                imgMain.setImageResource(R.drawable.cloud);
                                break;}
                            case "Rain" :{
                                myWeather.setBackgroundResource(R.drawable.rain_weather);
                                imgMain.setImageResource(R.drawable.rain1);
                                break;}
                            default: {
                                myWeather.setBackgroundResource(R.drawable.back1);
                                imgMain.setImageResource(R.drawable.rain_weather);
                            }
                        }

                        btnStory.setVisibility(View.VISIBLE);
                        btnAddFav.setVisibility(View.VISIBLE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }



    }

    public void getStory(View view) {

        Intent intent = new Intent(getApplicationContext(),Stories.class);
        intent.putExtra("cityName",city);
        double longitude = getLongitude();
        double latitude = getLatitude();
        long date = getDate();
        intent.putExtra("longitude",longitude);
        intent.putExtra("latitude",latitude);
        intent.putExtra("date",date);
        startActivity(intent);


        String pression = ""+date;
        Log.d("ma pression",pression );

    }

    public void listeFavorite(View view) {

        Intent intent = new Intent(getApplicationContext(), ListFavorite.class);
        startActivity(intent);
    }

    public void goAddFavorite(View view) {

        Intent intent = new Intent(getApplicationContext(), ListFavorite.class);
        intent.putExtra("cityName",city);
        startActivity(intent);
        d= new FavoriteCityDAO(this);
        FavoriteCity f= new FavoriteCity();
        f.setCityName(city);
        if (d!=null) {
            d.AddFavorite(f);

            Toast.makeText(getApplicationContext(), "City added to Favorite", Toast.LENGTH_SHORT);
        }

    }

    public void goSearch(View view) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}