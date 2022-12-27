package com.rkya.weather.model.openweathermap;

import com.google.gson.annotations.SerializedName;

import lombok.ToString;

@ToString
public class MainMetrics {
    public double temp;
    @SerializedName("feels_like")
    public double feelsLike;
    @SerializedName("temp_min")
    public double tempMin;
    @SerializedName("temp_max")
    public double tempMax;
    public double pressure;
    @SerializedName("sea_level")
    public double seaLevel;
    @SerializedName("grnd_level")
    public double groundLevel;
    public int humidity;
}
