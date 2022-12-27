package com.rkya.weather;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.rkya.weather.database.WeatherContract;
import com.rkya.weather.databinding.ActivityMainBinding;
import com.rkya.weather.sync.WeatherSyncUtils;
import com.rkya.weather.utils.loader.LoaderId;
import com.rkya.weather.view.home.HomeAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements
        HomeAdapter.HomeOnClickHandler,
        LoaderManager.LoaderCallbacks<Cursor> {

    private final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private RecyclerView homeRecyclerView;
    private int homeRecyclerPosition = RecyclerView.NO_POSITION;
    private HomeAdapter homeAdapter;
    private ProgressBar loadingIndicator;

    private VideoView backgroundVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        homeAdapter = new HomeAdapter(this, this);
        homeRecyclerView = findViewById(R.id.homeRecyclerView);
        homeRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        homeRecyclerView.setAdapter(homeAdapter);
        homeRecyclerView.setHasFixedSize(true);

//        TODO: Check if this is necessary
        homeRecyclerView.setVisibility(View.VISIBLE);

        loadingIndicator = findViewById(R.id.loadingIndicator);
        showLoading();

        LoaderManager.getInstance(this).initLoader(LoaderId.FORECAST.getValue(), null, this);
        WeatherSyncUtils.initialize(this);
        startBackgroundVideo();
    }

    private void startBackgroundVideo() {
        backgroundVideo = findViewById(R.id.backgroundVideo);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cloudy_day);
        backgroundVideo.setVideoURI(uri);
        backgroundVideo.start();

        backgroundVideo.setOnPreparedListener(mp -> {
            mp.setLooping(true);

//            TODO: Use the proper logic for video scaling
            float yRatio = mp.getVideoHeight() / (float) backgroundVideo.getHeight();
            float xRatio = mp.getVideoWidth() / (float) backgroundVideo.getWidth();
            float scale = Math.max(yRatio, xRatio) + 5;
            backgroundVideo.setScaleX(scale);
            backgroundVideo.setScaleY(scale);

//            String format = "mp.getVideoHeight() = %d, backgroundVideo.getHeight() = %d, yRatio = %f";
//            Log.d(TAG, String.format(format, mp.getVideoHeight(), backgroundVideo.getHeight(), yRatio));

//            format = "mp.getVideoWidth() = %d, backgroundVideo.getWidth() = %d, xRatio = %f";
//            Log.d(TAG, String.format(format, mp.getVideoWidth(), backgroundVideo.getWidth(), xRatio));

        });
    }

    private void showLoading() {
        homeRecyclerView.setVisibility(View.INVISIBLE);
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    private void showWeatherData() {
        loadingIndicator.setVisibility(View.INVISIBLE);
        homeRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        backgroundVideo.resume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        backgroundVideo.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        backgroundVideo.suspend();
    }

    @Override
    protected void onDestroy() {
        backgroundVideo.stopPlayback();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Methods in HomeAdapter.HomeOnClickHandler
     */

    @Override
    public void onClick(long date) {

    }

    /**
     * Methods in LoaderManager.LoaderCallbacks
     */

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, @Nullable Bundle bundle) {
        switch (LoaderId.getViewType(loaderId)) {

            case FORECAST:
                Uri forecastQueryUri = WeatherContract.WeatherEntry.CONTENT_URI;
                String sortOrder = WeatherContract.WeatherEntry.COLUMN_DATE + " ASC";

                return new CursorLoader(this,
                        forecastQueryUri,
                        WeatherContract.WeatherEntry.getMainForecastProjection(),
                        WeatherContract.WeatherEntry.getSqlSelectForTodayOnwards(),
                        null,
                        sortOrder);

            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        homeAdapter.swapCursor(data);
        if (homeRecyclerPosition == RecyclerView.NO_POSITION) {
            homeRecyclerPosition = 0;
        }

        homeRecyclerView.smoothScrollToPosition(homeRecyclerPosition);
        if (data.getCount() > 0) {
            showWeatherData();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        homeAdapter.swapCursor(null);
    }
}