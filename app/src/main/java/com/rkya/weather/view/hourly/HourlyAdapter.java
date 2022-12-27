package com.rkya.weather.view.hourly;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rkya.weather.R;
import com.rkya.weather.database.WeatherContract;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class HourlyAdapter extends RecyclerView.Adapter<WeatherViewHolder> {
    private final String TAG = HourlyAdapter.class.getSimpleName();
    private final Context context;
    private Cursor dataCursor;

    public HourlyAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout constraintLayout = (ConstraintLayout) LayoutInflater.from(context).inflate(R.layout.content_hourly_forecast, parent, false);
        constraintLayout.setFocusable(true);
        return new WeatherViewHolder(constraintLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        if (dataCursor == null || dataCursor.getCount() < context.getResources().getInteger(R.integer.hourly_forecast_size)) {
            return;
        }
        Log.i(TAG, "Elements in cursor: " + (dataCursor == null ? "null" : dataCursor.getCount()));

        try {
            dataCursor.moveToPosition(position);

            Log.d(TAG, "position = " + position);

            int weatherIndex = dataCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_WEATHER_ICON);
            int weatherId = dataCursor.getInt(weatherIndex);
            Log.d(TAG, "WeatherId = " + weatherId);

            int dateIndex = dataCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_DATE);
            long dateInMillis = dataCursor.getLong(dateIndex);
            Log.d(TAG, "dateInMillis = " + dateInMillis);

            int highInCelsiusIndex = dataCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP);
            double highInCelsius = dataCursor.getDouble(highInCelsiusIndex);
            Log.d(TAG, "highInCelsius = " + highInCelsius);

            int lowInCelsiusIndex = dataCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP);
            double lowInCelsius = dataCursor.getDouble(lowInCelsiusIndex);
            Log.d(TAG, "lowInCelsius = " + lowInCelsius);
        } catch (RuntimeException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        boolean isDay = (position / 12) % 2 == 0;

        if (position > 0) {
            holder.time.setText(((position % 12) + 1) + (isDay ? " AM" : " PM"));
        } else {
            holder.time.setText("Now");
        }
        holder.temperature.setText((int) (Math.random() * 10) - 2 + "\u00B0");

        switch ((int) (Math.random() * 10) % 9) {
            case 0:
                if (isDay)
                    holder.weatherImage.setImageResource(R.drawable.ic_01d);
                else
                    holder.weatherImage.setImageResource(R.drawable.ic_01n);
                break;
            case 1:
                if (isDay)
                    holder.weatherImage.setImageResource(R.drawable.ic_02d);
                else
                    holder.weatherImage.setImageResource(R.drawable.ic_02n);
                break;
            case 2:
                if (isDay)
                    holder.weatherImage.setImageResource(R.drawable.ic_03d);
                else
                    holder.weatherImage.setImageResource(R.drawable.ic_03n);
                break;
            case 3:
                if (isDay)
                    holder.weatherImage.setImageResource(R.drawable.ic_04d);
                else
                    holder.weatherImage.setImageResource(R.drawable.ic_04n);
                break;
            case 4:
                if (isDay)
                    holder.weatherImage.setImageResource(R.drawable.ic_09d);
                else
                    holder.weatherImage.setImageResource(R.drawable.ic_09n);
                break;
            case 5:
                if (isDay)
                    holder.weatherImage.setImageResource(R.drawable.ic_10d);
                else
                    holder.weatherImage.setImageResource(R.drawable.ic_10n);
                break;
            case 6:
                if (isDay)
                    holder.weatherImage.setImageResource(R.drawable.ic_11d);
                else
                    holder.weatherImage.setImageResource(R.drawable.ic_11n);
                break;
            case 7:
                if (isDay)
                    holder.weatherImage.setImageResource(R.drawable.ic_13d);
                else
                    holder.weatherImage.setImageResource(R.drawable.ic_13n);
                break;
            case 8:
                if (isDay)
                    holder.weatherImage.setImageResource(R.drawable.ic_50d);
                else
                    holder.weatherImage.setImageResource(R.drawable.ic_50n);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return context.getResources().getInteger(R.integer.hourly_forecast_size);
    }

    public void swapCursor(Cursor newCursor) {
        dataCursor = newCursor;
        notifyDataSetChanged();
    }
}
