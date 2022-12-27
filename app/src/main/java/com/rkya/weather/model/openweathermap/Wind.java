package com.rkya.weather.model.openweathermap;

import com.google.gson.annotations.SerializedName;

import lombok.ToString;

@ToString
public class Wind {
    public double speed;
    @SerializedName("deg")
    public int degree;
    public double gust;
}
