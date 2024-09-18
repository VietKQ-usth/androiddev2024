package vn.edu.usth.usthweather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WeatherAndForecastFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            // Add WeatherFragment to the container
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.layout_weather, new WeatherFragment())
                    .commit();

            // Add ForecastFragment to the container
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.linear_layout_forecast, new ForecastFragment())
                    .commit();
        }
        return inflater.inflate(R.layout.fragment_weather_and_forecast, container, false);
    }
}