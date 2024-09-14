package vn.edu.usth.usthweather;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class WeatherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("WeatherActivity", "onCreate called");
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);

        ViewPager2 viewPager = findViewById(R.id.view_pager);

        // Set the FragmentStateAdapter directly
        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public WeatherAndForecastFragment createFragment(int position) {
                // Create a new WeatherAndForecastFragment instance for each page
                return new WeatherAndForecastFragment();
            }

            @Override
            public int getItemCount() {
                return 3; // Number of pages
            }
        });
    }
    protected void onResume() {
        super.onResume();
        Log.i("WeatherActivity", "onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("WeatherActivity", "onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("WeatherActivity", "onStop called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("WeatherActivity", "onRestart called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("WeatherActivity", "onDestroy called");
    }

}