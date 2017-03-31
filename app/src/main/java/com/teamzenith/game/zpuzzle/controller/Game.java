package com.teamzenith.game.zpuzzle.controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.model.Hard;
import com.teamzenith.game.zpuzzle.model.Easy;
import com.teamzenith.game.zpuzzle.model.Level;
import com.teamzenith.game.zpuzzle.model.Medium;
import com.teamzenith.game.zpuzzle.util.GetCurrentStatus;
import com.teamzenith.game.zpuzzle.util.ImageSplit;
import com.teamzenith.game.zpuzzle.util.ImagesIDs;
import com.teamzenith.game.zpuzzle.util.MoveImage;
import com.teamzenith.game.zpuzzle.util.ShufflingImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Hichem Memmi on 2017-03-09.
 */

/**
 * This is the the activity of the GamePlay
 */
public class Game extends AppCompatActivity implements View.OnClickListener {

    private static final int CAMERA_REQUEST = 1888;
    private final MoveImage moveImage = new MoveImage();
    private boolean isFinish = false;
    private ArrayList<Integer> images;
    private TextView textView;
    private ImageView photoButton;
    private TableLayout ll;
    private TableRow tableRow;
    private int row;
    private int column;
    private Level level;
    private Bitmap[] bmp;
    private Bitmap[] tmpbmp;
    private HashMap<Integer, Bitmap> SHMap = new HashMap<Integer, Bitmap>();
    private ImagesIDs imagesIDs = new ImagesIDs();
    private HashMap<Integer, Integer> imagesIDList;
    private ImageSplit imageSplit = new ImageSplit();
    private HashMap<Integer, Bitmap> newMoveedImagesList;
    private ImageButton[] imageButtons;
    private ImageButton im;
    private int countMovement = 0;
    private float scale;
    private TextView currentMovement;
    private Timer T = new Timer();
    private TextView timerCounter;
    private Bitmap imageToSend;
    private int count = 0;
    private File imgFile1;
    File imageFile;

    public Game() {
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_game);
        scale = getApplicationContext().getResources().getDisplayMetrics().density;
        createComponents();
        initComponent();



        if(imageFile!=null){
            prepareAnImage();
        }
             actions();

        ActivityCompat.requestPermissions(Game.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
    }

    private void createComponents() {
        photoButton = (ImageView) this.findViewById(R.id.takePicture);
        currentMovement = (TextView) findViewById(R.id.currentMovement);
        timerCounter = (TextView) findViewById(R.id.timerCounter);
    }

    private void initComponent() {
        Intent intent = this.getIntent();
        imageFile = (File)intent.getSerializableExtra("Image");
        level = (Level) intent.getSerializableExtra("Level");
        if (level instanceof Hard) {
            row = Hard.ROW;
            column = Hard.COLUMN;
        } else if (level instanceof Medium) {
            row = Medium.ROW;
            column = Medium.COLUMN;
        } else {
            row = Easy.ROW;
            column = Easy.COLUMN;
        }
    }

    private void actions() {
        photoButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (ll != null) {
            count = 0;
            countMovement = 0;
            ll.removeAllViews();
            ll.refreshDrawableState();
        }
        Uri relativePath = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/images.jpeg"));
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, relativePath);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            prepareAnImage();
        }

    }

