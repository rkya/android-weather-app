package com.rkya.weather;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.VideoView;

import com.rkya.weather.databinding.ActivityMainBinding;
import com.rkya.weather.home.HomeAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements HomeAdapter.HomeOnClickHandler {

    private final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private RecyclerView homeRecyclerView;

    private VideoView backgroundVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        homeRecyclerView = findViewById(R.id.homeRecyclerView);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        homeRecyclerView.setAdapter(new HomeAdapter(this, this));
        homeRecyclerView.setHasFixedSize(true);

        homeRecyclerView.setVisibility(View.VISIBLE);

        backgroundVideo = findViewById(R.id.backgroundVideo);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.rain);
        backgroundVideo.setVideoURI(uri);
        backgroundVideo.start();

        backgroundVideo.setOnPreparedListener(mp -> {
            mp.setLooping(true);

//            TODO: Use the proper logic for video scaling
            float yRatio = mp.getVideoHeight() / (float) backgroundVideo.getHeight();
            float xRatio = mp.getVideoWidth() / (float) backgroundVideo.getWidth();
            float scale = Math.max(yRatio, xRatio) +5;
            backgroundVideo.setScaleX(scale);
            backgroundVideo.setScaleY(scale);

            String format = "mp.getVideoHeight() = %d, backgroundVideo.getHeight() = %d, yRatio = %f";
            Log.d(TAG, String.format(format, mp.getVideoHeight(), backgroundVideo.getHeight(), yRatio));

            format = "mp.getVideoWidth() = %d, backgroundVideo.getWidth() = %d, xRatio = %f";
            Log.d(TAG, String.format(format, mp.getVideoWidth(), backgroundVideo.getWidth(), xRatio));

        });
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

    @Override
    public void onClick(long date) {

    }
}