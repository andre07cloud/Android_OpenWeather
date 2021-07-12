package com.example.weatherzoneapp;

public class FavoriteCity {

    private String cityName;
    private int cityCode;

    public FavoriteCity() {
    }

    public FavoriteCity(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }
}
