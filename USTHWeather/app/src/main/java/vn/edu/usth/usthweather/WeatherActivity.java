package vn.edu.usth.usthweather;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;

import vn.edu.usth.usthweather.ui.theme.MediaStoreHelper;

public class WeatherActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);

        ViewPager2 viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        // Khởi tạo HomeFragmentPagerAdapter với FragmentActivity (WeatherActivity)
        HomeFragmentPagerAdapter homeFragmentPagerAdapter = new HomeFragmentPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(homeFragmentPagerAdapter);

        // Kiểm tra quyền truy cập
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE);
        } else {
            MediaStoreHelper.saveAudioToMediaStore(this);
            playMusic(); // Phát nhạc ngay sau khi lưu
        }

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

    private void playMusic() {
        Uri audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                .buildUpon()
                .appendPath("my_music.mp3") // Thay đổi phần này nếu cần
                .build();

        // Khởi tạo MediaPlayer để phát nhạc
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this, audioUri);
            mediaPlayer.prepare(); // Chuẩn bị phát
            mediaPlayer.start();   // Bắt đầu phát
        } catch (IOException e) {
            Toast.makeText(this, "Error playing audio: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Giải phóng tài nguyên
            mediaPlayer = null; // Đặt lại biến để tránh lỗi NullPointerException
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                MediaStoreHelper.saveAudioToMediaStore(this);
                playMusic(); // Phát nhạc ngay sau khi lưu
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
