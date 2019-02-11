package co.tech.image.compress.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.IOException;

import co.tech.image.compress.model.ImageStoreCompress;

public class MainActivityPresenter {

    private View view;
    private ImageStoreCompress imgCompress;

    public MainActivityPresenter(View view) {
        this.imgCompress = new ImageStoreCompress();
        this.view = view;
    }

    /**
     * @param context
     * @return File
     * Used for create image file in external storage
     */
    public File createFilePath(Context context) {
        try {
            return imgCompress.createImageFile(context);
        } catch (IOException ex) {
            // Error occurred while creating the File
            return null;
        }
    }

    /**
     * @param currentImgPath
     * set Compress image
     */
    public void compressImage(String currentImgPath){
        view.compressCaptureImage(imgCompress.compressImage(currentImgPath));
    }

    public interface View{
//        void compressCaptureImage(Bitmap bitmap);
        void compressCaptureImage(Bitmap compressBitmap);
        void showProgressBar();
        void hideProgressBar();
    }
}
