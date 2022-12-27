package com.rkya.weather.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.google.gson.Gson;
import com.rkya.weather.R;
import com.rkya.weather.database.WeatherContract;
import com.rkya.weather.database.WeatherPreferences;
import com.rkya.weather.model.openweathermap.FiveDayWeatherForecast;
import com.rkya.weather.model.openweathermap.SingleDayWeatherForecast;
import com.rkya.weather.model.openweathermap.Weather;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Utility functions to handle OpenWeatherMap JSON data.
 */
public final class OpenWeatherUtils {
    private static final String TAG = OpenWeatherUtils.class.getSimpleName();

    public static ContentValues[] getWeatherContentValuesFromJson(Context context) {

        Resources resources = context.getResources();
        FiveDayWeatherForecast fiveDayWeatherForecast;

        try (InputStream rawResource = resources.openRawResource(R.raw.five_day_weather_forecast);
             Scanner sc = new Scanner(rawResource)) {
            StringBuilder stringBuilder = new StringBuilder();

            while (sc.hasNext()) {
                stringBuilder.append(sc.nextLine());
            }

            fiveDayWeatherForecast = new Gson().fromJson(stringBuilder.toString(), FiveDayWeatherForecast.class);

        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Unable to find the config file: " + e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e(TAG, "Failed to open config file.");
            return null;
        }

        WeatherPreferences.setLocationDetails(context,
                fiveDayWeatherForecast.city.coordinates.lat, fiveDayWeatherForecast.city.coordinates.lon);

        ContentValues[] weatherContentValues = new ContentValues[resources.getInteger(R.integer.hourly_forecast_size) + 1];

        long normalizedUtcStartDay = WeatherDateUtils.getNormalizedUtcDateForToday();
        List<SingleDayWeatherForecast> singleDayWeatherForecastList = fiveDayWeatherForecast.weatherForecasts;
        Collections.sort(singleDayWeatherForecastList);

        for (int i = 0; i < weatherContentValues.length; i++) {

            long dateTimeMillis;
            double pressure;
            int humidity;
            double windSpeed;
            double windDirection;

            double high;
            double low;

            int weatherId;

            SingleDayWeatherForecast dayForecast = singleDayWeatherForecastList.get(i);

//            TODO: Adjust this math
            dateTimeMillis = normalizedUtcStartDay + WeatherDateUtils.DAY_IN_MILLIS * i;

            pressure = dayForecast.main.pressure;
            humidity = dayForecast.main.humidity;
            windSpeed = dayForecast.wind.speed;
            windDirection = dayForecast.wind.degree;

            Weather weatherObject = dayForecast.weather.get(0);
            weatherId = weatherObject.id;

            high = dayForecast.main.tempMax;
            low = dayForecast.main.tempMin;

            ContentValues weatherValues = new ContentValues();
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_DATE, dateTimeMillis);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_HUMIDITY, humidity);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_PRESSURE, pressure);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WIND_SPEED, windSpeed);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_DEGREES, windDirection);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP, high);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP, low);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID, weatherId);

            weatherContentValues[i] = weatherValues;
        }

        return weatherContentValues;
    }
}