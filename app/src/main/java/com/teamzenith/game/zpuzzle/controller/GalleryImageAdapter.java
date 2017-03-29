package com.teamzenith.game.zpuzzle.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.model.Level;

/**
 * Created by Krish on 28-03-2017.
 */

public class GalleryImageAdapter extends Activity {


    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static int IMG_RESULT = 1;

    String ImageDecode;
    String strSelectedImage;

    Intent intent;
    Level level;
    private Bitmap currentImage;

    ImageView imageViewLoad;
    ImageButton pImageButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        intent = this.getIntent();
        level = ( Level ) intent.getSerializableExtra("Level");
        strSelectedImage = ( String) intent.getSerializableExtra("PickImage");

        imageViewLoad = (ImageView) findViewById(R.id.imageViewPic);

        pImageButton = (ImageButton) findViewById(R.id.imageButtonPic);
        pImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(GalleryImageAdapter.this, "Play", Toast.LENGTH_LONG).show();

            }
        });

        // Loading the phone Gallery
        if( strSelectedImage.equals("Gallery")) {
            intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(intent, IMG_RESULT);
        }
        // Loading the Camera for a Picture click
        else if( strSelectedImage.equals("Camera")){

             intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        }
        // Load Random selected image
        else if( strSelectedImage.equals("Random")){

                Bitmap bmp = (Bitmap) intent.getParcelableExtra("picture");
                imageViewLoad.setImageBitmap(bmp);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // Gallery related Activity
            //
            if( strSelectedImage.equals("Gallery")) {
                if (requestCode == IMG_RESULT && resultCode == RESULT_OK && null != data) {

                    Uri photoUri = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};

                    if (photoUri != null) {

                        currentImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                        imageViewLoad.setImageBitmap(currentImage);
                    }
                }
            }
            // Camera related Activity
            //
            else if( strSelectedImage.equals("Camera")){

                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imageViewLoad.setImageBitmap(imageBitmap);

                }
            }

            else if( strSelectedImage.equals("Random")){

            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG)
                    .show();
        }

    }
}
