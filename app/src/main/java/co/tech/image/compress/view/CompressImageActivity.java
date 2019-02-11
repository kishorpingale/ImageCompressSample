package co.tech.image.compress.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;

import co.tech.image.compress.presenter.MainActivityPresenter;
import co.tech.image.compress.R;
import co.tech.image.compress.utils.Utils;

public class CompressImageActivity extends BaseActivity implements MainActivityPresenter.View {

    private final String mLOGTAG = CompressImageActivity.class.getSimpleName();
    private String mImagePath = null;
    private ImageView mImageView;
//    private TextView mSizeTextView;
    private MainActivityPresenter mainActivityPresenter;
    private File compressFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compress_image);
        mainActivityPresenter = new MainActivityPresenter(this);
        mImageView = findViewById(R.id.imageView_compress_img);
//        mSizeTextView = findViewById(R.id.textView_compress_size);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Utils.IMAGE_PATH)) {
            mImagePath = intent.getStringExtra(Utils.IMAGE_PATH);
            if (!TextUtils.isEmpty(mImagePath)) {
                mainActivityPresenter.compressImage(mImagePath);
            }
        }
    }

    /**
     * @param compressBitmap
     * Get compress image in compressCaptureImage callback method
     */
    @Override
    public void compressCaptureImage(Bitmap compressBitmap) {

        if(compressBitmap != null) {
            Glide.with(CompressImageActivity.this).load(compressBitmap).apply(
                    new RequestOptions().placeholder(
                            R.drawable.ic_launcher_foreground)).into(mImageView);

//            mSizeTextView.setText(getString(R.string.compress_file_size_text) + "000 ");
        }
    }

    @Override
    public void showProgressBar() {
    }

    @Override
    public void hideProgressBar() {
    }
}
