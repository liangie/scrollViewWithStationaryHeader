package com.leon.scrollviewwithstationaryheader;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by leon on 10/28/16.
 */
public class CusOnPageChangeListener implements ViewPager.OnPageChangeListener {
    private float offset;
    private int direction;// 1: 向左   2: 向右


    private float mIndicatorWidth;
    private int indicatorCount;
    private View mIndicator;
    private LinearLayout.LayoutParams mParams;

    public CusOnPageChangeListener(View mIndicator) {
        this.mIndicator = mIndicator;
        mParams = (LinearLayout.LayoutParams) mIndicator.getLayoutParams();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //indicatorView向右(手指向左划): offset:0f -> 1.0f   offsetPixels:0 -> screenWith
        //indicatorView向左(手指向右划): offset:1.0f -> 0f   offsetPixels:screenWidth -> 0

        //当offset==0时,表示滑动开始,给offset赋初值并直接返回
        if (offset == 0) {
            offset = positionOffset;
            return;
        }

        float x = 0;
        //根据offset的变化规律(0--1增加 或者 1-->0减小),来判断
        if (positionOffset > 0 && positionOffset < 1) {
            if (offset - positionOffset < 0) {
                if (direction != 2) {
                    direction = 2;
//                            Log.d("lianglei","<------向右");
                }
                x = position * mIndicatorWidth + (positionOffset * mIndicatorWidth);
//                        Log.d("lianglei", "<----向右" + offset + ":" + positionOffset);
            } else if (offset - positionOffset > 0) {
                if (direction != 1) {
                    direction = 1;
//                            Log.d("lianglei","向左------>");
                }
                x = position * mIndicatorWidth + (positionOffset * mIndicatorWidth);
//                        Log.d("lianglei","position:"+position);
//                        Log.d("lianglei", offset + ":" + positionOffset + "向左---->");
            }
            offset = positionOffset;
//            if(position==--indicatorCount){
//                x = mIndicatorWidth*indicatorCount;
//            }
            if(x>1) {
                double d = x;
                x = (int) Math.ceil(d);
            }
            mParams.leftMargin = (int)x;
            Log.d("lianglei", indicatorCount + "--width--------------:" + mIndicatorWidth * indicatorCount + "; x:" + x);
            mIndicator.setLayoutParams(mParams);
        }

        //当两个值都为0时,表示滑动动画执行完毕,将offset重置为0;
        if (positionOffset == 0 && positionOffsetPixels == 0) {
            offset = 0;
        }
//                Log.d("lianglei","position:"+position+";  offset:"+positionOffset+";  pixels:"+positionOffsetPixels);

    }

    /**
     * 手指离开屏幕时viewpager将要处于的position,及手离开屏幕执行动画完成后将处于的position
     *
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
    }

    /**
     * @param state==0 什么都没做; ==1 正在滑动;  ==2滑动停止
     */
    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public void setmIndicator(View mIndicator) {
        this.mIndicator = mIndicator;
    }

    public void setmIndicatorWidth(int mIndicatorWidth) {
        this.mIndicatorWidth = mIndicatorWidth;
    }

    public void setIndicatorCount(int indicatorCount) {
        this.indicatorCount = indicatorCount;
    }
}
