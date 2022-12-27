package com.rkya.weather.view.hourly;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rkya.weather.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class WeatherViewHolder extends RecyclerView.ViewHolder {
    final TextView temperature;
    final TextView time;
    final ImageView weatherImage;

    WeatherViewHolder(@NonNull View itemView) {
        super(itemView);

        temperature = itemView.findViewById(R.id.textViewTemperature);
        time = itemView.findViewById(R.id.textViewTime);
        weatherImage = itemView.findViewById(R.id.imageViewWeather);
    }
}
