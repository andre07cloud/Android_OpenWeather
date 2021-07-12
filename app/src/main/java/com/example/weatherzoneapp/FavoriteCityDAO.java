package com.example.weatherzoneapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

public class FavoriteCityDAO extends SQLiteOpenHelper {

    private static final int DB_Version=1;
    private static final String DB_NAME="DBFavoriteCity";
    private final static String TABLE_NAME="FavoriteCity";
    private final static String CITY_NAME="cityName";

    public FavoriteCityDAO(@Nullable Context context) {
        super(context, DB_NAME, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE FavoriteCity( " + " codecity INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cityName TEXT"  +")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS FavoriteCity");
        this.onCreate(db);
    }

    public void AddFavorite(FavoriteCity f){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues v= new ContentValues();
        v.put(CITY_NAME,f.getCityName());

        db.insert(TABLE_NAME,null,v);
        db.close();
    }

    public List<FavoriteCity> listFavorite(){
        List<FavoriteCity> favorites= new LinkedList<FavoriteCity>();
        String q= "SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor c= db.rawQuery(q,null);
        FavoriteCity f=null;
        if(c.moveToFirst()){
            do {
                f= new FavoriteCity();
                f.setCityCode(Integer.parseInt(c.getString(0)));
                f.setCityName(c.getString(1));
                favorites.add(f);
            }while (c.moveToNext());
        }
        return favorites;
    }
}
