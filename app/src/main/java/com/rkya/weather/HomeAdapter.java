package com.rkya.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
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
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId;

        switch (ViewType.getViewType(viewType)) {
            case HOURLY:
                layoutId = R.layout.content_hourly_forecast;
                break;

            case DAILY:
                layoutId = R.layout.content_daily_forecast;
                break;

            case A:
                layoutId = R.layout.content_daily_forecast;
                break;
            case B:
                layoutId = R.layout.content_daily_forecast;
                break;
            case C:
                layoutId = R.layout.content_daily_forecast;
                break;
            case D:
                layoutId = R.layout.content_daily_forecast;
                break;

            default:
                throw new IllegalArgumentException("Invalid view type, value = " + viewType);
        }

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        view.setFocusable(true);

        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        switch (ViewType.getViewType(position)) {
            case HOURLY:
                holder.hourlyView.setText("New hourly text from Adapter...");
//                generateHourlyView((HourlyViewHolder) holder);
                break;

            case DAILY:
                holder.hourlyView.setText("New daily text from Adapter...");
//                generateDailyView((DailyViewHolder) holder);
                break;

            case A:
                holder.hourlyView.setText("New A from Adapter...");
//                generateDailyView((DailyViewHolder) holder);
                break;
            case B:
                holder.hourlyView.setText("New B from Adapter...");
//                generateDailyView((DailyViewHolder) holder);
                break;
            case C:
                holder.hourlyView.setText("New C from Adapter...");
//                generateDailyView((DailyViewHolder) holder);
                break;
            case D:
                holder.hourlyView.setText("New D from Adapter...");
//                generateDailyView((DailyViewHolder) holder);
                break;

            default:
                throw new IllegalArgumentException("Invalid view type, value = " + position);
        }
    }

    private void generateDailyView(@NonNull DailyViewHolder holder) {
        holder.dailyView.setText("New daily text from Adapter...");
    }

    private void generateHourlyView(@NonNull HourlyViewHolder holder) {
        holder.hourlyView.setText("New hourly text from Adapter...");
    }

    @Override
    public int getItemCount() {
        return ViewType.values().length;
    }

//    TODO: Try to remove this redundant class
    class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView hourlyView;

        HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            hourlyView = itemView.findViewById(R.id.textViewHourlyForecast);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

    class HourlyViewHolder extends HomeViewHolder {
        final TextView hourlyView;

        HourlyViewHolder(@NonNull View itemView) {
            super(itemView);
            hourlyView = itemView.findViewById(R.id.textViewHourlyForecast);
            itemView.setOnClickListener(this);
        }
    }

    class DailyViewHolder extends HomeViewHolder {
        final TextView dailyView;

        DailyViewHolder(@NonNull View itemView) {
            super(itemView);
            dailyView = itemView.findViewById(R.id.textViewDailyForecast);
            itemView.setOnClickListener(this);
        }
    }
}
