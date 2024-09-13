package vn.edu.usth.usthweather;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.edu.usth.usthweather.weather.WeatherAndForecastFragment;
import vn.edu.usth.usthweather.weather.WeatherAndForecastFragment1;
import vn.edu.usth.usthweather.weather.WeatherAndForecastFragment2;

public class HomeFragmentPagerAdapter extends FragmentStateAdapter {

    public HomeFragmentPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public HomeFragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0 : return new WeatherAndForecastFragment();
            case 1 : return new WeatherAndForecastFragment1();
            case 2 : return new WeatherAndForecastFragment2();
        }
        return new Fragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
