package com.teamzenith.game.zpuzzle.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.dbhandler.GetImageURL;
import com.teamzenith.game.zpuzzle.dbhandler.UploadToDatabase;
import com.teamzenith.game.zpuzzle.model.Level;
import com.teamzenith.game.zpuzzle.model.User;
import com.teamzenith.game.zpuzzle.model.UserHistoryEntry;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by Hichem Memmi on 2017-03-27.
 */

public class AfterTheGameActivity extends AppCompatActivity implements View.OnClickListener, GetImageURL {

    private ImageView playAgainBtn;
    private ImageView goToMainBtn;
    private ImageView saveHistory;
    private Level level;
    private TextView movementTextView;
    private Intent intentFromGameActivity;
    private TextView timerTextView;
    private ImageView solvedImage;
    private float scale;

    private HistoryController historyController;
    private User user;
    private String userID;
    private String timerCounterString;
    private String countMovementString;
    private UserHistoryEntry userHistoryEntry;
    private User player;
    private ImageChooser.Method method;
    private int idOfDrawable;
    private Bitmap solved;
    private File imageFile;
    private String fileName, imageFileURL;
    private int current;
    private UploadToDatabase uploadToDatabase;
    private boolean b = false;

    /**
     *
     * @param bundle
     */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.after_the_game);
        intentFromGameActivity = getIntent();
        level = (Level) intentFromGameActivity.getSerializableExtra("Level");
        scale = getApplicationContext().getResources().getDisplayMetrics().density;
        historyController = new HistoryController();

        createComponents();
        actions();
    }

    private void createComponents() {
        playAgainBtn = (ImageView) findViewById(R.id.playAgain);
        goToMainBtn = (ImageView) findViewById(R.id.backToMain);
        saveHistory = (ImageView) findViewById(R.id.save_history);
        movementTextView = (TextView) findViewById(R.id.movementCounter);
        timerTextView = (TextView) findViewById(R.id.timer);
        solvedImage = (ImageView) findViewById(R.id.solvedImage);
        player = (User) intentFromGameActivity.getSerializableExtra("player");
        method = (ImageChooser.Method) intentFromGameActivity.getSerializableExtra("method");
        if (method.equals(ImageChooser.Method.RANDOM)) {
            fileName = intentFromGameActivity.getStringExtra("file");
        }
        uploadToDatabase = new UploadToDatabase();

    }


    private void actions() {
        playAgainBtn.setOnClickListener(this);
        goToMainBtn.setOnClickListener(this);

        saveHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                historyController.setToControllerFromAfterTheGameActivity(solvedImage, AfterTheGameActivity.this, userID);


            }
        });


        countMovementString = intentFromGameActivity.getStringExtra("CountMovement");
        String countMovementStringColored = "<font color='#EE0000'>" + countMovementString + "</font>";
        movementTextView.setText(Html.fromHtml("You solved on " + countMovementStringColored +" steps"));

        timerCounterString = intentFromGameActivity.getStringExtra("TimerCounter");
        timerTextView.setText(Html.fromHtml("Your time was " + "<font color='#EE0000'>" +timerCounterString+ "</font>"));
        userID = player.getUserID();

        solved = null;

        if (method.equals(ImageChooser.Method.RANDOM)) {
            idOfDrawable = intentFromGameActivity.getIntExtra("Image", 0);
            solved = BitmapFactory.decodeResource(getResources(), idOfDrawable);
            solved = Bitmap.createScaledBitmap(solved, (int) (300 * scale), (int) (300 * scale), false);
            current = intentFromGameActivity.getIntExtra("current", current);


        } else {
            imageFile = (File) intentFromGameActivity.getSerializableExtra("Image");

            Bitmap bitmapNeedsToRotate;
            Matrix matrix;
            int exifOrientation = getImageOrientation(imageFile);
            if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
                bitmapNeedsToRotate = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()), (int) (level.getSize() * scale), (int) (level.getSize() * scale), true);
                matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap.createBitmap(bitmapNeedsToRotate, 0, 0, bitmapNeedsToRotate.getWidth(), bitmapNeedsToRotate.getHeight(), matrix, true);
                solved = Bitmap.createBitmap(bitmapNeedsToRotate, 0, 0, bitmapNeedsToRotate.getWidth(), bitmapNeedsToRotate.getHeight(), matrix, true);
            } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
                bitmapNeedsToRotate = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()), (int) (level.getSize() * scale), (int) (level.getSize() * scale), true);
                matrix = new Matrix();
                matrix.postRotate(180);
                solved = Bitmap.createBitmap(bitmapNeedsToRotate, 0, 0, bitmapNeedsToRotate.getWidth(), bitmapNeedsToRotate.getHeight(), matrix, true);
            } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
                bitmapNeedsToRotate = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()), (int) (level.getSize() * scale), (int) (level.getSize() * scale), true);
                matrix = new Matrix();
                matrix.postRotate(270);
                solved = Bitmap.createBitmap(bitmapNeedsToRotate, 0, 0, bitmapNeedsToRotate.getWidth(), bitmapNeedsToRotate.getHeight(), matrix, true);
            } else {
                solved = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()), (int) (level.getSize() * scale), (int) (level.getSize() * scale), true);
            }
        }

        solvedImage.setImageBitmap(solved);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.playAgain) {
            Intent playAgainIntent = new Intent(getBaseContext(), Game.class);
            playAgainIntent.putExtra("Level", level);
            playAgainIntent.putExtra("player", player);
            playAgainIntent.putExtra("method", method);
            if (method.equals(ImageChooser.Method.RANDOM)) {
                playAgainIntent.putExtra("idOfDrawable", idOfDrawable);
                playAgainIntent.putExtra("current", current);

            } else {
                playAgainIntent.putExtra("Image", imageFile);
            }

            startActivity(playAgainIntent);
            finish();
        } else {
            Intent goBackToMain = new Intent(getBaseContext(), MainActivity.class);
            goBackToMain.putExtra("player", player);
            startActivity(goBackToMain);
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    public void get(String imageURL) {

        userHistoryEntry = new UserHistoryEntry(userID, level, countMovementString, timerCounterString, imageURL);
        try {
            historyController.save(userHistoryEntry);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
