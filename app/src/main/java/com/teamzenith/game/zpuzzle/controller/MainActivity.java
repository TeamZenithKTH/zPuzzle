package com.teamzenith.game.zpuzzle.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.model.Level;
import com.teamzenith.game.zpuzzle.model.LevelFactory;
import com.teamzenith.game.zpuzzle.model.LevelType;
import com.teamzenith.game.zpuzzle.util.ImagesIDs;
import com.teamzenith.game.zpuzzle.util.MoveImage;
import com.teamzenith.game.zpuzzle.util.ShufflingImage;

import java.util.HashMap;

/**
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button hardBtn;
    Button medelBtn;
    Button kidsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createComponents();
        Actions();
    }

    private void createComponents() {
        hardBtn=(Button) findViewById(R.id.hardBtn);
        medelBtn=(Button) findViewById(R.id.medelBtn);
        kidsBtn=(Button) findViewById(R.id.kidsBtn);
    }

    private void Actions() {
        hardBtn.setOnClickListener(this);
        medelBtn.setOnClickListener(this);
        kidsBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Button btn=(Button)v;
        Level level;
        LevelFactory levelFactory= LevelFactory.getInstance();


        if(btn.getId()==R.id.hardBtn){
            level=levelFactory.createNiveau(LevelType.HARD);

        }
        else if(btn.getId()==R.id.medelBtn){
            level=levelFactory.createNiveau(LevelType.MEDIUM);
        }
        else{
            level=levelFactory.createNiveau(LevelType.KIDS);
        }

        Intent intent=new Intent(this,Game.class);
        intent.putExtra("Level",level);
        startActivity(intent);
    }
}
