package com.rkya.weather.sync;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class WeatherAsyncWorkService extends Worker {
    public WeatherAsyncWorkService(@NonNull Context appContext, @NonNull WorkerParameters params) {
        super(appContext, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        WeatherSyncTask.syncWeather(context);
        return Result.success();
    }

}
