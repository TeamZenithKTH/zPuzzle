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
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

/**
 * Created by Hichem Memmi on 2017-03-09.
 */


/**
 * This is the the activity of the GamePlay
 */
public class Game extends AppCompatActivity implements View.OnClickListener {


    private static final int CAMERA_REQUEST = 1888;
    private final MoveImage moveImage = new MoveImage();
    boolean isFinish = false;
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
    private HashMap<Integer, Bitmap> SHMap = new HashMap<Integer, Bitmap>();
    private ImagesIDs imagesIDs = new ImagesIDs();
    private HashMap<Integer, Integer> imagesIDList;
    private ImageSplit imageSplit = new ImageSplit();
    private HashMap<Integer, Bitmap> newMoveedImagesList;
    ImageButton[] imageButtons;
    private ImageButton im;
    ImageButton ims;
    private static final String TAG = "Game";
    final GetCurrentStatus getCurrentStatus = new GetCurrentStatus();
    float scale;
    Bundle savedInstanceState;

    public Game() {
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_game);

        scale = getApplicationContext().getResources().getDisplayMetrics().density;
        createComponents();
        initComponent();
        actions();
        ActivityCompat.requestPermissions(Game.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);


        savedInstanceState=bundle;

    }




    private void createComponents() {
        photoButton = (Button) this.findViewById(R.id.takePicture);
        //textView = (TextView) findViewById(R.id.niveauText);
    }

    private void initComponent() {
        Intent intent = this.getIntent();
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

        // textView.setText(String.valueOf(level));
    }

    private void actions() {
        photoButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        if (ll != null) {
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
            File imgFile1 = new File(Environment.getExternalStorageDirectory() + "/images.jpeg");
            Bitmap photo;
            if (level instanceof Hard) {
                photo = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), (int)(350 * scale), (int)(350 * scale), true);
            } else if (level instanceof Medium) {
                photo = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), (int)(344 * scale), (int)(344 * scale), true);
            } else {
                photo = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), (int)(300 * scale), (int)(300 * scale), true);
            }
            try {
                bmp = imageSplit.get(photo, row, column);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            ShufflingImage shufflingImage = new ShufflingImage();
            tmpbmp = shufflingImage.shuffle(bmp);
            SHMap = shufflingImage.getShuffledOrder();
            settingImages(SHMap);
        }

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

                if(level instanceof Easy){
                    params.width = (int)(100 * scale);
                    params.height = (int)(100 * scale);
                }
                else if(level instanceof Medium){
                    params.width = (int)(86 * scale);
                    params.height = (int)(86 * scale);
                }
                else{
                    params.width = (int)(70 * scale);
                    params.height = (int)(70 * scale);
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
                if(level instanceof Easy){
                    params.width = (int)(100 * scale);
                    params.height = (int)(100 * scale);
                }
                else if(level instanceof Medium){
                    params.width = (int)(86 * scale);
                    params.height = (int)(86 * scale);
                }
                else{
                    params.width = (int)(70 * scale);
                    params.height = (int)(70 * scale);
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
        if (SHMap.size() == 0) {
            Toast.makeText(this, "Could not create a matrix", Toast.LENGTH_SHORT).show();
        } else if (SHMap.size() == 9) {
            kidsImageView();
        } else if (SHMap.size() == 16) {
            mediumImageView();
        } else {
            hardImageView();
        }
    }

    private void kidsImageView() {
        final ImageButton imageButton0 = (ImageButton) findViewById(0);
        final ImageButton imageButton1 = (ImageButton) findViewById(0 + 1);
        final ImageButton imageButton2 = (ImageButton) findViewById(0 + 2);
        final ImageButton imageButton3 = (ImageButton) findViewById(0 + 3);
        final ImageButton imageButton4 = (ImageButton) findViewById(0 + 4);
        final ImageButton imageButton5 = (ImageButton) findViewById(0 + 5);
        final ImageButton imageButton6 = (ImageButton) findViewById(0 + 6);
        final ImageButton imageButton7 = (ImageButton) findViewById(0 + 7);
        final ImageButton imageButton8 = (ImageButton) findViewById(0 + 8);
        final GetCurrentStatus getCurrentStatus = new GetCurrentStatus();
        final Toast toast = Toast.makeText(this, moveImage.getMsg(), Toast.LENGTH_LONG);

        imageButton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton0.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(bmp, newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    isFinish = false;
                }
            }
        });

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                newMoveedImagesList = moveImage.step(SHMap, imageButton1.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }

            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newMoveedImagesList = moveImage.step(SHMap, imageButton2.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton3.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });

        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton4.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton5.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton6.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton7.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newMoveedImagesList = moveImage.step(SHMap, imageButton8.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });

    }

    private void mediumImageView() {
        final ImageButton imageButton0 = (ImageButton) findViewById(0);
        final ImageButton imageButton1 = (ImageButton) findViewById(0 + 1);
        final ImageButton imageButton2 = (ImageButton) findViewById(0 + 2);
        final ImageButton imageButton3 = (ImageButton) findViewById(0 + 3);
        final ImageButton imageButton4 = (ImageButton) findViewById(0 + 4);
        final ImageButton imageButton5 = (ImageButton) findViewById(0 + 5);
        final ImageButton imageButton6 = (ImageButton) findViewById(0 + 6);
        final ImageButton imageButton7 = (ImageButton) findViewById(0 + 7);
        final ImageButton imageButton8 = (ImageButton) findViewById(0 + 8);
        final ImageButton imageButton9 = (ImageButton) findViewById(0 + 9);
        final ImageButton imageButton10 = (ImageButton) findViewById(0 + 10);
        final ImageButton imageButton11 = (ImageButton) findViewById(0 + 11);
        final ImageButton imageButton12 = (ImageButton) findViewById(0 + 12);
        final ImageButton imageButton13 = (ImageButton) findViewById(0 + 13);
        final ImageButton imageButton14 = (ImageButton) findViewById(0 + 14);
        final ImageButton imageButton15 = (ImageButton) findViewById(0 + 15);
        final Toast toast = Toast.makeText(this, "Done", Toast.LENGTH_LONG);

        imageButton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newMoveedImagesList = moveImage.step(SHMap, imageButton0.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton1.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }

            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton2.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton3.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton4.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton5.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton6.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton7.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton8.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton9.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton10.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton11.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton12.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton13.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton14.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newMoveedImagesList = moveImage.step(SHMap, imageButton15.getId(), row, column);
                setNewImages(newMoveedImagesList);
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }

            }
        });

    }

    private void hardImageView() {
        final ImageButton imageButton0 = (ImageButton) findViewById(0);
        final ImageButton imageButton1 = (ImageButton) findViewById(0 + 1);
        final ImageButton imageButton2 = (ImageButton) findViewById(0 + 2);
        final ImageButton imageButton3 = (ImageButton) findViewById(0 + 3);
        final ImageButton imageButton4 = (ImageButton) findViewById(0 + 4);
        final ImageButton imageButton5 = (ImageButton) findViewById(0 + 5);
        final ImageButton imageButton6 = (ImageButton) findViewById(0 + 6);
        final ImageButton imageButton7 = (ImageButton) findViewById(0 + 7);
        final ImageButton imageButton8 = (ImageButton) findViewById(0 + 8);
        final ImageButton imageButton9 = (ImageButton) findViewById(0 + 9);
        final ImageButton imageButton10 = (ImageButton) findViewById(0 + 10);
        final ImageButton imageButton11 = (ImageButton) findViewById(0 + 11);
        final ImageButton imageButton12 = (ImageButton) findViewById(0 + 12);
        final ImageButton imageButton13 = (ImageButton) findViewById(0 + 13);
        final ImageButton imageButton14 = (ImageButton) findViewById(0 + 14);
        final ImageButton imageButton15 = (ImageButton) findViewById(0 + 15);
        final ImageButton imageButton16 = (ImageButton) findViewById(0 + 16);
        final ImageButton imageButton17 = (ImageButton) findViewById(0 + 17);
        final ImageButton imageButton18 = (ImageButton) findViewById(0 + 18);
        final ImageButton imageButton19 = (ImageButton) findViewById(0 + 19);
        final ImageButton imageButton20 = (ImageButton) findViewById(0 + 20);
        final ImageButton imageButton21 = (ImageButton) findViewById(0 + 21);
        final ImageButton imageButton22 = (ImageButton) findViewById(0 + 22);
        final ImageButton imageButton23 = (ImageButton) findViewById(0 + 23);
        final ImageButton imageButton24 = (ImageButton) findViewById(0 + 24);
        final Toast toast = Toast.makeText(this, "Done", Toast.LENGTH_LONG);

        imageButton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newMoveedImagesList = moveImage.step(SHMap, imageButton0.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton1.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }

            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton2.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton3.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton4.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton5.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton6.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton7.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton8.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton9.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton10.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton11.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton12.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton13.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMoveedImagesList = moveImage.step(SHMap, imageButton14.getId(), row, column);
                setNewImages(newMoveedImagesList);
                //toast.show();
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }
            }
        });
        imageButton15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newMoveedImagesList = moveImage.step(SHMap, imageButton15.getId(), row, column);
                setNewImages(newMoveedImagesList);
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }

            }
        });
     imageButton16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newMoveedImagesList = moveImage.step(SHMap, imageButton16.getId(), row, column);
                setNewImages(newMoveedImagesList);
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }

            }
        });
        imageButton17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newMoveedImagesList = moveImage.step(SHMap, imageButton17.getId(), row, column);
                setNewImages(newMoveedImagesList);
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }

            }
        });
        imageButton18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newMoveedImagesList = moveImage.step(SHMap, imageButton18.getId(), row, column);
                setNewImages(newMoveedImagesList);
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }

            }
        });
        imageButton19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newMoveedImagesList = moveImage.step(SHMap, imageButton19.getId(), row, column);
                setNewImages(newMoveedImagesList);
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }

            }
        });
        imageButton20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newMoveedImagesList = moveImage.step(SHMap, imageButton20.getId(), row, column);
                setNewImages(newMoveedImagesList);
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }

            }
        });
        imageButton21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newMoveedImagesList = moveImage.step(SHMap, imageButton21.getId(), row, column);
                setNewImages(newMoveedImagesList);
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }

            }
        });
        imageButton22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newMoveedImagesList = moveImage.step(SHMap, imageButton22.getId(), row, column);
                setNewImages(newMoveedImagesList);
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }

            }
        });
        imageButton23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newMoveedImagesList = moveImage.step(SHMap, imageButton23.getId(), row, column);
                setNewImages(newMoveedImagesList);
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }

            }
        });
        imageButton24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newMoveedImagesList = moveImage.step(SHMap, imageButton24.getId(), row, column);
                setNewImages(newMoveedImagesList);
                isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                if (isFinish) {
                    toast.show();
                    SetOriginalImagesToMatrix();
                    isFinish = false;
                }

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
     * Set The new images postion to the imageButtons for each move that the user done.
     *
     * @param SHMap
     */
    public void setNewImages(HashMap<Integer, Bitmap> SHMap) {
        this.SHMap = SHMap;
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
            Intent it= new Intent(getBaseContext(),AfterTheGameActivity.class);
            it.putExtra("Level",level);
            startActivity(it);

        }
    }
}