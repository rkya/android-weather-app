package com.rkya.weather.home;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.rkya.weather.R;
import com.rkya.weather.model.openweathermap.SingleDayWeatherForecast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = HomeAdapter.class.getSimpleName();

    private enum ViewType {
        HOURLY(0), DAILY(1), A(2), B(3), C(4), D(5);

        private int value;
        ViewType (int value) {
            this.value = value;
        }

        public static ViewType getViewType(int value) {
            for (ViewType viewType: ViewType.values()) {
                if (viewType.value == value) {
                    return viewType;
                }
            }

            throw new IllegalArgumentException("Invalid view type, value = " + value);
        }
    }

    /* The context we use to utility methods, app resources and layout inflaters */
    private final Context context;

    /*
     * Below, we've defined an interface to handle clicks on items within this Adapter. In the
     * constructor of our ForecastAdapter, we receive an instance of a class that has implemented
     * said interface. We store that instance in this variable to call the onClick method whenever
     * an item is clicked in the list.
     */
    final private HomeOnClickHandler onClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface HomeOnClickHandler {
        void onClick(long date);
    }

    public HomeAdapter(Context context, HomeOnClickHandler onClickHandler) {
        this.context = context;
        this.onClickHandler = onClickHandler;
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (ViewType.getViewType(viewType)) {
            case HOURLY:
                return getHourlyViewHolder(parent);
            case DAILY:
                return getDailyViewHolder(parent);
            case A:
                return getHourlyViewHolder(parent);
            case B:
                return getHourlyViewHolder(parent);
            case C:
                return getHourlyViewHolder(parent);
            case D:
                return getHourlyViewHolder(parent);

            default:
                throw new IllegalArgumentException("Invalid view type, value = " + viewType);
        }
    }

    @NonNull
    private HourlyViewHolder getHourlyViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.content_hourly_forecast, parent, false);
        view.setFocusable(true);

        return new HourlyViewHolder(view);
    }

    @NonNull
    private DailyViewHolder getDailyViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.content_daily_forecast, parent, false);
        view.setFocusable(true);

        return new DailyViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return ViewType.getViewType(position).value;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (ViewType.getViewType(position)) {
            case HOURLY:
                generateHourlyView((HourlyViewHolder) holder, "New hourly text from Adapter...");
                Resources resources = context.getResources();

                try (InputStream rawResource = resources.openRawResource(R.raw.current_weather_data);
                     Scanner sc = new Scanner(rawResource)) {

                    StringBuilder stringBuilder = new StringBuilder();

                    while (sc.hasNext()) {
                        stringBuilder.append(sc.nextLine());
                    }
                    Log.d(TAG, stringBuilder.toString());

                    SingleDayWeatherForecast singleDayWeatherForecast = new Gson().
                            fromJson(stringBuilder.toString(), SingleDayWeatherForecast.class);

                    Log.d(TAG, "SingleDayWeatherForecast: " + singleDayWeatherForecast);
                } catch (Resources.NotFoundException e) {
                    Log.e(TAG, "Unable to find the config file: " + e.getMessage());
                } catch (IOException e) {
                    Log.e(TAG, "Failed to open config file.");
                }
//                Log.i(TAG, holder.getClass().getName());
//                generateHourlyView((HourlyViewHolder) holder);
                break;

            case DAILY:
                generateDailyView((DailyViewHolder) holder, "New daily text from Adapter...");
//                DailyViewHolder dailyViewHolder = (DailyViewHolder) holder;
//                dailyViewHolder.dailyView.setText("New daily text from Adapter...");
//                generateDailyView((DailyViewHolder) holder);
                break;

            case A:

                generateHourlyView((HourlyViewHolder) holder, "New A from Adapter...");
//                generateDailyView((DailyViewHolder) holder);
                break;
            case B:
                generateHourlyView((HourlyViewHolder) holder, "New B from Adapter...");
//                generateDailyView((DailyViewHolder) holder);
                break;
            case C:
                generateHourlyView((HourlyViewHolder) holder, "New C from Adapter...");
//                generateDailyView((DailyViewHolder) holder);
                break;
            case D:
                generateHourlyView((HourlyViewHolder) holder, "New D from Adapter...");
//                generateDailyView((DailyViewHolder) holder);
                break;

            default:
                throw new IllegalArgumentException("Invalid view type, value = " + position);
        }
    }

    private void generateHourlyView(@NonNull HourlyViewHolder holder, String text) {
        holder.hourlyView.setText(text);
    }

    private void generateDailyView(@NonNull DailyViewHolder holder, String text) {
        holder.dailyView.setText(text);
    }

    @Override
    public int getItemCount() {
        return ViewType.values().length;
    }

}
