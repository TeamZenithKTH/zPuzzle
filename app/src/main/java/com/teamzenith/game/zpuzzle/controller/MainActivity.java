package com.teamzenith.game.zpuzzle.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.model.Level;
import com.teamzenith.game.zpuzzle.model.LevelFactory;
import com.teamzenith.game.zpuzzle.model.LevelType;

/**
 *
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RadioButton rbEasy;
    RadioButton rbMedium;
    RadioButton rbHard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createComponents();
    }

    private void createComponents() {
        rbEasy=(RadioButton) findViewById(R.id.levelEasy);
        rbMedium=(RadioButton) findViewById(R.id.levelMedium);
        rbHard=(RadioButton) findViewById(R.id.levelHard);
    }

    @Override
    public void onClick(View v) {
        LevelFactory levelFactory= LevelFactory.getInstance();
        Level level = null;
        if (rbEasy.isChecked()) {
            level = levelFactory.createLevel(LevelType.EASY);
        } else if (rbMedium.isChecked()) {
            level = levelFactory.createLevel(LevelType.MEDIUM);
        } else {
            level = levelFactory.createLevel(LevelType.HARD);
        }

        Intent intent=new Intent(this,Game.class);
        intent.putExtra("Level", level);
        startActivity(intent);
    }
}
