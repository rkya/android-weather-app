package com.rkya.weather.model.openweathermap;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.ToString;

@ToString
public class SingleDayWeatherForecast implements Comparable<SingleDayWeatherForecast> {
    public List<Weather> weather;
    @SerializedName("dt")
    public long dateTime;
    public MainMetrics main;
    public Clouds clouds;
    public Wind wind;
    public Rain rain;
    @SerializedName("sys")
    public DayInformation dayInformation;
    public int timezone;
    @SerializedName("coord")
    public Coordinates coordinates;
    int visibility; // Max value is 10_000
    @SerializedName("pop")
    double probabilityOfPrecipitation; // Values range between 0 and 1

    @Override
    public int compareTo(SingleDayWeatherForecast o) {
        return Long.compare(dateTime, o.dateTime);
    }
}
