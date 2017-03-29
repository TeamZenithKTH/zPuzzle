package com.teamzenith.game.zpuzzle.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.model.Level;


/**
 * Created by Krish on 28-03-2017.
 */

public class RandomImageAdapter  extends PagerAdapter {

    Context mContext;
    
    RandomImageAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return sliderImagesId.length;
    }

    private int[] sliderImagesId = new int[]{
            R.drawable.image1, R.drawable.image2,
            R.drawable.image3, R.drawable.image4,
            R.drawable.image5, R.drawable.image1,
            R.drawable.image2, R.drawable.image3,
            R.drawable.image4, R.drawable.image5
    };

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == ((ImageView) obj);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int i) {


        ImageView mImageView = new ImageView(mContext);
        mImageView.setImageResource(sliderImagesId[i]);
        ((ViewPager) container).addView(mImageView, 0);
         return mImageView;

    }

    @Override
    public void destroyItem(ViewGroup container, int i, Object obj) {
        ((ViewPager) container).removeView((ImageView) obj);
    }
}
