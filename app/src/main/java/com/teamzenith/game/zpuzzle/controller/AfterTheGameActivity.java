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

import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.model.Level;


import java.io.File;

/**
 * Created by Hichem Memmi on 2017-03-27.
 */

public class AfterTheGameActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView playAgainBtn;
    private ImageView goToMainBtn;
    private Level level;
    private  TextView movementTextView;
    private Intent intentFromGameActivity;
    private TextView timerTextView;
    private ImageView solvedImage;
    private float scale;

    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.after_the_game);
        intentFromGameActivity=getIntent();
       level=(Level) intentFromGameActivity.getSerializableExtra("Level");
        scale = getApplicationContext().getResources().getDisplayMetrics().density;
        createComponents();
        actions();
    }

    private void createComponents() {
        playAgainBtn=(ImageView) findViewById(R.id.playAgain);
        goToMainBtn=(ImageView) findViewById(R.id.backToMain);
        movementTextView=(TextView) findViewById(R.id.movementCounter);
        timerTextView=(TextView) findViewById(R.id.timer);
        solvedImage=(ImageView) findViewById(R.id.solvedImage);
    }


    private void actions() {
        playAgainBtn.setOnClickListener(this);
        goToMainBtn.setOnClickListener(this);
        String countMovementString=intentFromGameActivity.getStringExtra("CountMovement");

        String countMovementStringColored = "<font color='#EE0000'>"+countMovementString+"</font>";
        movementTextView.setText(Html.fromHtml("You solved on "+ countMovementStringColored+" steps"));

        String timerCounterString=intentFromGameActivity.getStringExtra("TimerCounter");
        String timerCounterStringColored = "<font color='#EE0000'>"+timerCounterString+"</font>";
        timerTextView.setText(Html.fromHtml("Your time was "+timerCounterStringColored+" seconds"));


        File imageFile = (File)intentFromGameActivity.getSerializableExtra("Image");
        Bitmap solved = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()), (int)(300 * scale), (int)(300 * scale), true);
        solvedImage.setImageBitmap(solved);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.playAgain){
            Intent playAgainIntent=new Intent(getBaseContext(),Game.class);
            playAgainIntent.putExtra("Level",level);
            startActivity(playAgainIntent);
        }
        else{
            Intent goBackToMain=new Intent(getBaseContext(),MainActivity.class);
            startActivity(goBackToMain);
        }
    }
}
