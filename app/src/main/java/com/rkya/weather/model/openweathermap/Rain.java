package com.rkya.weather.model.openweathermap;

import com.google.gson.annotations.SerializedName;

import lombok.ToString;

@ToString
class Rain {
    @SerializedName("1h")
    double oneHourVolume;
    @SerializedName("3h")
    double threeHourVolume;
}
