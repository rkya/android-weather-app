package com.rkya.weather.home;

import android.view.View;
import android.widget.TextView;

import com.rkya.weather.R;

import androidx.annotation.NonNull;

class HourlyViewHolder extends HomeViewHolder {
    final TextView hourlyView;

    HourlyViewHolder(@NonNull View itemView) {
        super(itemView);
        hourlyView = itemView.findViewById(R.id.textViewHourlyForecast);
//            itemView.setOnClickListener(this);
    }
}
