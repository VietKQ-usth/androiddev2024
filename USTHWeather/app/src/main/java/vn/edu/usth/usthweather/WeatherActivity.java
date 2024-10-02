package vn.edu.usth.usthweather;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.Manifest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Objects;

public class WeatherActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 2;
    private static final int REQUEST_READ_MEDIA_AUDIO = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        ViewPager2 viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        HomeFragmentPagerAdapter homeFragmentPagerAdapter = new HomeFragmentPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(homeFragmentPagerAdapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_MEDIA_AUDIO},
                        REQUEST_READ_MEDIA_AUDIO);
            } else {
                copyAndPlayMusic();
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_EXTERNAL_STORAGE);
            } else {
                copyAndPlayMusic();
            }
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


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE || requestCode == REQUEST_READ_MEDIA_AUDIO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                copyAndPlayMusic();
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void copyAndPlayMusic() {
        try {
            // Get the InputStream from assets
            InputStream is = getAssets().open("my_music.mp3");

            // Create a File in the app's external files directory
            File musicFile = new File(getExternalFilesDir(null), "my_music.mp3");

            // Copy the file
            copyFile(is, musicFile);

            // Play the music
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(musicFile.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error playing music!", Toast.LENGTH_SHORT).show();
        }
    }

    private void copyFile(InputStream in, File dst) throws IOException {
        try (OutputStream out = Files.newOutputStream(dst.toPath())) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Dừng nhạc khi ứng dụng không còn hoạt động
        if (mediaPlayer != null) {
            mediaPlayer.pause(); // Tạm dừng phát nhạc
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Tiếp tục phát nhạc khi vào lại ứng dụng
        if (mediaPlayer != null ) {
            mediaPlayer.start(); // Tiếp tục phát nhạc
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer resources
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            // Hiển thị Toast khi bắt đầu refresh
            Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show();

            // Mô phỏng yêu cầu mạng bằng cách sử dụng Thread và Handler
            new Thread(() -> {
                try {
                    // Mô phỏng độ trễ của yêu cầu mạng (2 giây)
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Sau khi hoàn tất, sử dụng Handler để cập nhật UI trên luồng chính
                new Handler(Looper.getMainLooper()).post(() -> {
                    // Hiển thị Toast khi refresh hoàn tất
                    Toast.makeText(WeatherActivity.this, "Refresh completed", Toast.LENGTH_SHORT).show();
                });
            }).start();
            return true;
        } else if (id == R.id.action_settings) {
            // Xử lý khi nhấn vào nút Settings
            Intent intent = new Intent(this, PrefActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
