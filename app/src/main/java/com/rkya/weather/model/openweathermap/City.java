package com.rkya.weather.model.openweathermap;

import com.google.gson.annotations.SerializedName;

public class City {
    public int id;
    public String name;
    @SerializedName("coord")
    public Coordinates coordinates;
    public String country;
    public long sunrise;
    public long sunset;
    public int timezone;
    @SerializedName("dt")
    public long dateTime;
}
