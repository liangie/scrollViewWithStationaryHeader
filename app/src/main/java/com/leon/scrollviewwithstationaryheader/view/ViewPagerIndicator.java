package com.leon.scrollviewwithstationaryheader.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.leon.scrollviewwithstationaryheader.R;

/**
 * Created by leon on 10/28/16.
 */
public class ViewPagerIndicator extends LinearLayout {
    private View mIndicator;
    private int mIndicatorCount;
    private int mIndicatorWidth;
    private IndicatorCallback mCallback;

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray atts = context.obtainStyledAttributes(attrs, R.styleable.pagerindicator);
        mIndicatorCount = atts.getInt(R.styleable.pagerindicator_indicator_num, 0);

    }

    public void setmCallback(IndicatorCallback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        if (getChildCount() != 1)
            throw new IllegalArgumentException("must have only one child");
        mIndicator = getChildAt(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = getMeasuredWidth();
        LayoutParams params = (LayoutParams) mIndicator.getLayoutParams();
        mIndicatorWidth = parentWidth / mIndicatorCount;
        params.width = mIndicatorWidth;
        mIndicator.setLayoutParams(params);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mCallback != null) {
            mCallback.gotIndicatorWith(getMeasuredWidth());
        }
    }

    public int getmIndicatorWidth() {
        return mIndicatorWidth;
    }

    public View getmIndicator() {
        return mIndicator;
    }

    public int getmIndicatorCount() {
        return mIndicatorCount;
    }

    public interface IndicatorCallback {
        void gotIndicatorWith(int width);
    }
}
