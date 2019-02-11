package co.tech.image.compress.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;

import co.tech.image.compress.BuildConfig;
import co.tech.image.compress.R;
import co.tech.image.compress.presenter.MainActivityPresenter;
import co.tech.image.compress.utils.ScaleListener;
import co.tech.image.compress.utils.Utils;

public class MainActivity extends BaseActivity implements MainActivityPresenter.View {

    private ImageView mImageView;
    private TextView mActualSize;
    private Button mCaptureImageButton;
    private Button mCompressImageButton;
    private MainActivityPresenter mainActivityPresenter;
    private ScaleGestureDetector mScaleGestureDetector;
    private File mPhotoFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityPresenter = new MainActivityPresenter(this);
        mImageView = findViewById(R.id.imageView_main);
        mActualSize = findViewById(R.id.textView_actual_img_size);
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener(mImageView));

        mCaptureImageButton = findViewById(R.id.button_image_capture);
        mCaptureImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        mCompressImageButton = findViewById(R.id.button_image_compress);
        mCompressImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                compressCaptureImage();
                showCompressImageScreen();
            }
        });
    }

    /**
     * @param event
     * @return Motion event set to image view for zoom in and zoom out
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mScaleGestureDetector != null) {
            mScaleGestureDetector.onTouchEvent(event);
        }
        return true;
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
     * Open device Camera for capture a image
     */
    private void takePicture() {
        File photoFile = null;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = mainActivityPresenter.createFilePath(this);

            // Continue only if the File was successfully created
            if (photoFile != null) {

                Uri photoURI = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                mPhotoFile = photoFile;

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, Utils.REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Utils.REQUEST_IMAGE_CAPTURE) {
                if (mPhotoFile != null) {
                    if (checkFileSize()) {
                        Glide.with(MainActivity.this).load(mPhotoFile).apply(
                                new RequestOptions().placeholder(
                                        R.drawable.ic_launcher_foreground)).into(mImageView);
                    } else {
                        showAlertDialog(this, getString(
                                R.string.image_size_limit_exceed),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        takePicture();
                                    }
                                });

                    }
                }
            }
        }
    }

    /**
     * Help to calculate image size
     * if file size is more than 6 mb show alert to the user.
     */
    private boolean checkFileSize() {
        try {

            // Get length of file in bytes
            long fileSizeInBytes = mPhotoFile.length();
            // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
            long fileSizeInKB = fileSizeInBytes / 1024;
            // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
            long fileSizeInMB = fileSizeInKB / 1024;
            if (fileSizeInMB < 8) {
                mActualSize.setText(getString(R.string.compress_file_size_text) + " " + fileSizeInMB);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     */
    private void showCompressImageScreen() {
        if(mPhotoFile != null && mPhotoFile.exists()) {
            Intent i = new Intent(MainActivity.this, CompressImageActivity.class);
            i.putExtra(Utils.IMAGE_PATH, mPhotoFile.getAbsolutePath());
//            i.putExtra(Utils.IMAGE_NAME, mPhotoFile.getName());
            startActivity(i);
        }
    }

    @Override
    public void compressCaptureImage(Bitmap imgPath) {
    }

    @Override
    public void showProgressBar() {
    }

    @Override
    public void hideProgressBar() {
    }
}
