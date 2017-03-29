package com.teamzenith.game.zpuzzle.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by memmi on 2017-03-13.
 */

public class ImageSplit {

    /**
     * Divide an image to mutible small pieces.
     *
     * @param photo The photo that will be divided.
     * @param row The number of how many rows will be the image divided to.
     * @param column The number of the columns that the image will be divided to.
     * @return The return value will be an Array of Bitmaps.
     * @throws FileNotFoundException An exception will be thrown if an image was not provided to be divided.
     */
    private Bitmap[] bmp;
    private Bitmap[] OriginalImagePicese;

    public Bitmap[] get(Bitmap photo, int row, int column) throws FileNotFoundException {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        bmp = new Bitmap[row * column];
        OriginalImagePicese=new Bitmap[row * column];
        int count = 0;
        int width = photo.getWidth();
        int height = photo.getHeight();
        FileOutputStream out = null;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                bmp[count] = Bitmap.createBitmap(photo, (width * j) / row, (i * height) / row, width / row, height / row);
                OriginalImagePicese[count]=bmp[count];
                count++;
            }
        }

            bmp[(row * column) - 1] = null;
            return bmp;

    }

    public Bitmap[] getOriginalDividedImage() {
        return OriginalImagePicese;
    }
}
