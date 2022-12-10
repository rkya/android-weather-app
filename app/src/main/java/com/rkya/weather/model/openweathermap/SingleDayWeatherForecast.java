package com.rkya.weather.model.openweathermap;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.ToString;

@ToString
public class SingleDayWeatherForecast {
    List<Weather> weather;
    @SerializedName("dt")
    long time;
    MainMetrics main;
    Clouds clouds;
    Wind wind;
    int visibility; // Max value is 10_000
    @SerializedName("pop")
    double probabilityOfPrecipitation; // Values range between 0 and 1
    Rain rain;
    @SerializedName("sys")
    DayInformation dayInformation;
}
