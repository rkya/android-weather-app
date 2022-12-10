package com.rkya.weather.model.openweathermap;

import com.google.gson.annotations.SerializedName;

import lombok.ToString;

@ToString
class DayInformation {
   @SerializedName("pod")
   String partOfDay;
   String country;
   long sunrise;
   long sunset;
}
