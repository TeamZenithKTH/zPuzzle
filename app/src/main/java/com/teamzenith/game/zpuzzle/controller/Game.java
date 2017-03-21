package com.teamzenith.game.zpuzzle.controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.model.Hard;
import com.teamzenith.game.zpuzzle.model.Kids;
import com.teamzenith.game.zpuzzle.model.Level;
import com.teamzenith.game.zpuzzle.model.Medium;
import com.teamzenith.game.zpuzzle.util.ImageSplit;
import com.teamzenith.game.zpuzzle.util.ImagesIDs;
import com.teamzenith.game.zpuzzle.util.MoveImage;
import com.teamzenith.game.zpuzzle.util.ShufflingImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class Game extends AppCompatActivity implements View.OnClickListener {


    private static final int CAMERA_REQUEST = 1888;
    private ArrayList<Integer> images;
    private TextView textView;
    private Button photoButton;
    private TableLayout ll;
    private TableRow tableRow;
    private int row;
    private int column;
    private Level level;
    private Bitmap[] bmp;
    private Bitmap[] tmpbmp;
    private HashMap<Integer, Bitmap> OMap=new HashMap<Integer, Bitmap>();
    private HashMap<Integer, Bitmap> SHMap=new HashMap<Integer, Bitmap>();
    private ImagesIDs imagesIDs = new  ImagesIDs();
    private HashMap<Integer, Integer> imagesIDList;



    ShufflingImage shufflingImage = new ShufflingImage();
    private final MoveImage moveImage = new MoveImage();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        createComponents();
        initComponent();
        actions();
        ActivityCompat.requestPermissions(Game.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);

    }


    private void createComponents() {
        photoButton = (Button) this.findViewById(R.id.takePicture);
        textView = (TextView) findViewById(R.id.niveauText);


    }

    private void initComponent() {
        Intent intent = this.getIntent();
        Level level = (Level) intent.getSerializableExtra("Level");
         if (level instanceof Hard) {
            row = Hard.ROW;
            column = Hard.COLUMN;
        } else if (level instanceof Medium) {
            row = Medium.ROW;
            column = Medium.COLUMN;
        } else {
            row = Kids.ROW;
            column = Kids.COLUMN;

        }

        textView.setText(String.valueOf(level));
    }

    private void actions() {
        photoButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        if(ll!=null){
           ll.removeAllViews();
            ll.refreshDrawableState();
        }

        Uri relativePath = Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/images.jpeg"));
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, relativePath);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
   }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            File imgFile1 = new File(Environment.getExternalStorageDirectory()+"/images.jpeg");
            Bitmap photo;
            if (level instanceof Hard) {
                photo = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), 1000, 1000, true);
            } else if (level instanceof Medium) {
                photo = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), 750, 750, true);
            } else
                photo = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), 500, 500, true);

            ImageSplit imageSplit = new ImageSplit();
            try {
                bmp = imageSplit.get(photo, row, column);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ShufflingImage shufflingImage = new ShufflingImage();
            tmpbmp =shufflingImage.shuffle(bmp);
            SHMap= shufflingImage.getShuffledOrder();
            settingImages(SHMap);
            }

        createImageViews(SHMap);



    }


    private void settingImages(HashMap<Integer, Bitmap> SHMap) {
            this.SHMap =SHMap;
            int j = 0;
            ll = (TableLayout) findViewById(R.id.table);
            tableRow = new TableRow(this);
            for (int i = 0; i < row * column; i++) {
                if (j == ((row * column) / row) - 1) {
                    ImageButton im = new ImageButton(this);
                    im.setId(i);
                   // Bitmap resized = Bitmap.createScaledBitmap(tmpbmp[i], 250, 250, true);
                    im.setImageBitmap(tmpbmp[i]);
                    imagesIDs.setposition(im.getId(),i);
                    TableRow.LayoutParams params = new TableRow.LayoutParams();
                    params.setMargins(1, 1, 1, 1);
                    params.width=250;
                    params.height=250;
                    im.setLayoutParams(params);
                    tableRow.addView(im, params);
                    ll.addView(tableRow);
                    tableRow = new TableRow(this);
                    j = 0;
                } else {
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    tableRow.setLayoutParams(lp);
                    ImageButton im = new ImageButton(this);
                    im.setId(i);
                    imagesIDs.setposition(im.getId(),i);
                    System.out.println("Image ID2 "+im.getId());
                    //Bitmap resized = Bitmap.createScaledBitmap(tmpbmp[i], 250, 250, true);
                    im.setImageBitmap(tmpbmp[i]);
                    TableRow.LayoutParams params = new TableRow.LayoutParams();
                    params.setMargins(1, 1, 1, 1);
                    params.width=250;
                    params.height=250;
                    im.setLayoutParams(params);
                    tableRow.addView(im, params);
                    j++;
                }
            }
        imagesIDList =imagesIDs.getposition();

    }


  private void createImageViews(final HashMap<Integer, Bitmap> SHMap){
        this.SHMap = SHMap;
        if (SHMap.size() == 4) {
            kidsImageView();
        }else if (SHMap.size()==9){
            mediumImageView();
        }else{
            hardImageView();
        }
    }

    private void kidsImageView(){
        final ImageButton imageButton0 = (ImageButton) findViewById(0);
        final ImageButton imageButton1=(ImageButton) findViewById(0+1);
        final ImageButton imageButton2=(ImageButton) findViewById(0+2);
        final ImageButton imageButton3=(ImageButton) findViewById(0+3);

        imageButton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(imageButton0.getId());
                setNewImages( moveImage.move(SHMap,imageButton0.getId()));

            }
        });

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages( moveImage.move(SHMap,imageButton1.getId()));

            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages( moveImage.move(SHMap,imageButton2.getId()));
            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages(  moveImage.move(SHMap,imageButton3.getId()));
            }
        });
    }
    private void mediumImageView(){

        final ImageButton imageButton0 = (ImageButton) findViewById(0);
        final ImageButton imageButton1=(ImageButton) findViewById(0+1);
        final ImageButton imageButton2=(ImageButton) findViewById(0+2);
        final ImageButton imageButton3=(ImageButton) findViewById(0+3);
        final ImageButton imageButton4=(ImageButton) findViewById(0+4);
        final ImageButton imageButton5=(ImageButton) findViewById(0+5);
        final ImageButton imageButton6=(ImageButton) findViewById(0+6);
        final ImageButton imageButton7=(ImageButton) findViewById(0+7);
        final ImageButton imageButton8=(ImageButton) findViewById(0+8);

        imageButton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages( moveImage.move(SHMap,imageButton0.getId()));
            }
        });

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages(  moveImage.move(SHMap,imageButton1.getId()));
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages(  moveImage.move(SHMap,imageButton2.getId()));
            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages(  moveImage.move(SHMap,imageButton3.getId()));
            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages( moveImage.move(SHMap,imageButton4.getId()));
            }
        });
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages(   moveImage.move(SHMap,imageButton5.getId()));
            }
        });
        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages( moveImage.move(SHMap,imageButton6.getId()));
            }
        });
        imageButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages(  moveImage.move(SHMap,imageButton7.getId()));
            }
        });
        imageButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages(  moveImage.move(SHMap,imageButton8.getId()));
            }
        });
    }
    private void hardImageView(){
        final ImageButton imageButton0 = (ImageButton) findViewById(0);
        final ImageButton imageButton1=(ImageButton) findViewById(0+1);
        final ImageButton imageButton2=(ImageButton) findViewById(0+2);
        final ImageButton imageButton3=(ImageButton) findViewById(0+3);
        final ImageButton imageButton4=(ImageButton) findViewById(0+4);
        final ImageButton imageButton5=(ImageButton) findViewById(0+5);
        final ImageButton imageButton6=(ImageButton) findViewById(0+6);
        final ImageButton imageButton7=(ImageButton) findViewById(0+7);
        final ImageButton imageButton8=(ImageButton) findViewById(0+8);
        final ImageButton imageButton9=(ImageButton) findViewById(0+9);
        final ImageButton imageButton10=(ImageButton) findViewById(0+10);
        final ImageButton imageButton11=(ImageButton) findViewById(0+11);
        final ImageButton imageButton12=(ImageButton) findViewById(0+12);
        final ImageButton imageButton13=(ImageButton) findViewById(0+13);
        final ImageButton imageButton14=(ImageButton) findViewById(0+14);
        final ImageButton imageButton15=(ImageButton) findViewById(0+15);

        imageButton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setNewImages(  moveImage.move(SHMap,imageButton0.getId()));
            }
        });

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages( moveImage.move(SHMap,imageButton1.getId()));
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages( moveImage.move(SHMap,imageButton2.getId()));
            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages( moveImage.move(SHMap,imageButton3.getId()));
            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages( moveImage.move(SHMap,imageButton4.getId()));
            }
        });
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages(   moveImage.move(SHMap,imageButton5.getId()));
            }
        });
        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages(  moveImage.move(SHMap,imageButton6.getId()));
            }
        });
        imageButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages( moveImage.move(SHMap,imageButton7.getId()));
            }
        });
        imageButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages( moveImage.move(SHMap,imageButton8.getId()));

            }
        });
        imageButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages(  moveImage.move(SHMap,imageButton9.getId()));

            }
        });
        imageButton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages(  moveImage.move(SHMap,imageButton10.getId()));

            }
        });
        imageButton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages( moveImage.move(SHMap,imageButton11.getId()));

            }
        });
        imageButton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages(  moveImage.move(SHMap,imageButton12.getId()));

            }
        });
        imageButton13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages( moveImage.move(SHMap,imageButton13.getId()));

            }
        });
        imageButton14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages( moveImage.move(SHMap,imageButton14.getId()));

            }
        });
        imageButton15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImages(  moveImage.move(SHMap,imageButton15.getId()));

            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(Game.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /**
     *
     * @param SHMap
     */
    public void setNewImages(HashMap<Integer, Bitmap> SHMap){
        this.SHMap=SHMap;
        for (int i = 0; i<SHMap.size(); i++){
            ImageButton im=(ImageButton)  findViewById(imagesIDList.get(i));
            im.setImageBitmap(SHMap.get(i));
        }
    }

}