public Bitmap createBitmap(File imgFile1){
    if (level instanceof Hard) {
        return Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), (int) (350 * scale), (int) (350 * scale), true);
    } else if (level instanceof Medium) {
        return Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), (int) (344 * scale), (int) (344 * scale), true);
    } else {
        return Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), (int) (300 * scale), (int) (300 * scale), true);
    }
}

    public void prepareAnImage(){
        Bitmap photo;
        if(imageFile!=null){
            photo=createBitmap(imageFile);
        }
        else
        { imgFile1 = new File(Environment.getExternalStorageDirectory() + "/images.jpeg");
            photo=createBitmap(imgFile1);}

        try {
            bmp = imageSplit.get(photo, row, column);
            T.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String next = "<font color='#EE0000'>" + String.valueOf(count) + "</font>";
                            timerCounter.setText(Html.fromHtml("Timer: " + next));
                            count++;
                        }
                    });
                }
            }, 1000, 1000);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ShufflingImage shufflingImage = new ShufflingImage();
        tmpbmp = shufflingImage.shuffle(bmp);
        SHMap = shufflingImage.getShuffledOrder();
        settingImages(SHMap);


        createImageViews(SHMap);
    }
    private void settingImages(HashMap<Integer, Bitmap> SHMap) {
        this.SHMap = SHMap;
        imageButtons = new ImageButton[row * column];
        int j = 0;
        ll = (TableLayout) findViewById(R.id.table);
        tableRow = new TableRow(this);
        for (int i = 0; i < row * column; i++) {
            if (j == ((row * column) / row) - 1) {
                im = new ImageButton(this);
                im.setId(i);
                im.setImageBitmap(tmpbmp[i]);
                imagesIDs.setposition(im.getId(), i);
                TableRow.LayoutParams params = new TableRow.LayoutParams();

                if (level instanceof Easy) {
                    params.width = (int) (100 * scale);
                    params.height = (int) (100 * scale);
                } else if (level instanceof Medium) {
                    params.width = (int) (86 * scale);
                    params.height = (int) (86 * scale);
                } else {
                    params.width = (int) (70 * scale);
                    params.height = (int) (70 * scale);
                }
                im.setLayoutParams(params);
                tableRow.addView(im, params);
                ll.addView(tableRow);
                tableRow = new TableRow(this);
                imageButtons[i] = im;
                j = 0;
            } else {
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                tableRow.setLayoutParams(lp);
                im = new ImageButton(this);
                im.setId(i);
                imagesIDs.setposition(im.getId(), i);
                System.out.println("Image ID2 " + im.getId());
                im.setImageBitmap(tmpbmp[i]);
                TableRow.LayoutParams params = new TableRow.LayoutParams();
                params.setMargins(1, 1, 1, 1);
                if (level instanceof Easy) {
                    params.width = (int) (100 * scale);
                    params.height = (int) (100 * scale);
                } else if (level instanceof Medium) {
                    params.width = (int) (86 * scale);
                    params.height = (int) (86 * scale);
                } else {
                    params.width = (int) (70 * scale);
                    params.height = (int) (70 * scale);
                }
                im.setLayoutParams(params);
                tableRow.addView(im, params);
                imageButtons[i] = im;
                j++;
            }
        }
        imagesIDList = imagesIDs.getposition();
    }

    private void createImageViews(final HashMap<Integer, Bitmap> SHMap) {
        this.SHMap = SHMap;
        final GetCurrentStatus getCurrentStatus = new GetCurrentStatus();
        final Toast toast = Toast.makeText(this, moveImage.getMsg(), Toast.LENGTH_LONG);
        for (int i = 0; i < imageButtons.length; i++) {
            imageButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageButton b = (ImageButton) v;
                    newMoveedImagesList = moveImage.step(SHMap, b.getId(), row, column);
                    setNewImages(newMoveedImagesList);
                    //toast.show();
                    // isFinish = getCurrentStatus.checkCurrentImage(bmp, newMoveedImagesList);
                    isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                    if (isFinish) {
                        toast.show();
                        SetOriginalImagesToMatrix();
                        isFinish = false;
                    }
                }
            });
        }
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
     * Set The new images postion to the imageButtons for each move that the user done.
     *
     * @param SHMap
     */
    public void setNewImages(HashMap<Integer, Bitmap> SHMap) {
        this.SHMap = SHMap;
        countMovement++;
        String next = "<font color='#EE0000'>" + String.valueOf(countMovement) + "</font>";
        currentMovement.setText("your current move is ");
        int lastIndexSpace = currentMovement.getText().toString().lastIndexOf(" ");
        String currentText = currentMovement.getText().toString();
        String newText = currentText.substring(0, lastIndexSpace) + " " + next;
        currentMovement.setText(Html.fromHtml(newText));
        for (int i = 0; i < SHMap.size(); i++) {
            ImageButton im = (ImageButton) findViewById(i);
            im.setImageBitmap(SHMap.get(i));
        }
    }
    /**
     * Set the Original image's pieces after the user solve the puzzle.
     */
    private void SetOriginalImagesToMatrix() {
        Bitmap[] tmpbitMap;
        tmpbitMap = imageSplit.getOriginalDividedImage();
        for (int i = 0; i < tmpbitMap.length; i++) {
            ImageButton im = (ImageButton) findViewById(i);
            im.setImageBitmap(tmpbitMap[i]);
            Intent it = new Intent(getBaseContext(), AfterTheGameActivity.class);
            it.putExtra("Level", level);
            it.putExtra("CountMovement", String.valueOf(countMovement));
            it.putExtra("TimerCounter", String.valueOf(count));
            it.putExtra("Image", imgFile1);
            T.cancel();
            startActivity(it);
        }
    }
}