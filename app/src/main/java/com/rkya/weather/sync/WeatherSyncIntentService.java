package com.rkya.weather.sync;

import android.app.IntentService;
import android.content.Intent;

public class WeatherSyncIntentService extends IntentService {

    public WeatherSyncIntentService() {
        super("WeatherSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        WeatherSyncTask.syncWeather(this);
    }
}
