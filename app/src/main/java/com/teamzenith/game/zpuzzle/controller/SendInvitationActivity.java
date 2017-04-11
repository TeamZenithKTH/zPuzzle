package com.teamzenith.game.zpuzzle.controller;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.dbhandler.GetUsersNamesIDs;
import com.teamzenith.game.zpuzzle.dbhandler.SendInvitationToUser;
import com.teamzenith.game.zpuzzle.model.SendInvitation;
import com.teamzenith.game.zpuzzle.model.User;
import com.teamzenith.game.zpuzzle.model.UsersNameID;
import com.teamzenith.game.zpuzzle.util.CustomOnItemSelectedListener;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SendInvitationActivity extends AppCompatActivity implements SendInvitationToUser, GetUsersNamesIDs {
    private InvitationsController invitationsController;
    private User player;
    private EditText invitationText, senderPresent;
    private ImageView invitationImage;
    private Spinner levelSpinner, friendSpinner;
    private Button send, cancel;
    private SendInvitation sendInvitation;
    private static int IMG_RESULT = 1;
    private Intent intent;
    private Bitmap currentImage;
    private File image;
    private String imageURL;
    private UsersNameID usersNameID;
    private HashMap<String, String> allUsersList = new HashMap<>();
    private List<String> list = new ArrayList<String>();
    private String friendName, friendID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_invitation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.send_toolbar);
        toolbar.setTitle("Send Invitation");
        setSupportActionBar(toolbar);
        invitationsController = new InvitationsController();
        Intent mIntent = getIntent();
        player = (User) mIntent.getSerializableExtra("player");
        send = (Button) findViewById(R.id.send_invitation);
        cancel = (Button) findViewById(R.id.cancel_send_invitation);
        friendSpinner = (Spinner) findViewById(R.id.select_friend);
        levelSpinner = (Spinner) findViewById(R.id.select_level);
        invitationText = (EditText) findViewById(R.id.invitation_text);
        invitationImage = (ImageView) findViewById(R.id.invitation_image);
        senderPresent = (EditText) findViewById(R.id.sender_present);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        invitationsController.getUsersNames(SendInvitationActivity.this);
        addListenerOnSpinnerItemSelection();
        invitationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMG_RESULT);
                addItemsOnFreindsSpinner(list);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    invitationsController.sendInvitationToUser(invitationImage, SendInvitationActivity.this, player.getUserID());
                    finish();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void addItemsOnFreindsSpinner(List<String> list) {
        this.list = list;
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        friendSpinner.setAdapter(dataAdapter);
    }

    private void addListenerOnSpinnerItemSelection() {
        friendSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
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


                 //
                Bitmap currentImage = null;
                try {
                    currentImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                    image = new File(getRealPathFromURI(photoUri));
                    currentImage=createBitmap(image,currentImage.getHeight(),currentImage.getWidth());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                invitationImage.setImageBitmap(currentImage);
            }
        }
    }


    private Bitmap createBitmap(File imgFile1,int heigh,int width) {
        Bitmap bitmapNeedsToRotate;
        Matrix matrix;
        int exifOrientation = getImageOrientation(imgFile1);
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            bitmapNeedsToRotate = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()),width,heigh, true);
            matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap.createBitmap(bitmapNeedsToRotate, 0, 0, bitmapNeedsToRotate.getWidth(), bitmapNeedsToRotate.getHeight(), matrix, true);
            return Bitmap.createBitmap(bitmapNeedsToRotate, 0, 0, bitmapNeedsToRotate.getWidth(), bitmapNeedsToRotate.getHeight(), matrix, true);
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            bitmapNeedsToRotate = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), width,heigh, true);
            matrix = new Matrix();
            matrix.postRotate(180);
            return Bitmap.createBitmap(bitmapNeedsToRotate, 0, 0, bitmapNeedsToRotate.getWidth(), bitmapNeedsToRotate.getHeight(), matrix, true);
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            bitmapNeedsToRotate = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), width,heigh, true);
            matrix = new Matrix();
            matrix.postRotate(270);
            return Bitmap.createBitmap(bitmapNeedsToRotate, 0, 0, bitmapNeedsToRotate.getWidth(), bitmapNeedsToRotate.getHeight(), matrix, true);
        } else {
            return Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), width,heigh, true);
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


    private int getImageOrientation(File imgFile1) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imgFile1.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
    }

    private void checkNotEmptyInformation(SendInvitation sendInvitation) {
        if (invitationText == null) {
            Toast.makeText(this, "Invitation can not be empty", Toast.LENGTH_SHORT).show();
        } else if (invitationImage == null) {
            Toast.makeText(this, "You need to upload image first.", Toast.LENGTH_SHORT).show();
        } else if (levelSpinner == null) {
            Toast.makeText(this, "Level can not be empty", Toast.LENGTH_SHORT).show();
        } else if (friendSpinner == null) {
            Toast.makeText(this, "Please, choose a friend", Toast.LENGTH_SHORT).show();
        } else if (senderPresent == null) {
            Toast.makeText(this, "Please, add some nice words to be sent to your friend", Toast.LENGTH_SHORT).show();
        } else {
            this.sendInvitation = sendInvitation;
            try {
                invitationsController.send(sendInvitation, player.getUserID());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void getInitationImage(String imageURL) {
        this.imageURL = imageURL;
        friendName = String.valueOf(friendSpinner.getSelectedItem());
        String level = String.valueOf(levelSpinner.getSelectedItem());
        friendID = allUsersList.get(friendName);
        sendInvitation = new SendInvitation(invitationText.getText().toString().trim(), imageURL, level, friendID, friendName, senderPresent.getText().toString().trim(), false);
        checkNotEmptyInformation(sendInvitation);
    }

    @Override
    public void get(UsersNameID usersNameID) {
        this.usersNameID = usersNameID;
        HashMap<String, String> usersList = new HashMap<>();
        usersList.put(usersNameID.getUserName(), usersNameID.getUserID());
        for (int index = 0; index < usersList.size(); index++) {
            list.add(usersNameID.getUserName());
            allUsersList.put(usersNameID.getUserName(), usersNameID.getUserID());
        }
    }
}
