package droid2016.ifmo.ru.homework3;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * Created by Vlad on 30.11.2016.
 */

final class DownloadUtils {

    private static final String TAG = "DownloadUtils";
    private static final String[] images = {"https://upload.wikimedia.org/wikipedia/commons/thumb/7/79/Face-smile.svg/2000px-Face-smile.svg.png",
            "https://upload.wikimedia.org/wikipedia/commons/8/80/Kindness_Barnstar_Hires.png",
            "https://upload.wikimedia.org/wikipedia/commons/e/ec/Happy_smiley_face.png",
            "https://upload.wikimedia.org/wikipedia/commons/d/d7/Emoticon_Face_Frown_GE.png",
            "https://upload.wikimedia.org/wikipedia/commons/4/49/Emoticon_Face_Smiley_GE.png",
            "https://upload.wikimedia.org/wikipedia/commons/d/d7/Emoticon_Face_Frown_GE.png"};
    private static int lastNumber;
    private static final Random random = new Random();

    static String getNextURL() {
        int next = random.nextInt(images.length - 1);
        while (next == lastNumber) {
            next = random.nextInt(images.length - 1);
        }
        lastNumber = next;
        return images[next];
    }

    static void downloadFile(Context context, String downloadUrl, File destFile) throws IOException {
        Log.d(TAG, "Start downloading url: " + downloadUrl);
        HttpURLConnection conn = (HttpURLConnection) new URL(downloadUrl).openConnection();

        InputStream in = null;
        OutputStream out = null;

        try {
            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                Log.e(TAG, "Unexpected HTTP response: " + responseCode + ", " + conn.getResponseMessage());
            }

            int contentLength = conn.getContentLength();
            Log.d(TAG, "Content Length: " + contentLength);

            byte[] buffer = new byte[1024 * 8];

            int receivedBytes;
            int receivedLength = 0;

            in = conn.getInputStream();
            out = new FileOutputStream(destFile);

            while ((receivedBytes = in.read(buffer)) >= 0) {
                out.write(buffer, 0, receivedBytes);
                receivedLength += receivedBytes;
            }

            if (receivedLength != contentLength) {
                Log.d(TAG, "Received " + receivedLength + " bytes, but expected " + contentLength);
            } else {
                Log.d(TAG, "Received " + receivedLength + " bytes");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e(TAG, "Failed to close HTTP input stream: " + e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    Log.e(TAG, "Failed to close file: " + e);
                }
            }
            conn.disconnect();
        }
    }
}
