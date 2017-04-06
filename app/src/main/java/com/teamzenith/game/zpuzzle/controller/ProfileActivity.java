package com.teamzenith.game.zpuzzle.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.dbhandler.GetUserInformation;
import com.teamzenith.game.zpuzzle.dbhandler.UserDAO;
import com.teamzenith.game.zpuzzle.model.User;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by alaaalkassar on 4/1/17.
 */

public class ProfileActivity extends AppCompatActivity implements GetUserInformation{
    private ImageView userProfileImage;
    private Button updateUserProfileImage, cancleUserUpdate;
    private User player, user;
    private String userID, userImage;
    private static int GALERI_RESULT = 1;
    private Bitmap currentImage;
    private String newImageFile;
    private File imageFile;
    private Bitmap solved, convertedImage;
    private ByteArrayOutputStream bYtE;
    private EditText userNameUpdate, userEmailUpdate;
    private String userName, newName, newEmail;
    private ProgressDialog progressDialog;
    private ProfileController profileController;
    private DatabaseReference mDatabase, usersDB;
    private String imageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent mIntent = getIntent();
        player = (User) mIntent.getSerializableExtra("player");

        ProfileController profileController = new ProfileController();

        profileController.setToController(this,player);
        userNameUpdate=(EditText) findViewById(R.id.user_name_update);
        userEmailUpdate = (EditText) findViewById(R.id.user_email_update);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
           onDestroy(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void get(User user) {
        userNameUpdate.setText(user.getUserName());
       userEmailUpdate.setText(user.getUserEmail());
    }
}


