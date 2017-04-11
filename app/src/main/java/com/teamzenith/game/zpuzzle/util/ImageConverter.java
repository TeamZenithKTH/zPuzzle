package com.teamzenith.game.zpuzzle.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by memmi on 2017-04-05.
 */

public class ImageConverter {

    public String convertToString(Bitmap image) {


        ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
        //solved.recycle();
        byte[] byteArray = bYtE.toByteArray();
        String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return imageFile;
    }


    public Drawable convertToBitmap(String imageFile){
        byte[] decodedString = Base64.decode(imageFile, Base64.DEFAULT);
        ByteArrayInputStream is=new ByteArrayInputStream(decodedString);
        Drawable decodedByte = Drawable.createFromStream(is, "bloodsample");
        return decodedByte;
    }

}
