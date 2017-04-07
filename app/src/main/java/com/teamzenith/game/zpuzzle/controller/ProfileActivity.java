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

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by alaaalkassar on 4/1/17.
 */

public class ProfileActivity extends AppCompatActivity implements GetUserInformation, UpdateUserImage {
    private Button updateUserProfileImage, cancleUserUpdate;
    private User player, user;
    private String userID, userImage;
    private static int IMG_RESULT = 1;
    private Bitmap currentImage;
    private String newImageFile;
    private File imageFile;
    private EditText userNameUpdate, userEmailUpdate;
    private boolean faceBookLogin;
    private ImageView userImageView;
    private Intent intent;
    private ProfileController profileController;
    private String imageURL;
    private EditText userPasswordUpdate;
    private EditText userOldPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
        Intent mIntent = getIntent();
        updateUserProfileImage = (Button) findViewById(R.id.update_user_profile);
        userImageView = (ImageView) findViewById(R.id.user_profile_image);
        faceBookLogin = (boolean) mIntent.getSerializableExtra("faceBookLogin");
        if (faceBookLogin == true) {
            updateUserProfileImage.setEnabled(false);
        }
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        profileController = new ProfileController();

        player = (User) mIntent.getSerializableExtra("player");
        profileController.setToController(this, player);
        userNameUpdate = (EditText) findViewById(R.id.user_name_update);
        userEmailUpdate = (EditText) findViewById(R.id.user_email_update);
        userPasswordUpdate = (EditText) findViewById(R.id.update_password);
        userOldPassword = (EditText) findViewById(R.id.user_old_password);
        userImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMG_RESULT);

            }
        });

        updateUserProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileController.setToControllerFromProfileActivity(userImageView, ProfileActivity.this, player.getUserID());

            }
        });

    }

    private void updatePassword() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(userEmailUpdate.toString().trim(), userOldPassword.toString().trim());

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(userPasswordUpdate.toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        System.out.println("Password Updated");
                                    } else {
                                        System.out.println("Error password not updated");
                                    }
                                }
                            });
                        } else {
                            System.out.println("Error auth failed");
                        }
                    }
                });
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private void checkEmailAndUserName() throws ParseException {
        if (userNameUpdate == null) {
            Toast.makeText(this, "Username can not be empty", Toast.LENGTH_SHORT).show();
        } else if (userEmailUpdate == null) {
            Toast.makeText(this, "Email can not be empty", Toast.LENGTH_SHORT).show();
        } else {
            player.setUserName(userNameUpdate.getText().toString().trim());
            player.setUserEmail(userEmailUpdate.getText().toString().trim());
            updatePassword();
            try {
                profileController.save(player);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //profileController.save(player);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();// close this activity and return to preview activity (if there is any)
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


