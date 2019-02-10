package co.tech.image.compress.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import co.tech.image.compress.R;
import co.tech.image.compress.utils.*;

public class SplashActivity extends BaseActivity {

    private final int mDelayMillis = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (checkPermission(this)) {
            //Required permission allowed.
            showMainScreen();
        } else {
            //Request to user allow required permission.
            showMessage();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Show main screen to the user after some delayed.
     */
    private void showMainScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, mDelayMillis);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case Utils.PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean cameraGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeExtStorageGranted = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean readExtStorageGranted = grantResults[2] ==
                            PackageManager.PERMISSION_GRANTED;
                    //Required permission not allowed.
                    if (!cameraGranted || !writeExtStorageGranted || !readExtStorageGranted) {
                        //Check whether device OS is Marshmallow(API 23)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermission(SplashActivity.this);
                            return;
                        }
                    } else {
                        //Required permission allowed.
                        showMainScreen();
                    }
                }
                break;
        }
    }


    private void showMessage() {
        showAlertDialog(this, getString(
                R.string.request_permission_message),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermission(SplashActivity.this);
                        }
                    }
                });
    }
}
