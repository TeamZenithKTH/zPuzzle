package com.teamzenith.game.zpuzzle.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.model.Level;
import com.teamzenith.game.zpuzzle.model.User;
import com.teamzenith.game.zpuzzle.model.UserHistoryEntry;


import java.io.File;
import java.text.ParseException;

/**
 * Created by Hichem Memmi on 2017-03-27.
 */

public class AfterTheGameActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView playAgainBtn;
    private ImageView goToMainBtn;
    private ImageView saveHistory;
    private Level level;
    private  TextView movementTextView;
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
    private  File imageFile;
    private String fileName;
    private int current;


    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.after_the_game);
        intentFromGameActivity=getIntent();
       level=(Level) intentFromGameActivity.getSerializableExtra("Level");
        scale = getApplicationContext().getResources().getDisplayMetrics().density;
        historyController = new HistoryController();

        createComponents();
        actions();
    }

    private void createComponents() {
        playAgainBtn=(ImageView) findViewById(R.id.playAgain);
        goToMainBtn=(ImageView) findViewById(R.id.backToMain);
        saveHistory = (ImageView) findViewById(R.id.save_history);
        movementTextView=(TextView) findViewById(R.id.movementCounter);
        timerTextView=(TextView) findViewById(R.id.timer);
        solvedImage=(ImageView) findViewById(R.id.solvedImage);
        player = (User) intentFromGameActivity.getSerializableExtra("player");
        method=(ImageChooser.Method)intentFromGameActivity.getSerializableExtra("method");
        if(method.equals(ImageChooser.Method.RANDOM)){
            fileName=intentFromGameActivity.getStringExtra("file");
        }
    }


    private void actions() {
        playAgainBtn.setOnClickListener(this);
        goToMainBtn.setOnClickListener(this);
        saveHistory.setOnClickListener(this);

        countMovementString=intentFromGameActivity.getStringExtra("CountMovement");
        String countMovementStringColored = "<font color='#EE0000'>"+countMovementString+"</font>";
        movementTextView.setText(Html.fromHtml("You solved on "+ countMovementStringColored+" steps"));

        timerCounterString=intentFromGameActivity.getStringExtra("TimerCounter");
        String timerCounterStringColored = "<font color='#EE0000'>"+timerCounterString+"</font>";
        timerTextView.setText(Html.fromHtml("Your time was "+timerCounterStringColored+" seconds"));



        solved=null;

        if(method.equals(ImageChooser.Method.RANDOM)){
            idOfDrawable=intentFromGameActivity.getIntExtra("Image",0);
            solved = BitmapFactory.decodeResource(getResources(), idOfDrawable);
            solved =Bitmap.createScaledBitmap(solved, (int) (300 * scale), (int) (300 * scale), false);
            current=intentFromGameActivity.getIntExtra("current",current);


        }

        else{
            imageFile = (File)intentFromGameActivity.getSerializableExtra("Image");
            solved = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()), (int)(300 * scale), (int)(300 * scale), true);
        }

        solvedImage.setImageBitmap(solved);


        userID=player.getUserID();
        userHistoryEntry = new UserHistoryEntry(userID,level,countMovementString,timerCounterString);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.playAgain){
            Intent playAgainIntent=new Intent(getBaseContext(),Game.class);
            playAgainIntent.putExtra("Level",level);
            playAgainIntent.putExtra("player",player);
            playAgainIntent.putExtra("method",method);
            if(method.equals(ImageChooser.Method.RANDOM)){
                playAgainIntent.putExtra("file",fileName);
                playAgainIntent.putExtra("idOfDrawable",idOfDrawable);
                playAgainIntent.putExtra("current",current);

            }
            else{
                playAgainIntent.putExtra("Image",imageFile);
            }

            startActivity(playAgainIntent);
        }
        else if (v.getId() == R.id.save_history) {
            Toast.makeText(this, "History Saved", Toast.LENGTH_SHORT).show();

            try {
                historyController.save(userHistoryEntry);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else{
            Intent goBackToMain=new Intent(getBaseContext(),MainActivity.class);
            goBackToMain.putExtra("player",player);
            startActivity(goBackToMain);

        }
    }
}
