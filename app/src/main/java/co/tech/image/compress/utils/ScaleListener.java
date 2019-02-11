package co.tech.image.compress.utils;

import android.view.ScaleGestureDetector;
import android.widget.ImageView;

/**
 * This class helps to Zoom in and zoom out Image view.
 */
public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

    private float mScaleFactor = 1.0f;
    private ImageView mImageView;

    public ScaleListener(ImageView imageView) {
        this.mImageView = imageView;
    }

    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector){
        mScaleFactor *= scaleGestureDetector.getScaleFactor();
        mScaleFactor = Math.max(0.1f,
                Math.min(mScaleFactor, 10.0f));
        mImageView.setScaleX(mScaleFactor);
        mImageView.setScaleY(mScaleFactor);
        return true;
    }
}
