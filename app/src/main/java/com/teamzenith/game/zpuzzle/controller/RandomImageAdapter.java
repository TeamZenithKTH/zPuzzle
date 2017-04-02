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

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Krish on 28-03-2017.
 */


public class RandomImageAdapter  extends PagerAdapter {

    Context mContext;


    private int[] sliderImagesId = new int[]{
            R.drawable.naruto, R.drawable.son_goku,
            R.drawable.one_piece, R.drawable.hatake_kakashi,
            R.drawable.son_goku_ssj,
            R.drawable.stockholm, R.drawable.stockholm_1,
            R.drawable.kth, R.drawable.gamla_stam,
            R.drawable.zlatan_ibrahimovic
    };
    private PrepareForClick prepareForClick;

    RandomImageAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return sliderImagesId.length;
    }

    public int[] getSliderImagesId() {
        return sliderImagesId;
    }

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == ((ImageView) obj);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int i) {


        ImageView mImageView = new ImageView(mContext);
        mImageView.setImageResource(sliderImagesId[i]);
        mImageView.setTag(sliderImagesId[i]);


        mImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                prepareForClick.setOnPrepare(v);

            }
        });
        ((ViewPager) container).addView(mImageView, 0);
         return mImageView;

    }

    @Override
    public void destroyItem(ViewGroup container, int i, Object obj) {
        ((ViewPager) container).removeView((ImageView) obj);
    }


    public void setOnPrepareListener(PrepareForClick prepareForClick) {

       this.prepareForClick=prepareForClick;

    }
}

