package com.team13.trojancheckin_out.Layouts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView pfp;

    public DownloadImageTask(ImageView pfp) {
        this.pfp = pfp;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap bit = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            bit = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bit;
    }

    protected void onPostExecute(Bitmap bits) {
        pfp.setImageBitmap(bits);
    }
}
