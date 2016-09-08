package com.zurisoft.mark.flashlight;

import android.app.Application;
import android.content.pm.PackageManager;

/**
 * Created by mark on 08/09/16.
 */
public class MainApplication extends Application{
    public boolean hasFlash;
    @Override
    public void onCreate() {
        super.onCreate();
         /*
       * First check if device is supporting flashlight or not
       */
        hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);


    }
}
