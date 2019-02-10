package co.tech.image.compress.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import co.tech.image.compress.R;
import co.tech.image.compress.utils.Utils;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
     * To check whether the user allowed the required permissions or not
     */
    protected boolean checkPermission(Activity currentActivity) {

        //Camera check self permission
        int cameraPermission = ContextCompat.checkSelfPermission(currentActivity,
                Manifest.permission.CAMERA);
        //Write external storage check self permission
        int writeStoragePermission = ContextCompat.checkSelfPermission(currentActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //Read external storage check self permission
        int readStoragePermission = ContextCompat.checkSelfPermission(currentActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        // Check Camera & Write, Read external storage permissions are granted or not
        if (cameraPermission == PackageManager.PERMISSION_GRANTED && writeStoragePermission ==
                PackageManager.PERMISSION_GRANTED && readStoragePermission ==
                PackageManager.PERMISSION_GRANTED) {
            // Permissions are granted
            return true;
        } else {
            // Permissions are not granted
            return false;
        }
    }

    /**
     * Request run time permission
     */
    protected void requestPermission(Activity currentActivity) {
        ActivityCompat.requestPermissions(currentActivity,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.READ_EXTERNAL_STORAGE}, Utils.PERMISSION_REQUEST_CODE);
    }

    /**
     * @param activity
     * @param message
     * @param positiveClickListener Show alert dialog to the user
     */
    protected void showAlertDialog(Activity activity, String message,
                                   DialogInterface.OnClickListener positiveClickListener) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton("OK", positiveClickListener)
//                .setNegativeButton("Cancel", negativeClickListener)
                .create()
                .show();
    }
}
