package com.rkya.weather.model.openweathermap;

import lombok.ToString;

@ToString
class Weather {
    int id;
    String main;
    String description;
    String icon;
}
