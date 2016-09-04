package com.zurisoft.mark.flashlight;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by mark on 01/09/16.
 */
public class HomeActivity extends AppCompatActivity {

    private boolean isFlashOn;
    private boolean hasFlash;

    ImageButton buttonSwitcher;
    private Camera mcamera;
    Camera.Parameters param;
    MediaPlayer mplayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // flash switch button
        buttonSwitcher = (ImageButton) findViewById(R.id.btnSwitch);
      /*
       * First check if device is supporting flashlight or not
       */
        hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!hasFlash) {
            // device doesn't support flash
            // Show alert message and close the application
            AlertDialog alert = new AlertDialog.Builder(HomeActivity.this)
                    .create();
            alert.setTitle("Error");
            alert.setMessage("Sorry, your device doesn't support flash light!");
            alert.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // closing the application
                    finish();
                }
            });
            alert.show();
            return;
        }
        // get the camera
        getCamera();
        // displaying button image
        toggleImageButton();
      /*
       * Switch button click event to toggle flash on/off
       */
        buttonSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFlashOn) {
                    // turn off flash
                    turnOffFlash();
                } else {
                    // turn on flash
                    turnOnFlash();
                }
            }
        });
    }


    /*
         * Get the camera
         */
    private void getCamera() {
        if (mcamera == null) {
            try {
                mcamera = Camera.open();
                param = mcamera.getParameters();
            } catch (RuntimeException e) {
                Log.e("Camera Error.  ", e.getMessage());
            }
        }
    }

    /*
    * Playing sound
    * will play button toggle sound on flash on / off
    * */
    private void playSound(){
        if(isFlashOn){
            mplayer = MediaPlayer.create(HomeActivity.this, R.raw.light_switch_off);
        }else{
            mplayer = MediaPlayer.create(HomeActivity.this, R.raw.light_switch_on);
        }
        mplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });
        mplayer.start();
    }



    private void turnOffFlash() {
        if (isFlashOn) {
            if (mcamera == null || param == null) {
                return;
            }
            // play sound
            playSound();
            param = mcamera.getParameters();
            param.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mcamera.setParameters(param);
            mcamera.stopPreview();
            isFlashOn = false;
            // changing button/switch image
            toggleImageButton();
        }
    }


}