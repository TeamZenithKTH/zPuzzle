package com.teamzenith.game.zpuzzle.dbhandler;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Random;

/**
 * Created by alaaalkassar on 4/5/17.
 */

public class UploadToDatabase {

    private FirebaseStorage storage;
    private String userID;
    private String downloadUrl;
    private GetImageURL getImageURL;
    private UpdateUserImage updateUserImage;
    private ImageView uploadImageView;
    private SendInvitationToUser sendInvitationToUser;

    public void upload(String userID) {
        this.userID = userID;
        uploadImageView.setDrawingCacheEnabled(true);
        uploadImageView.buildDrawingCache();
        Bitmap bitmap = uploadImageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 5, baos);
        byte[] data = baos.toByteArray();
        storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        // Create a reference to "mountains.jpg"
        StorageReference mountainsRef = storageRef.child(userID).child("user.jpg");

        UploadTask uploadTask = mountainsRef.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //taskSnapshot.getMetadata() ;//contains file metadata such as size, content-type, and download URL.
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                ;
                getImageURL.get(downloadUrl.toString());
            }
        });
    }

    public void uploadUserImage(String userID) {
        this.userID = userID;
        uploadImageView.setDrawingCacheEnabled(true);
        uploadImageView.buildDrawingCache();
        Bitmap bitmap = uploadImageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 5, baos);
        byte[] data = baos.toByteArray();
        storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        // Create a reference to "mountains.jpg"
        StorageReference mountainsRef = storageRef.child(userID).child("userImage.jpg");

        UploadTask uploadTask = mountainsRef.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //taskSnapshot.getMetadata() ;//contains file metadata such as size, content-type, and download URL.
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                ;
                updateUserImage.getImage(downloadUrl.toString());
            }
        });
    }

    public void uploadUserInvitationImage(String userID) {
        this.userID = userID;
        uploadImageView.setDrawingCacheEnabled(true);
        uploadImageView.buildDrawingCache();
        Bitmap bitmap = uploadImageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 5, baos);
        byte[] data = baos.toByteArray();
        storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        Random rand = new Random();
        int index = rand.nextInt(10000);
        // Create a reference to "mountains.jpg"
        StorageReference mountainsRef = storageRef.child(userID).child(index + "invitationImage.jpg");

        UploadTask uploadTask = mountainsRef.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //taskSnapshot.getMetadata() ;//contains file metadata such as size, content-type, and download URL.
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                ;
                sendInvitationToUser.getInitationImage(downloadUrl.toString());
            }
        });
    }

    public void setListener(GetImageURL getImageURL, ImageView imageView) {
        this.getImageURL = getImageURL;
        this.uploadImageView = imageView;
    }


    public void setListenerUpdateImage(UpdateUserImage updateUserImage, ImageView userImageView) {
        this.updateUserImage = updateUserImage;
        this.uploadImageView = userImageView;
    }

    public void setListenerUserInvitationImage(SendInvitationToUser sendInvitationToUser, ImageView userImageView) {
        this.sendInvitationToUser = sendInvitationToUser;
        this.uploadImageView = userImageView;
    }
}
