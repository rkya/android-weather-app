package com.rkya.weather.home;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//    TODO: Try to remove this redundant class
public abstract class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        final TextView hourlyView;

    HomeViewHolder(@NonNull View itemView) {
        super(itemView);
//            hourlyView = itemView.findViewById(R.id.textViewHourlyForecast);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
