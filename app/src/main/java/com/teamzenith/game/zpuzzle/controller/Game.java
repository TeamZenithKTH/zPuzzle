package com.teamzenith.game.zpuzzle.controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.model.Hard;
import com.teamzenith.game.zpuzzle.model.Easy;
import com.teamzenith.game.zpuzzle.model.Level;
import com.teamzenith.game.zpuzzle.model.Medium;
import com.teamzenith.game.zpuzzle.model.User;
import com.teamzenith.game.zpuzzle.util.GetCurrentStatus;
import com.teamzenith.game.zpuzzle.util.ImageSplit;
import com.teamzenith.game.zpuzzle.util.ImagesIDs;
import com.teamzenith.game.zpuzzle.util.MoveImage;
import com.teamzenith.game.zpuzzle.util.ShufflingImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    private File imageFile = null;
    private Drawable d;
    private int idOfDrawable = 0;
    private User player;
    private ImageChooser.Method method;
    private static int GALERI_RESULT = 1;
    private RandomImageAdapter adapterView = null;
    private String fileName;
    private int current;
    private ViewPager viewPager;


    public Game() {
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_game);
        scale = getApplicationContext().getResources().getDisplayMetrics().density;
        createComponents();
        initComponent();

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.line1);

        if (method.equals(ImageChooser.Method.GALERI)) {
            photoButton = new ImageView(this);
            photoButton.setImageResource(R.drawable.photofromgaleri);
            photoButton.setId(R.id.pickphotogaleri);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER;
            photoButton.setLayoutParams(layoutParams);
            linearLayout.addView(photoButton);
            prepareAnImage();

        } else if (method.equals(ImageChooser.Method.CAMERA)) {
            photoButton = new ImageView(this);
            photoButton.setImageResource(R.drawable.cm);
            photoButton.setId(R.id.pickphotocamera);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER;
            photoButton.setLayoutParams(layoutParams);
            linearLayout.addView(photoButton);
            prepareAnImage();
        } else {
            viewPager = new ViewPager(this);
            adapterView = new RandomImageAdapter(this.getBaseContext());
            viewPager.setId(R.id.pickphotorandom);
            viewPager.setAdapter(adapterView);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER;
            viewPager.setLayoutParams(layoutParams);
            Intent it = getIntent();
            current = it.getIntExtra("current", 0);
            viewPager.setCurrentItem(current);
            linearLayout.addView(viewPager);

            // File filePath = getFileStreamPath(fileName);
            idOfDrawable = it.getIntExtra("idOfDrawable", 0);
            prepareAnImage();
        }

        actions();

        ActivityCompat.requestPermissions(Game.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
    }

    private void createComponents() {
        currentMovement = (TextView) findViewById(R.id.currentMovement);
        timerCounter = (TextView) findViewById(R.id.timerCounter);
    }

    private void initComponent() {
        Intent intent = this.getIntent();
        imageFile = (File) intent.getSerializableExtra("Image");
        level = (Level) intent.getSerializableExtra("Level");
        player = (User) intent.getSerializableExtra("player");
        method = (ImageChooser.Method) intent.getSerializableExtra("method");
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
        if (photoButton != null) {
            photoButton.setOnClickListener(this);
        } else {
            adapterView.setOnPrepareListener(new PrepareForClick() {
                @Override
                public void setOnPrepare(View p) {
                    if (ll != null) {
                        count = 0;
                        countMovement = 0;
                        ll.removeAllViews();
                        ll.refreshDrawableState();
                    }
                    ImageView imageView = (ImageView) p;
                    idOfDrawable = (Integer) imageView.getTag();
                    prepareAnImage();
                }
            });
        }

    }

    @Override
    public void onClick(View v) {

        if (ll != null) {
            count = 0;
            countMovement = 0;
            ll.removeAllViews();
            ll.refreshDrawableState();
        }
        if (method.equals(ImageChooser.Method.CAMERA)) {
            Uri relativePath = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/images.jpeg"));
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, relativePath);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        } else if (method.equals(ImageChooser.Method.GALERI)) {
            Intent galeriIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(galeriIntent, GALERI_RESULT);
        }


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                prepareAnImage();
            } else if (requestCode == GALERI_RESULT) {
                Uri selectedImageURI = data.getData();
                imageFile = new File(getRealPathFromURI(selectedImageURI));
                prepareAnImage();
            }

        }

    }

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
    }

    public Bitmap createBitmap(File imgFile1) {

        int exifOrientation = getImageOrientation(imgFile1);

        if (level instanceof Hard) {

            if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
                Bitmap bitmapNeedsToRotate = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), (int) (350 * scale), (int) (350 * scale), true);
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                return Bitmap.createBitmap(bitmapNeedsToRotate, 0, 0, bitmapNeedsToRotate.getWidth(), bitmapNeedsToRotate.getHeight(), matrix, true);
            } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
                Bitmap bitmapNeedsToRotate = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), (int) (350 * scale), (int) (350 * scale), true);
                Matrix matrix = new Matrix();
                matrix.postRotate(180);
                return Bitmap.createBitmap(bitmapNeedsToRotate, 0, 0, bitmapNeedsToRotate.getWidth(), bitmapNeedsToRotate.getHeight(), matrix, true);
            } else
                return Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), (int) (350 * scale), (int) (350 * scale), true);
        } else if (level instanceof Medium) {
            return Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), (int) (344 * scale), (int) (344 * scale), true);
        } else {
            return Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), (int) (300 * scale), (int) (300 * scale), true);
        }
    }

    private int getImageOrientation(File imgFile1) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imgFile1.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
    }

    public void prepareAnImage() {
        Bitmap photo;
        if (method.equals(ImageChooser.Method.RANDOM)) {

            photo = BitmapFactory.decodeResource(getResources(), idOfDrawable);

            if (level instanceof Hard) {
                photo = Bitmap.createScaledBitmap(photo, (int) (350 * scale), (int) (350 * scale), false);

            } else if (level instanceof Medium) {
                photo = Bitmap.createScaledBitmap(photo, (int) (344 * scale), (int) (344 * scale), false);

            } else {
                photo = Bitmap.createScaledBitmap(photo, (int) (300 * scale), (int) (300 * scale), false);
            }

        } else if (method.equals(ImageChooser.Method.GALERI)) {
            photo = createBitmap(imageFile);

        } else {
            imageFile = new File(Environment.getExternalStorageDirectory() + "/images.jpeg");
            photo = createBitmap(imageFile);

        }

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
            if (method.equals(ImageChooser.Method.RANDOM)) {
                it.putExtra("Image", idOfDrawable);
                it.putExtra("current", viewPager.getCurrentItem());
            } else {
                it.putExtra("Image", imageFile);
            }

            it.putExtra("player", player);
            it.putExtra("method", method);
            T.cancel();
            startActivity(it);

        }
    }
}