package vn.edu.usth.usthweather.ui.theme;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import vn.edu.usth.usthweather.R;

public class MediaStoreHelper {
    private static final String TAG = "MediaStoreHelper";

    public static Uri saveAudioToMediaStore(Context context) {
        ContentResolver contentResolver = context.getContentResolver();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Audio.Media.DISPLAY_NAME, "my_music.mp3");
        contentValues.put(MediaStore.Audio.Media.MIME_TYPE, "audio/mpeg");
        contentValues.put(MediaStore.Audio.Media.RELATIVE_PATH, "Music/");

        // Chèn dữ liệu vào MediaStore
        Uri audioUri = contentResolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, contentValues);

        if (audioUri != null) {
            try (InputStream inputStream = context.getResources().openRawResource(R.raw.my_music);
                 OutputStream outputStream = contentResolver.openOutputStream(audioUri)) {

                if (outputStream != null) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                    Log.d(TAG, "Tệp nhạc đã được lưu vào MediaStore!");
                } else {
                    Log.e(TAG, "OutputStream không thể được mở.");
                }
            } catch (IOException e) {
                Log.e(TAG, "Lỗi khi ghi file nhạc vào MediaStore.", e);
            }
        } else {
            Log.e(TAG, "Không thể lưu tệp nhạc vào MediaStore.");
        }
        return audioUri; // Trả về audioUri
    }
}
