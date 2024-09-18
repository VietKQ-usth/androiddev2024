package vn.edu.usth.usthweather;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);

        ViewPager2 viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        // Khởi tạo HomeFragmentPagerAdapter với FragmentActivity (WeatherActivity)
        HomeFragmentPagerAdapter homeFragmentPagerAdapter = new HomeFragmentPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(homeFragmentPagerAdapter);

        // Tạo tiêu đề cho mỗi trang
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Hanoi");
                            break;
                        case 1:
                            tab.setText("Paris");
                            break;
                        case 2:
                            tab.setText("Москва");
                            break;
                    }
                }).attach();
    }
}
