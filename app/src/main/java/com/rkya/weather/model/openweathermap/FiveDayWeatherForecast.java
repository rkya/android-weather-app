package com.rkya.weather.model.openweathermap;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FiveDayWeatherForecast {
    public int cnt;
    @SerializedName("list")
    public List<SingleDayWeatherForecast> weatherForecasts;
    public City city;
}
