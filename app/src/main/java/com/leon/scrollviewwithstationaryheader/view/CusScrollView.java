package com.leon.scrollviewwithstationaryheader.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by leon on 9/28/16.
 */
public class CusScrollView extends ScrollView {
    private boolean ableOfFling = true;
    private OnCusScrollListener onCusScrollListener;
    public int downY;

    public boolean isAbleOfFling() {
        return ableOfFling;
    }

    public void setFlingListener(OnCusScrollListener onCusScrollListener) {
        this.onCusScrollListener = onCusScrollListener;
    }

    public void setAbleOfFling(boolean ableOfFling) {
        this.ableOfFling = ableOfFling;
    }


    public interface OnCusScrollListener {
        boolean flingFinished();

        void onScrollChange(int l, int t, int oldl, int oldt);
    }

    public CusScrollView(Context context) {
        super(context);
    }

    public CusScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CusScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void fling(int velocityY) {
        if (isAbleOfFling()&&onCusScrollListener!=null) {
            onCusScrollListener.flingFinished();
            super.fling(velocityY);
        }
    }


    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onCusScrollListener != null) {
            onCusScrollListener.onScrollChange(l, t, oldl, oldt);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        return true;
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            Log.d("lianglei", "aaaa--action-DOWN--parent:" + ev.getRawY());
            downY = (int) ev.getRawY();
//            return true;
        }
//        return false;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            Log.d("lianglei", "aaaa--down:" + ev.getRawY());
            return false;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//        Log.d("lianglei", "aaaa--request--parent");
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    public boolean performClick() {
//        Log.d("lianglei","aaaa-click-parent");
        return super.performClick();
    }
}
