package com.rkya.weather.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.rkya.weather.database.WeatherContract;
import com.rkya.weather.utils.OpenWeatherUtils;

public class WeatherSyncTask {
    private static final String TAG = WeatherSyncTask.class.getSimpleName();

    synchronized public static void syncWeather(Context context) {
//        TODO: Get real data

        try {
            ContentValues[] weatherValues = OpenWeatherUtils.getWeatherContentValuesFromJson(context);

            if (weatherValues != null && weatherValues.length != 0) {
                ContentResolver weatherContentResolver = context.getContentResolver();

                weatherContentResolver.delete(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        null,
                        null);

                weatherContentResolver.bulkInsert(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        weatherValues);
            }

        } catch (Exception e) {
            Log.e(TAG, "Unknown error: " + e.getMessage(), e);
        }
    }
}