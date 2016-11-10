package com.leon.scrollviewwithstationaryheader;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.leon.scrollviewwithstationaryheader.view.ViewPagerIndicator;

/**
 * Created by leon on 10/28/16.
 */
public class CusViewPager extends ViewPager implements ViewPagerIndicator.IndicatorCallback {
    private ViewPagerIndicator mIndicatorLayout;
    private int mIndicatorWidth;
    private View mIndicator;


    private ViewPager.OnPageChangeListener mListener;

    public CusViewPager(Context context) {
        super(context);
    }

    public CusViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setmIndicatorLayout(ViewPagerIndicator mIndicatorLayout) {
        this.mIndicatorLayout = mIndicatorLayout;
        mIndicatorLayout.setmCallback(this);
        mIndicator = mIndicatorLayout.getmIndicator();
    }

    public void setmListener(OnPageChangeListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        if (listener instanceof CusOnPageChangeListener) {
            ((CusOnPageChangeListener) listener).setmIndicatorWidth(mIndicatorWidth);
            ((CusOnPageChangeListener) listener).setIndicatorCount(mIndicatorLayout.getmIndicatorCount());
            super.setOnPageChangeListener(listener);
        }
    }

    @Override
    public void gotIndicatorWith(int width) {
        mIndicatorWidth = mIndicatorLayout.getmIndicatorWidth();
        Log.d("lianglei","mIndicatorWidth:"+mIndicatorWidth);
        setOnPageChangeListener(mListener);
    }
}
