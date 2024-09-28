package vn.edu.usth.usthweather;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;

import vn.edu.usth.usthweather.ui.theme.MediaStoreHelper;

public class WeatherActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager2 viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        HomeFragmentPagerAdapter homeFragmentPagerAdapter = new HomeFragmentPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(homeFragmentPagerAdapter);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE);
        } else {
            saveAndPlayMusic(); // Lưu và phát nhạc
        }

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

    private void saveAndPlayMusic() {
        Uri audioUri = MediaStoreHelper.saveAudioToMediaStore(this);
        playMusic(audioUri); // Phát nhạc ngay sau khi lưu thành công
    }

    private void playMusic(Uri audioUri) {
        if (audioUri == null) {
            Log.e("WeatherActivity", "audioUri is null, cannot play music.");
            Toast.makeText(this, "Audio URI is null.", Toast.LENGTH_SHORT).show();
            return; // Thoát nếu audioUri không hợp lệ
        }

        MediaPlayer mediaPlayer = new MediaPlayer(); // Chuyển mediaPlayer thành biến cục bộ
        try {
            mediaPlayer.setDataSource(this, audioUri);
            mediaPlayer.prepare(); // Chuẩn bị phát
            mediaPlayer.setOnCompletionListener(mp -> Log.d("MediaPlayer", "Audio playback completed."));
            mediaPlayer.start();   // Bắt đầu phát
            Log.d("WeatherActivity", "Playing audio from URI: " + audioUri); // Loại bỏ toString()
        } catch (IOException e) {
            Log.e("WeatherActivity", "Error playing audio: " + e.getMessage());
            Toast.makeText(this, "Error playing audio: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveAndPlayMusic(); // Lưu và phát nhạc
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
