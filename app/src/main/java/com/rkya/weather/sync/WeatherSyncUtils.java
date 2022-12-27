package com.rkya.weather.sync;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.rkya.weather.database.WeatherContract;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class WeatherSyncUtils {

    private static final int SYNC_INTERVAL_HOURS = 3;
    private static final String SUNSHINE_SYNC_TAG = "weather-sync";
    private static boolean isInitialized;

    static void schedulePeriodicWorkRequestSync(@NonNull final Context context) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest workRequest =
                new PeriodicWorkRequest.Builder(WeatherAsyncWorkService.class, SYNC_INTERVAL_HOURS, TimeUnit.HOURS)
                        .setConstraints(constraints)
                        .build();

        WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(SUNSHINE_SYNC_TAG, ExistingPeriodicWorkPolicy.REPLACE, workRequest);
    }

    synchronized public static void initialize(@NonNull final Context context) {
        if (isInitialized) {
            return;
        }
        isInitialized = true;

        schedulePeriodicWorkRequestSync(context);

//        Not performing the query on main UI thread
        Thread checkForEmptyThread = new Thread(() -> {

            Uri weatherForecastQueryUri = WeatherContract.WeatherEntry.CONTENT_URI;

            String[] projectionColumns = {WeatherContract.WeatherEntry._ID};
            String selectionStatement = WeatherContract.WeatherEntry
                    .getSqlSelectForTodayOnwards();

            try (Cursor cursor = context.getContentResolver().query(
                    weatherForecastQueryUri,
                    projectionColumns,
                    selectionStatement,
                    null,
                    null)) {

                if (null == cursor || cursor.getCount() == 0) {
                    startImmediateSync(context);
                }
            }
        });

        checkForEmptyThread.start();
    }

    public static void startImmediateSync(@NonNull final Context context) {
        Intent intentToSyncImmediately = new Intent(context, WeatherSyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }
}