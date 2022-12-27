package com.rkya.weather.view.home;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rkya.weather.R;
import com.rkya.weather.view.hourly.HourlyAdapter;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = HomeAdapter.class.getSimpleName();
    private final Context context;
    private final HomeOnClickHandler onClickHandler;
    private Cursor dataCursor;
    private HourlyAdapter hourlyAdapter;

    public HomeAdapter(Context context, HomeOnClickHandler onClickHandler) {
        this.context = context;
        this.onClickHandler = onClickHandler;
        hourlyAdapter = new HourlyAdapter(context);
    }

    public void swapCursor(Cursor newCursor) {
        dataCursor = newCursor;
        hourlyAdapter.swapCursor(newCursor);
        notifyDataSetChanged();
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (HomeViewType.getViewType(viewType)) {
            case HOURLY:
                return getHourlyViewHolder(parent);
            case DAILY:
                return getDailyViewHolder(parent);
            default:
                throw new IllegalArgumentException("Invalid view type, value = " + viewType);
        }
    }

    @NonNull
    private HourlyViewHolder getHourlyViewHolder(@NonNull ViewGroup parent) {
        CardView view = (CardView) LayoutInflater.from(context).inflate(R.layout.content_hourly_forecast_list, parent, false);
        view.setFocusable(true);

        RecyclerView hourlyRecyclerView = view.findViewById(R.id.hourlyRecyclerView);
        hourlyRecyclerView.setLayoutManager(
                new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        hourlyRecyclerView.setAdapter(hourlyAdapter);
        hourlyRecyclerView.setHasFixedSize(true);

        return new HourlyViewHolder(view);
    }

    @NonNull
    private DailyViewHolder getDailyViewHolder(@NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        CardView dailyForecastView = (CardView) inflater.inflate(R.layout.content_daily_forecast, parent, false);
        dailyForecastView.setFocusable(true);

        LinearLayout linearLayout = (LinearLayout) dailyForecastView.getChildAt(0);

        for (int i = 0; i < 8; i++) {
            ConstraintLayout constraintLayout = (ConstraintLayout) inflater.inflate(R.layout.daily_details, dailyForecastView, false);
            constraintLayout.setId(i);
            linearLayout.addView(constraintLayout, i);
        }

        return new DailyViewHolder(dailyForecastView);
    }

    @Override
    public int getItemViewType(int position) {
        return HomeViewType.getViewType(position).value;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (dataCursor == null || dataCursor.getCount() < context.getResources().getInteger(R.integer.hourly_forecast_size)) {
            return;
        }

        switch (HomeViewType.getViewType(position)) {
            case HOURLY:
                generateHourlyView((HourlyViewHolder) holder, "New hourly text from Adapter...");
                break;
            case DAILY:
                generateDailyView((DailyViewHolder) holder, "New daily text from Adapter...");
                break;
            default:
                throw new IllegalArgumentException("Invalid view type, value = " + position);
        }
    }

    private void generateHourlyView(@NonNull HourlyViewHolder holder, String text) {
//        holder.hourlyView.setText(text);
    }

    private void generateDailyView(@NonNull DailyViewHolder holder, String text) {
//        holder.dailyView.setText(text);
    }

    @Override
    public int getItemCount() {
        return HomeViewType.values().length;
    }

    private enum HomeViewType {
        HOURLY(0), DAILY(1);

        private final int value;

        HomeViewType(int value) {
            this.value = value;
        }

        public static HomeViewType getViewType(int value) {
            for (HomeViewType homeViewType : HomeViewType.values()) {
                if (homeViewType.value == value) {
                    return homeViewType;
                }
            }

            throw new IllegalArgumentException("Invalid view type, value = " + value);
        }
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface HomeOnClickHandler {
        void onClick(long date);
    }

}
