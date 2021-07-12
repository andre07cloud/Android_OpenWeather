package com.example.weatherzoneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editSearch;
    TextView txtvide;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtvide = findViewById(R.id.txtvide);
        editSearch  = findViewById(R.id.editSearch);
    }

    public void goMain(View view) {
        if (editSearch.getText().toString().isEmpty()){
            txtvide.setText("City field can not be empty!");
        }
        else{
            Intent intent = new Intent(getApplicationContext(), ShowZOne.class);
            intent.putExtra("search",editSearch.getText().toString());
            startActivity(intent);
        }

    }

    public void listeFavorite1(View view) {

        Intent intent = new Intent(getApplicationContext(), ListFavorite.class);
        startActivity(intent);

    }

    public void exitApp(View view) {
        finish();
    }
}