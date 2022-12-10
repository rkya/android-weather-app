package com.rkya.weather.model.openweathermap;

import com.google.gson.annotations.SerializedName;

import lombok.ToString;

@ToString
class Clouds {
    @SerializedName("all")
    int cloudiness;
}
