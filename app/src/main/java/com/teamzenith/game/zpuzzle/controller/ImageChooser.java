package com.teamzenith.game.zpuzzle.controller;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.model.Hard;
import com.teamzenith.game.zpuzzle.model.Level;
import com.teamzenith.game.zpuzzle.model.Medium;

import java.io.File;

/**
 * Created by memmi on 2017-03-30.
 */

public class ImageChooser extends AppCompatActivity {
    private Button takephoto;
    private static final int CAMERA_REQUEST = 1888;
    private static int GALERI_RESULT = 1;
    private File imgFile1;
    private Level level;
    private float scale;
    Button galeri;


    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.image_chooser);
        scale = getApplicationContext().getResources().getDisplayMetrics().density;





        Intent it=getIntent();
        level=(Level)it.getSerializableExtra("Level");

        takephoto=(Button)findViewById(R.id.takephoto);
        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri relativePath = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/images.jpeg"));
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, relativePath);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });


        galeri=(Button)findViewById(R.id.galeri);
        galeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galeriIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(galeriIntent, GALERI_RESULT);
            }
        });


        ActivityCompat.requestPermissions(ImageChooser.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode==CAMERA_REQUEST){
                imgFile1 = new File(Environment.getExternalStorageDirectory() + "/images.jpeg");
                Intent it=new Intent(this,Game.class);
                it.putExtra("Image", imgFile1);
                it.putExtra("Level",level);
                startActivity(it);
            }
           else if(requestCode==GALERI_RESULT){
                Uri selectedImageURI = data.getData();
                File imageFile = new File(getRealPathFromURI(selectedImageURI));
                Intent it=new Intent(this,Game.class);
                it.putExtra("Image", imageFile);
                it.putExtra("Level",level);
                startActivity(it);
            }


        }}


    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }}
