package droid2016.ifmo.ru.homework3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Vlad on 30.11.2016.
 */

public class ActionReceiver extends BroadcastReceiver {
    private final String TAG = "ActionReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        Intent urlIntent = new Intent(context, ImageIntentService.class);
        urlIntent.putExtra(context.getString(R.string.URL_TAG), DownloadUtils.getNextURL());
        context.startService(urlIntent);
    }
}
