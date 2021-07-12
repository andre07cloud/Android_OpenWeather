package com.example.weatherzoneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListFavorite extends AppCompatActivity {

    ListView listeFilmsAff;
    List<FavoriteCity> listeFavorites;
    FavoriteCityDAO d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l_ist_favories);
        listeFilmsAff= findViewById(R.id.listFavorites);
        listeFavorites= new LinkedList<FavoriteCity>();
        d= new FavoriteCityDAO(this);

        /********************Afficher la liste des favoris****/
        listeFavorites= d.listFavorite();
        List<String> cityNames= new ArrayList<String>();
        for(FavoriteCity f:listeFavorites){
            cityNames.add(f.getCityName());
        }

        ArrayAdapter<String> adapterTitre= new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                cityNames);
        listeFilmsAff.setAdapter(adapterTitre);

    }

    public void goFull(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

    public void goFirst(View view) {

        finish();
    }
}