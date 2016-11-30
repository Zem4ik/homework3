package droid2016.ifmo.ru.homework3;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Vlad on 29.11.2016.
 */

public class ImageIntentService extends IntentService {
    private static final String TAG = "ImageIntentService";

    public ImageIntentService() {
        super("ImageIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");
        String url = intent.getStringExtra(getString(R.string.URL_TAG));
        File image = new File(getFilesDir(), getString(R.string.imageFile));
        try {
            DownloadUtils.downloadFile(this, url, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendBroadcast( new Intent(getString(R.string.IMAGE_DOWNLOADED)));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Toast.makeText(this, "ImageIntentService created", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        //Toast.makeText(this, "My Service Stopped", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }
}
