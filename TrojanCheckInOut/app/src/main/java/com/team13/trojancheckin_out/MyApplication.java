package com.team13.trojancheckin_out;

import android.app.Application;
import android.content.res.Configuration;

import com.cloudinary.android.MediaManager;

import java.util.HashMap;
import java.util.Map;

public class MyApplication extends Application {
    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!
        Map config = new HashMap();
        config.put("cloud_name","mindydie");
        config.put("api_key", "218152914823857");
        config.put("api_secret","_citpdQZKhf9GLu6QB4kwa5Tr1I");
        MediaManager.init(MyApplication.this, config);
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
