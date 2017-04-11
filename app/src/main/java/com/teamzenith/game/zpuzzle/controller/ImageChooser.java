package com.teamzenith.game.zpuzzle.controller;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.model.Level;
import com.teamzenith.game.zpuzzle.model.User;

import java.io.File;

/**
 * This class will select the Image according to what the user select to import the image from.
 * Created by Hichem Memmi on 2017-03-30.
 */

public class ImageChooser extends AppCompatActivity {

    enum Method {
        CAMERA, GALERI, RANDOM;
    }

    private ImageView takephoto;
    private static final int CAMERA_REQUEST = 1888;
    private static int GALERI_RESULT = 1;
    private File imgFile1;
    private Level level;
    private float scale;
    private ImageView galeri;
    private ViewPager viewPager;
    private Context mContext;
    private User player;
    private RandomImageAdapter adapterView;

    /**
     * this method called once the user launch this activity
     * @param bundle
     */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.image_chooser);
        scale = getApplicationContext().getResources().getDisplayMetrics().density;
        mContext = this;
        Intent sendToGameActivity = getIntent();
        level = (Level) sendToGameActivity.getSerializableExtra("Level");
        player = (User) sendToGameActivity.getSerializableExtra("player");
        takephoto = (ImageView) findViewById(R.id.camera);
        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri relativePath = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/images.jpeg"));
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, relativePath);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        galeri = (ImageView) findViewById(R.id.galeri);
        galeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galeriIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galeriIntent, GALERI_RESULT);
            }
        });
        viewPager = (ViewPager) findViewById(R.id.randomImages);
        adapterView = new RandomImageAdapter(this.getBaseContext());
        viewPager.setAdapter(adapterView);
        adapterView.setOnPrepareListener(new PrepareForClick() {
            @Override
            public void setOnPrepare(View p) {
                int currentItem = viewPager.getCurrentItem();
                Intent it = new Intent(ImageChooser.this, Game.class);
                it.putExtra("method", Method.RANDOM);
                it.putExtra("idOfDrawable", adapterView.getSliderImagesId()[currentItem]);
                it.putExtra("current", currentItem);
                it.putExtra("Level", level);
                it.putExtra("player", player);
                startActivity(it);

                finish();

            }
        });
        ActivityCompat.requestPermissions(ImageChooser.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
    }

    /**
     * this method will called once the user try to import the image from the gallery or pick an image with
     * camera
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                imgFile1 = new File(Environment.getExternalStorageDirectory() + "/images.jpeg");
                Intent it = new Intent(this, Game.class);
                it.putExtra("method", Method.CAMERA);
                it.putExtra("Image", imgFile1);
                it.putExtra("Level", level);
                it.putExtra("player", player);
                startActivity(it);

                finish();

            } else if (requestCode == GALERI_RESULT) {
                Uri selectedImageURI = data.getData();
                File imageFile = new File(getRealPathFromURI(selectedImageURI));
                Intent it = new Intent(this, Game.class);
                it.putExtra("method", Method.GALERI);
                it.putExtra("Image", imageFile);
                it.putExtra("Level", level);
                it.putExtra("player", player);
                startActivity(it);

                finish();

            }
        }
    }

    /**
     * this method is to get the path of the image if it is imported from gallery
     * @param contentURI
     * @return
     */
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    /**
     * this method will be called once the user press on the back button
     */
    @Override
    public void onBackPressed() {
        finish();
    }}

