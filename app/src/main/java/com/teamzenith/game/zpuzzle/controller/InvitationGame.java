package com.teamzenith.game.zpuzzle.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.model.Level;
import com.teamzenith.game.zpuzzle.model.SendInvitation;
import com.teamzenith.game.zpuzzle.model.User;
import com.teamzenith.game.zpuzzle.util.GetCurrentStatus;
import com.teamzenith.game.zpuzzle.util.ImageSplit;
import com.teamzenith.game.zpuzzle.util.ImagesIDs;
import com.teamzenith.game.zpuzzle.util.MoveImage;
import com.teamzenith.game.zpuzzle.util.ShufflingImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

/**
 * Created by Hichem Memmi on 4/11/17.
 */

/**
 * this class is the game play for those users will get an invitation from other users
 */
public class InvitationGame extends AppCompatActivity {
    private SendInvitation sendInvitation;
    private String invitationLetter, presentLetter, senderName, imageURL;
    private boolean status;
    private final MoveImage moveImage = new MoveImage();
    private boolean isFinish = false;
    private ArrayList<Integer> images;
    private TextView textView;
    private ImageView photoButton;
    private TableLayout ll;
    private TableRow tableRow;
    private Level level;
    private Bitmap[] bmp;
    private Bitmap[] tmpbmp;
    private HashMap<Integer, Bitmap> SHMap = new HashMap<Integer, Bitmap>();
    private ImagesIDs imagesIDs = new ImagesIDs();
    private HashMap<Integer, Integer> imagesIDList;
    private ImageSplit imageSplit = new ImageSplit();
    private HashMap<Integer, Bitmap> newMoveedImagesList;
    private ImageButton[] imageButtons;
    private ImageButton im;
    private int countMovement = 0;
    private float scale;
    private TextView currentMovement;
    private Timer T;
    private TextView timerCounter;
    private Bitmap imageToSend;
    private int count = 0;
    private File imageFile = null;
    private Drawable d;
    private int idOfDrawable = 0;
    private User player;
    private static int GALERI_RESULT = 1;
    private RandomImageAdapter adapterView = null;
    private int current;
    private ViewPager viewPager;
    private int minute = 0;
    private int hour = 0;
    private int seconds = 0;
    private String next;
    private String timer;
    private Target saveFileTarget;
    private int row;
    private int column;


