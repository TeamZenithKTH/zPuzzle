package com.teamzenith.game.zpuzzle.util;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.teamzenith.game.zpuzzle.controller.MainActivity;

/**
 * Created by alaaalkassar on 3/27/17.
 */

public class Logout extends AppCompatActivity {


    private boolean logoutStatus = false;

    public boolean loginMethodChecker() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        MainActivity mainActivity = new MainActivity();
        if (AccessToken.getCurrentAccessToken() != null) {

            /*Intent i = new Intent(mainActivity, );
            startActivity(i);*/
            return logoutStatus = true;
        } else {
            Toast.makeText(this, "No facebook login", Toast.LENGTH_SHORT).show();
            return logoutStatus;
        }
    }

}
