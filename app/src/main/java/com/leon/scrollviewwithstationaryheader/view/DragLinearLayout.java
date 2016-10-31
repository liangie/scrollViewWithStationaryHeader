package com.leon.scrollviewwithstationaryheader.view;

/**
 * Created by leon on 10/27/16.
 */

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by leon on 6/3/16.
 */
public class DragLinearLayout extends LinearLayout {
    private ViewDragHelper mViewDragHelper;
    private DragCallback mCallback;

    public DragLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    public void setmCallback(DragCallback mCallback) {
        this.mCallback = mCallback;
    }

    private void initLayout() {
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);
            }

            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                int fixedLeft;
                View parent = (View) child.getParent();
                int boundLeft = parent.getPaddingLeft();
                int boundRight = parent.getWidth() - child.getWidth() - parent.getPaddingRight();
                if (left < boundLeft) {
                    fixedLeft = boundLeft;
                } else if (left > boundRight) {
                    fixedLeft = boundRight;
                } else {
                    fixedLeft = left;
                }
                return fixedLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                int fixedTop;

                View parent = (View) child.getParent();
                int boundTop = parent.getPaddingTop();
                int boundBottom = parent.getHeight() - child.getHeight() - parent.getPaddingBottom();

                if (child instanceof ImageView) {
                    Log.d("lianglei", "imageview---");
                    return boundBottom;
                }

                if (top < boundTop) {
                    fixedTop = boundTop;
                } else if (top > boundBottom) {
                    fixedTop = boundBottom;
                } else {
                    fixedTop = top;
                }
                return fixedTop;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    public interface DragCallback{
        float updateX();
        int getDirection();
    }

    public void setmViewDragHelper(ViewDragHelper helper){
        this.mViewDragHelper = helper;
    }
}
