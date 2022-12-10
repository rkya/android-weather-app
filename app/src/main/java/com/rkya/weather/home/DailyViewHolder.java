package com.rkya.weather.home;

import android.view.View;
import android.widget.TextView;

import com.rkya.weather.R;

import androidx.annotation.NonNull;

class DailyViewHolder extends HomeViewHolder {
    final TextView dailyView;

    DailyViewHolder(@NonNull View itemView) {
        super(itemView);
        dailyView = itemView.findViewById(R.id.textViewDailyForecast);
//            itemView.setOnClickListener(this);
    }
}
