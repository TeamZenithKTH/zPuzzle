package com.teamzenith.game.zpuzzle.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.dbhandler.GetUserInformation;
import com.teamzenith.game.zpuzzle.dbhandler.UpdateUserImage;
import com.teamzenith.game.zpuzzle.model.User;
import com.teamzenith.game.zpuzzle.model.UsersNameID;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by alaaalkassar on 4/1/17.
 */

public class ProfileActivity extends AppCompatActivity implements GetUserInformation, UpdateUserImage {
    private Button updateUserProfile, cancleUserUpdate;
    private User player, user;
    private String userID, userImage;
    private static int IMG_RESULT = 1;
    private Bitmap currentImage;
    private String newImageFile;
    private File imageFile;
    private EditText userNameUpdate;
    private TextView userEmailUpdate;
    private boolean faceBookLogin;
    private ImageView userImageView;
    private Intent intent;
    private ProfileController profileController;
    private String imageURL;
    private EditText userPasswordUpdate;
    private EditText userOldPassword;
    private String newPassword;
    private String email;
    private String oldPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
        Intent mIntent = getIntent();
        updateUserProfile = (Button) findViewById(R.id.update_user_profile);
        userImageView = (ImageView) findViewById(R.id.user_profile_image);
        faceBookLogin = (boolean) mIntent.getSerializableExtra("faceBookLogin");
        if (faceBookLogin == true) {
            updateUserProfile.setEnabled(false);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        profileController = new ProfileController();
        player = (User) mIntent.getSerializableExtra("player");
        profileController.setToController(this, player);
        userNameUpdate = (EditText) findViewById(R.id.user_name_update);
        userEmailUpdate = (TextView) findViewById(R.id.user_email_update);
        userPasswordUpdate = (EditText) findViewById(R.id.update_password);
        userOldPassword = (EditText) findViewById(R.id.user_old_password);
        cancleUserUpdate = (Button) findViewById(R.id.cancel_user_update);
        userImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMG_RESULT);
            }
        });
        updateUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileController.setToControllerFromProfileActivity(userImageView, ProfileActivity.this, player.getUserID());
            }
        });
        cancleUserUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updatePassword() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        email = userEmailUpdate.getText().toString().trim();
        oldPassword = userOldPassword.getText().toString().trim();
        newPassword = userPasswordUpdate.getText().toString().trim();
        final Toast passwordUpdated = Toast.makeText(this, "Password updated", Toast.LENGTH_SHORT);
        final Toast passwordNotUpdated = Toast.makeText(this, "Error password not updated", Toast.LENGTH_SHORT);
        final Toast errorAuth = Toast.makeText(this, "Error auth failed", Toast.LENGTH_SHORT);
        AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        passwordUpdated.show();
                                        userOldPassword.setText("");
                                        userPasswordUpdate.setText("");
                                    } else {
                                        passwordNotUpdated.show();
                                    }
                                }
                            });
                        } else {
                            errorAuth.show();
                        }
                    }
                });
    }

    private void checkEmailAndUserName() throws ParseException {
        player.setUserName(userNameUpdate.getText().toString().trim());
        UsersNameID usersNameID = new UsersNameID();
        usersNameID.setUserName(userNameUpdate.getText().toString().trim());
        usersNameID.setUserID(player.getUserID());
        updatePassword();
        try {
            profileController.save(player, usersNameID);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_RESULT && resultCode == RESULT_OK && null != data) {
            Uri photoUri = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            if (photoUri != null) {
                try {
                    currentImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                userImageView.setImageBitmap(currentImage);
            }
        }
    }


    @Override
    public void get(User user) {
        userNameUpdate.setText(user.getUserName());
        userEmailUpdate.setText(user.getUserEmail());
        userImage = user.getUserImage();
        Picasso.with(getBaseContext()).load(userImage).into(userImageView);
    }

    @Override
    public void getImage(String imageURL) {
        this.imageURL = imageURL;
        player.setUserImage(imageURL);
        try {
            checkEmailAndUserName();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}


