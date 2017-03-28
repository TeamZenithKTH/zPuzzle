package com.teamzenith.game.zpuzzle.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.view.View;
import android.widget.Toast;

import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.model.Level;
import java.io.ByteArrayOutputStream;


/**
 * Created by Krish on 28-03-2017.
 */

public class PickImage  extends AppCompatActivity{


    Level level;
    ImageButton gImageButton;
    ImageButton pImageButton;
    ViewPager mViewPager;
    Intent gintent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_image);

        gintent = this.getIntent();
        level = (Level) gintent.getSerializableExtra("Level");

        gintent = new Intent(PickImage.this, GalleryImageAdapter.class );


        // Random image picture loading
        mViewPager = (ViewPager) findViewById(R.id.viewPageRandom);
        RandomImageAdapter adapterView = new RandomImageAdapter(this);
        mViewPager.setAdapter(adapterView);


        mViewPager.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Toast.makeText(PickImage.this, "Please try again", Toast.LENGTH_LONG).show();

             /*   gintent = new Intent(PickImage.this, GalleryImageAdapter.class );
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), view.getId() );
                gintent.putExtra("picture",bitmap);
                gintent.putExtra("Level", level);
                gintent.putExtra("PickImage" ,"Random");
                startActivity(gintent);*/

            }
        });


        // Initate new Activity for Gallery Pictures
        gImageButton = (ImageButton) findViewById(R.id.imageButtonGallery);
        gImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                gintent.putExtra("Level", level);
                gintent.putExtra("PickImage" ,"Gallery");
                startActivity(gintent);

            }
        });


        // Initate new Activity for Taking a Pictures with camera
        pImageButton = (ImageButton) findViewById(R.id.imageButtonCamera);
        pImageButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                gintent.putExtra("Level", level);
                gintent.putExtra("PickImage" ,"Camera");
                startActivity(gintent);

            }

        });
    }


}
