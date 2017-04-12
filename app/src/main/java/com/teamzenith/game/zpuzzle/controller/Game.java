package com.teamzenith.game.zpuzzle.controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.model.Level;
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
    private Timer T;
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
    private int current;
    private ViewPager viewPager;
    private int minute = 0;
    private int hour = 0;
    private int seconds = 0;
    private String next;
    private String timer;

    /**
     * This method called once the activity called
     * @param bundle
     */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_game);
        scale = getApplicationContext().getResources().getDisplayMetrics().density;
        createComponents();
        initComponent();

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.line1);
        T = new Timer();


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
            photoButton.setImageResource(R.drawable.ic_camera);
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

            idOfDrawable = it.getIntExtra("idOfDrawable", 0);
            prepareAnImage();
        }
        actions();
        ActivityCompat.requestPermissions(Game.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
    }

    /**
     * this method is to create two textView
     */
    private void createComponents() {
        currentMovement = (TextView) findViewById(R.id.currentMovement);
        timerCounter = (TextView) findViewById(R.id.timerCounter);
    }

    /**
     * this method is to fill variables
     */
    private void initComponent() {
        Intent intent = this.getIntent();
        imageFile = (File) intent.getSerializableExtra("Image");
        level = (Level) intent.getSerializableExtra("Level");
        player = (User) intent.getSerializableExtra("player");
        method = (ImageChooser.Method) intent.getSerializableExtra("method");
        row = level.getSizeOfRow();
        column = level.getSizeOfColumn();

    }

    /**
     * this method handle the action listener
     */
    private void actions() {
        if (photoButton != null) {
            photoButton.setOnClickListener(this);
        } else {
            adapterView.setOnPrepareListener(new PrepareForClick() {
                @Override
                public void setOnPrepare(View p) {
                    countMovement = 0;
                    if (ll != null) {
                        count = 0;
                        hour = 0;
                        seconds = 0;
                        minute = 0;
                        countMovement = 0;
                        ll.removeAllViews();
                        ll.refreshDrawableState();
                        T.cancel();
                        T = new Timer();
                    }
                    ImageView imageView = (ImageView) p;
                    idOfDrawable = (Integer) imageView.getTag();
                    prepareAnImage();

                    currentMovement.setText(Html.fromHtml("your current move is <font color='#EE0000'> 0 </font>"));
                }
            });
        }

    }

    /**
     * This method called  when the button has been clicked
     * @param v
     */
    @Override
    public void onClick(View v) {

        if (ll != null) {
            // count = 0;
            countMovement = 0;
            count = 0;
            hour = 0;
            seconds = 0;
            minute = 0;
            ll.removeAllViews();
            ll.refreshDrawableState();
            T.cancel();
            T = new Timer();
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

    /**
     * this method called when the user pick
     * @param requestCode
     * @param resultCode
     * @param data
     */
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

    /**
     * this method is to create an image bitmap with the right orientation, that mean you can pick a photo
     * and this method will rotate it if it is needed, this method is called by prepareImage method
     * @param imgFile1
     * @return
     */
    private Bitmap createBitmap(File imgFile1) {
        Bitmap bitmapNeedsToRotate;
        Matrix matrix;
        int exifOrientation = getImageOrientation(imgFile1);
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            bitmapNeedsToRotate = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), (int) (level.getSize() * scale), (int) (level.getSize() * scale), true);
            matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap.createBitmap(bitmapNeedsToRotate, 0, 0, bitmapNeedsToRotate.getWidth(), bitmapNeedsToRotate.getHeight(), matrix, true);
            return Bitmap.createBitmap(bitmapNeedsToRotate, 0, 0, bitmapNeedsToRotate.getWidth(), bitmapNeedsToRotate.getHeight(), matrix, true);
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            bitmapNeedsToRotate = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), (int) (level.getSize() * scale), (int) (level.getSize() * scale), true);
            matrix = new Matrix();
            matrix.postRotate(180);
            return Bitmap.createBitmap(bitmapNeedsToRotate, 0, 0, bitmapNeedsToRotate.getWidth(), bitmapNeedsToRotate.getHeight(), matrix, true);
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            bitmapNeedsToRotate = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), (int) (level.getSize() * scale), (int) (level.getSize() * scale), true);
            matrix = new Matrix();
            matrix.postRotate(270);
            return Bitmap.createBitmap(bitmapNeedsToRotate, 0, 0, bitmapNeedsToRotate.getWidth(), bitmapNeedsToRotate.getHeight(), matrix, true);
        } else {
            return Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), (int) (level.getSize() * scale), (int) (level.getSize() * scale), true);
        }
    }

    /**
     * this method is to get the orientation of an image
     * @param imgFile1
     * @return
     */
    private int getImageOrientation(File imgFile1) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imgFile1.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
    }

    /**
     * create a bitmap when the user pick a photo
     */
    private void prepareAnImage() {
        Bitmap photo;
        if (method.equals(ImageChooser.Method.RANDOM)) {
            photo = BitmapFactory.decodeResource(getResources(), idOfDrawable);
            photo = Bitmap.createScaledBitmap(photo, (int) (level.getSize() * scale), (int) (level.getSize() * scale), false);
        } else if (method.equals(ImageChooser.Method.GALERI)) {
            photo = createBitmap(imageFile);
        } else {
            imageFile = new File(Environment.getExternalStorageDirectory() + "/images.jpeg");
            photo = createBitmap(imageFile);
        }

        try {
            count = 0;
            T.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            timer = ((String.valueOf(hour).length() > 1) ? hour : "0" + hour) + ":" + ((String.valueOf(minute).length() > 1) ? minute : "0" + minute) + ":" + ((String.valueOf(seconds).length() > 1) ? seconds : "0" + seconds) + ":" +
                                    ((String.valueOf(count).length() > 1) ?
                                            (String.valueOf(count).length() > 2) ?
                                                    count
                                                    : "0" + count
                                            : "00" + count);
                            next = "<font color='#EE0000'>" + timer + "</font>";
                            timerCounter.setText(Html.fromHtml("Timer: " + next));
                            count++;
                            if (count == 999) {
                                seconds++;
                                count = 0;
                                if (seconds == 59) {
                                    minute++;
                                    seconds = 0;
                                    if (minute == 59) {
                                        minute = 0;
                                        hour++;
                                    }

                                }
                            }
                        }
                    });
                }
            }, 1, 1);
            bmp = imageSplit.get(photo, row, column);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ShufflingImage shufflingImage = new ShufflingImage();
        tmpbmp = shufflingImage.shuffle(bmp);
        SHMap = shufflingImage.getShuffledOrder();
        settingImages(SHMap);
        createImageViews(SHMap);
    }

    /**
     * create dynamically the tablelayout and imageButtons which will hold the pieces of an image
     * @param SHMap
     */
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
                params.width = (int) (level.getSizeOfPiece() * scale);
                params.height = (int) (level.getSizeOfPiece() * scale);
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
                im.setImageBitmap(tmpbmp[i]);
                TableRow.LayoutParams params = new TableRow.LayoutParams();
                params.setMargins(1, 1, 1, 1);
                params.width = (int) (level.getSizeOfPiece() * scale);
                params.height = (int) (level.getSizeOfPiece() * scale);
                im.setLayoutParams(params);
                tableRow.addView(im, params);
                imageButtons[i] = im;
                j++;
            }
        }
        imagesIDList = imagesIDs.getposition();
    }

    /**
     * This method is to allow the user to move pieces and it controls if it is allowed to move a piece
     * @param SHMap
     */
    private void createImageViews(final HashMap<Integer, Bitmap> SHMap) {
        this.SHMap = SHMap;
        final GetCurrentStatus getCurrentStatus = new GetCurrentStatus();
        for (int i = 0; i < imageButtons.length; i++) {
            imageButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageButton b = (ImageButton) v;
                    newMoveedImagesList = moveImage.step(SHMap, b.getId(), row, column);
                    setNewImages(newMoveedImagesList);
                    isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                    if (isFinish) {
                        SetOriginalImagesToMatrix();
                        isFinish = false;
                    }
                }
            });

        }

    }

    /**
     * this method is a feature available from android lollipop and it is called permission, so the user need to confirm
     * that he acept that the application will use the gallery and camera
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
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
     * this method is to set pieces of an image after the user has been moved a piece
     * @param SHMap
     */
    private void setNewImages(HashMap<Integer, Bitmap> SHMap) {
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
     * check if the puzzle is solved, so this method is called once the user has solved the puzzle
     */
    private void SetOriginalImagesToMatrix() {
        Bitmap[] tmpbitMap;
        tmpbitMap = imageSplit.getOriginalDividedImage();
        for (int i = 0; i < tmpbitMap.length; i++) {
            ImageButton im = (ImageButton) findViewById(i);
            im.setImageBitmap(tmpbitMap[i]);
        }
        Intent it = new Intent(getBaseContext(), AfterTheGameActivity.class);
        it.putExtra("Level", level);
        it.putExtra("CountMovement", String.valueOf(countMovement));
        it.putExtra("TimerCounter", String.valueOf(next));
        it.putExtra("Timer", String.valueOf(timer));

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
        finish();
    }

    /**
     * this method will be called once the user click on the back button
     */
    @Override
    public void onBackPressed() {
        finish();
        T.cancel();

    }
}