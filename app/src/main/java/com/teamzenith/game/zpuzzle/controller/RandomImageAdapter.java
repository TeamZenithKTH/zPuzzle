package com.teamzenith.game.zpuzzle.controller;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.teamzenith.game.zpuzzle.R;


/**
 * Created by Krish on 28-03-2017.
 */


public class RandomImageAdapter extends PagerAdapter {
    Context mContext;
    private int[] sliderImagesId = new int[]{
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5
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

    /**
     *
     * @param container
     * @param i
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int i) {
        ImageView mImageView = new ImageView(mContext);
        mImageView.setImageResource(sliderImagesId[i]);
        mImageView.setTag(sliderImagesId[i]);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    /**
     * this method is called once the user pick an image
     * @param prepareForClick
     */
    public void setOnPrepareListener(PrepareForClick prepareForClick) {
        this.prepareForClick = prepareForClick;
    }
}

