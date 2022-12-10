package com.rkya.weather.model.openweathermap;

import com.google.gson.annotations.SerializedName;

import lombok.ToString;

@ToString
class MainMetrics {
   double temp;
   @SerializedName("feels_like")
   double feelsLike;
   @SerializedName("temp_min")
   double tempMin;
   @SerializedName("temp_max")
   double tempMax;
   double pressure;
   @SerializedName("sea_level")
   double seaLevel;
   @SerializedName("grnd_level")
   double groundLevel;
   double humidity;
}
