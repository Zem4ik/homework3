package droid2016.ifmo.ru.homework3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView;
    private BroadcastReceiver actionReceiver;
    private BroadcastReceiver imageSignalReceiver;
    private boolean error = false;
    private File image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.image);
        textView = (TextView) findViewById(R.id.status_text);
        image = new File(getFilesDir(), getString(R.string.imageFile));

        textView.setText(R.string.image_is_downloading);
        updateScreen();

        actionReceiver = new ActionReceiver();
        imageSignalReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("imageSignalReceiver", "started");
                updateScreen();
            }
        };
        registerReceiver(actionReceiver, new IntentFilter(Intent.ACTION_POWER_CONNECTED));
        registerReceiver(imageSignalReceiver, new IntentFilter(getString(R.string.IMAGE_DOWNLOADED)));
    }

    private void updateScreen() {
        if (image.exists()) {
            try {
                imageView.setImageBitmap(BitmapFactory.decodeFile(image.getAbsolutePath()));
                error = false;
            } catch (Exception e) {
                e.printStackTrace();
                error = true;
            }
        }
        if (error) {
            textView.setText(R.string.downloading_error);
            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.INVISIBLE);
        } else {
            textView.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(actionReceiver);
        unregisterReceiver(imageSignalReceiver);
    }
}
