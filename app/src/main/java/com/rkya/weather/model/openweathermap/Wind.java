package com.rkya.weather.model.openweathermap;

import com.google.gson.annotations.SerializedName;

import lombok.ToString;

@ToString
class Wind {
    double speed;
    @SerializedName("deg")
    int degree;
    double gust;
}