    /**
     * this method will be called once the user launch this activity, which means when the user
     * try to solve a puzzle send from other user
     * @param bundle
     */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_invitation_game);
        scale = getApplicationContext().getResources().getDisplayMetrics().density;
        Intent intent = getIntent();

        SendInvitation selectedFromList = (SendInvitation) intent.getSerializableExtra("selectedFromList");
        player = (User) intent.getSerializableExtra("player");

        try {
            InvitationGame(selectedFromList);
            prepareAnImage();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method is to fill class fields
     * @param sendInvitation
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void InvitationGame(SendInvitation sendInvitation) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        this.sendInvitation = sendInvitation;
        this.invitationLetter = sendInvitation.getIntiationText();
        this.presentLetter = sendInvitation.getPresentText();
        this.senderName = sendInvitation.getUserID();
        this.imageURL = sendInvitation.getImageURL();
        this.status = sendInvitation.isStatus();
        String level = sendInvitation.getLevel();
        Class cls = Class.forName(Level.class.getPackage().getName() + "." + level);
        this.level = (Level) cls.newInstance();
        row = this.level.getSizeOfRow();
        column = this.level.getSizeOfColumn();
    }

    /**
     * this method is to get an image from an url
     */
    private void prepareAnImage() {

        Picasso.with(this)
                .load(this.imageURL)
                .into(new Target(){
                          @Override
                          public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                              FileOutputStream out = null;
                              try {

                                  Bitmap photo;
                                  out = new FileOutputStream(Environment.getExternalStorageDirectory()+"/image.png");
                                //  bitmap.compress(Bitmap.CompressFormat.PNG, 75, out); // bmp is your Bitmap instance
                                  // PNG is a lossless format, the compression factor (100) is ignored

                                  photo = createBitmap(bitmap);
                                  try {
                                      bmp = imageSplit.get(photo, row, column);
                                  } catch (FileNotFoundException e) {
                                      e.printStackTrace();
                                  }

                                  ShufflingImage shufflingImage = new ShufflingImage();
                                  tmpbmp = shufflingImage.shuffle(bmp);
                                  SHMap = shufflingImage.getShuffledOrder();
                                  settingImages(SHMap);
                                  createImageViews(SHMap);


                              } catch (Exception e) {
                                  e.printStackTrace();
                              } finally {
                                  try {
                                      if (out != null) {
                                          out.close();
                                      }
                                  } catch (IOException e) {
                                      e.printStackTrace();
                                  }
                              }
                          }

                          @Override
                          public void onBitmapFailed(Drawable errorDrawable) {

                          }

                          @Override
                          public void onPrepareLoad(Drawable placeHolderDrawable) {

                          }
                      });

    }


    /**
     * this method is to scale a bitmap image
     * @param imgFile1
     * @return
     */
    private Bitmap createBitmap(Bitmap imgFile1) {
        return Bitmap.createScaledBitmap(imgFile1, (int) (level.getSize() * scale), (int) (level.getSize() * scale), true);
    }

    /**
     * this method is to create dynamically tableLayout and imageButtons
     * @param SHMap
     */
    private void settingImages(HashMap<Integer, Bitmap> SHMap) {
        this.SHMap = SHMap;
        imageButtons = new ImageButton[row * column];
        int j = 0;
        ll = (TableLayout) findViewById(R.id.table);
        tableRow = new TableRow(this);
        for (int i = 0; i < row * column; i++) {
            if (j == ((row * column) / row) - 1) {
                im = new ImageButton(this);
                im.setId(i);
                im.setImageBitmap(tmpbmp[i]);
                imagesIDs.setposition(im.getId(), i);
                TableRow.LayoutParams params = new TableRow.LayoutParams();
                params.width = (int) (level.getSizeOfPiece() * scale);
                params.height = (int) (level.getSizeOfPiece() * scale);
                im.setLayoutParams(params);
                tableRow.addView(im, params);
                ll.addView(tableRow);
                tableRow = new TableRow(this);
                imageButtons[i] = im;
                j = 0;
            } else {
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                tableRow.setLayoutParams(lp);
                im = new ImageButton(this);
                im.setId(i);
                imagesIDs.setposition(im.getId(), i);
                im.setImageBitmap(tmpbmp[i]);
                TableRow.LayoutParams params = new TableRow.LayoutParams();
                params.setMargins(1, 1, 1, 1);
                params.width = (int) (level.getSizeOfPiece() * scale);
                params.height = (int) (level.getSizeOfPiece() * scale);
                im.setLayoutParams(params);
                tableRow.addView(im, params);
                imageButtons[i] = im;
                j++;
            }
        }
        imagesIDList = imagesIDs.getposition();
    }

    /**
     * this method is to allow the user to move pieces
     * @param SHMap
     */
    private void createImageViews(final HashMap<Integer, Bitmap> SHMap) {
        this.SHMap = SHMap;
        final GetCurrentStatus getCurrentStatus = new GetCurrentStatus();
        for (int i = 0; i < imageButtons.length; i++) {
            imageButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageButton b = (ImageButton) v;
                    newMoveedImagesList = moveImage.step(SHMap, b.getId(), row, column);
                    setNewImages(newMoveedImagesList);
                    isFinish = getCurrentStatus.checkCurrentImage(imageSplit.getOriginalDividedImage(), newMoveedImagesList);
                    if (isFinish) {
                        SetOriginalImagesToMatrix();
                        isFinish = false;
                    }
                }
            });
        }

    }

    /**
     * this method is to fill imageButtons when the user move a piece
     * @param SHMap
     */
    private void setNewImages(HashMap<Integer, Bitmap> SHMap) {
        this.SHMap = SHMap;
        for (int i = 0; i < SHMap.size(); i++) {
            ImageButton im = (ImageButton) findViewById(i);
            im.setImageBitmap(SHMap.get(i));
        }
    }


    /**
     * this method is to check if the puzzle is solved
     */
    private void SetOriginalImagesToMatrix() {
        Bitmap[] tmpBitMap;
        tmpBitMap = imageSplit.getOriginalDividedImage();
        for (int i = 0; i < tmpBitMap.length; i++) {
            ImageButton im = (ImageButton) findViewById(i);
            im.setImageBitmap(tmpBitMap[i]);
        }
        Intent intent = new Intent(this, AfterInvitationGameActivity.class);
        intent.putExtra("player",player);
        intent.putExtra("sendInvitation",sendInvitation);

        startActivity(intent);
    }


}
