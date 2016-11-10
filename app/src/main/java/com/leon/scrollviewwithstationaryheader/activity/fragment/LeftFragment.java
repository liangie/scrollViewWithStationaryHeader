package com.leon.scrollviewwithstationaryheader.activity.fragment;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.leon.scrollviewwithstationaryheader.FunctionUtil;
import com.leon.scrollviewwithstationaryheader.R;
import com.leon.scrollviewwithstationaryheader.view.CusScrollView;

import java.lang.reflect.Field;

/**
 * Created by leon on 10/27/16.
 */
public class LeftFragment extends Fragment implements View.OnClickListener,CusScrollView.OnCusScrollListener {

    private View root;
    private View alphaView;
    private CusScrollView mScrollView;
    private TextView underTv;
    //trackpad的位置状态,true表示在底部,false表示在顶部
    private boolean isStand = true;
    //按下时trackpad的位置状态
    private boolean oldStatnd = true;
    //手指按下时的Y座标值
//    int downY;
    private int blankHeightDp = 300;
    private RelativeLayout.LayoutParams mParams;

    private boolean forbidScroll = true;
    int blankHeight;
    private Toast mToast;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (mScrollView.getScrollY() < FunctionUtil.dpToPx(getActivity(), blankHeightDp)) {
                        if (mScrollView.getScrollY() < FunctionUtil.dpToPx(getActivity(), blankHeightDp - 50)) {
                            isStand = false;
                            autoScrollAnimation(mScrollView.getScrollY(), 0);
                            oldStatnd = false;
                            break;
                        }
                        isStand = true;
                        autoScrollAnimation(mScrollView.getScrollY(), FunctionUtil.dpToPx(getActivity(), blankHeightDp));
                        oldStatnd = true;
                    }
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = inflater.inflate(R.layout.fragment_left, null);
        initView();
        return root;
    }

    private void initView() {
        alphaView = root.findViewById(R.id.alpha_view);
        mScrollView = (CusScrollView) root.findViewById(R.id.scroll_view);
        underTv = (TextView) root.findViewById(R.id.under_tv);
//        underTv.setOnClickListener(this);
        mScrollView.setFlingListener(this);
        mParams = (RelativeLayout.LayoutParams) mScrollView.getLayoutParams();

        blankHeight = FunctionUtil.dpToPx(getActivity(), blankHeightDp);
        LinearLayout scrollContentHolder = (LinearLayout)root.findViewById(R.id.scroll_content_holder);
        Log.d("lianglei","params.topMargin:"+scrollContentHolder.getPaddingTop());
        blankHeight = scrollContentHolder.getPaddingTop();

        root.findViewById(R.id.under_tv).setOnClickListener(this);
        root.findViewById(R.id.card01).setOnClickListener(this);
        root.findViewById(R.id.card01).findViewById(R.id.turn_on_tv).setOnClickListener(this);
        root.findViewById(R.id.card02).setOnClickListener(this);
        scrollContentHolder.setOnClickListener(this);


        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        oldStatnd = isStand;
//                        downY = (int) event.getRawY();

                        return false;
                    case MotionEvent.ACTION_MOVE:
                        /*mScrollView.setAbleOfFling(false);
                        int rawY = (int) event.getRawY();
                        int downY = mScrollView.downY;
                        //spanY手指滑动距离,正数向下,负数向上
                        int spanY = rawY - downY;
                        if (!isStand) {
                            //cusScrollView的topMargin==0
                            if (spanY < 0) {
                                //topMargin==0时往上划,无操作
                                return false;
                            } else {
                                if (mScrollView.getScrollY() > 0) {
                                    //在cusScrollView的topMargin==0且手指向下划时,且scrollview没在顶部则响应scrollview内部滑动
                                    return false;
                                }
                                mParams.topMargin = spanY>FunctionUtils.dpToPx(getActivity(),200)
                                        ?FunctionUtils.dpToPx(getActivity(),200):spanY;
                            }
                        } else {
                            //cusScrollView的topMargin==MAX
                            if (spanY > 0) {
                                //topMargin==MAX时向下划,无操作
                                return false;
                            } else {
                                //topMargin==MAX时手指向上划
                                int newTopMargin = FunctionUtils.dpToPx(getActivity(), 200) + spanY;
                                newTopMargin=newTopMargin < 0 ? 0 : newTopMargin;
                                if (mParams.topMargin == newTopMargin&&newTopMargin==0) {
                                    return false;
                                }
                                mParams.topMargin = newTopMargin;
                            }
                        }

                        mScrollView.setLayoutParams(mParams);
                        return true;*/
                        return false;
                    case MotionEvent.ACTION_UP:
                        /*
                        * 当cusScrollView本身滑动时(即topMargin>0时),禁止cusScrollView内部滚动。
                        * 反之允许
                        * *//*
                        mScrollView.setAbleOfFling(mParams.topMargin > 0 ? false : true);
                        //disY,正数向下,负数向上
                        int disY = (int) event.getRawY() - mScrollView.downY;
                        if (Math.abs(disY) > 10) {
                            if (disY > 0) {
                                //向下的手势
                                if (mScrollView.getScrollY() <= 0) {
                                    //当cusScrollView在顶部时才响应cusScrollView本身的滑动
                                    isStand = true;
                                    autoScrollAnimation(mParams.topMargin, FunctionUtils.dpToPx(getActivity(), blankHeightDp));
                                    oldStatnd = isStand;
                                }
                            } else if (disY < 0) {
                                //向上手势
                                isStand = false;
                                autoScrollAnimation(mParams.topMargin, 0);
                                oldStatnd = false;
                            }
                        } else {

//                           if (disY > 0) {
//                                isStand = false;
//                                autoScrollAnimation(mParams.topMargin, FunctionUtils.dpToPx(getActivity(), blankHeightDp));
//                                oldStatnd = false;
//                            } else if (disY < 0) {
//                                isStand = true;
//                                autoScrollAnimation(mParams.topMargin, 0);
//                                oldStatnd = true;
//                            }
                        }*/


                        int currentScrollY = mScrollView.getScrollY();
                        mScrollView.setAbleOfFling(currentScrollY < blankHeight ? false : true);
                        int disY = mScrollView.downY - (int) event.getRawY();
                        if (Math.abs(disY) > 10) {
                            if (disY > 0) {
                                if (mScrollView.getScrollY() < FunctionUtil.dpToPx(getActivity(), blankHeightDp)) {
                                    isStand = true;
                                    autoScrollAnimation(mScrollView.getScrollY(), FunctionUtil.dpToPx(getActivity(), blankHeightDp));
                                }
                            } else if (disY < 0) {
                                if (mScrollView.getScrollY() < FunctionUtil.dpToPx(getActivity(), blankHeightDp)) {
                                    isStand = false;
                                    autoScrollAnimation(mScrollView.getScrollY(), 0);
                                }
                            }
                        } else {
                            if (disY > 0) {
                                isStand = false;
                                autoScrollAnimation(mScrollView.getScrollY(), 0);
                            } else if (disY < 0) {
                                isStand = true;
                                autoScrollAnimation(mScrollView.getScrollY(), FunctionUtil.dpToPx(getActivity(), blankHeightDp));
                            }
                        }
                        break;
                }
                return false;
            }


        });

    }

    @Override
    public boolean flingFinished() {
        isFlingFinished();
        return false;
    }

    @Override
    public void onScrollChange(int l, int t, int oldl, int oldt) {
        float alpha = t / (float) FunctionUtil.dpToPx(getActivity(), blankHeightDp);
        alphaView.setAlpha(alpha);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.under_tv:
                Toast("under_tv");
                break;
            case R.id.card01:
                Toast("card01");
                break;
            case R.id.turn_on_tv:
                Toast("readAll");
                break;
            case R.id.card02:
                break;
            case R.id.scroll_content_holder:

                break;
        }
    }

    private void Toast(String msg){
        if(mToast!=null) {
            mToast.cancel();
            mToast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
            mToast.show();
        }else{
            mToast = new Toast(getActivity());
        }
    }

    /**
     * scroll手指抬起后,满足条件的动画
     *
     * @param start
     * @param end
     */
    private void autoScrollAnimation(float start, float end) {
//        if (isStand == oldStatnd) {
//            return;
//        }
        ValueAnimator animator = ValueAnimator.ofFloat(start, end).setDuration(isStand ? 260 : 180);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mScrollView.scrollTo(0, (int) (float) animation.getAnimatedValue());
//                mParams.topMargin = (int) (float) animation.getAnimatedValue();
//                mScrollView.setLayoutParams(mParams);
            }
        });
        animator.start();
    }

    /**
     * 获得ScrollView惯性滑动动画是否停止
     */
    public void isFlingFinished() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class<?> scrollViewClass = null;
                    scrollViewClass = Class.forName(ScrollView.class.getName());
                    Field overScroller = scrollViewClass.getDeclaredField("mScroller");

                    overScroller.setAccessible(true);
                    OverScroller scroller = (OverScroller) overScroller.get(mScrollView);
                    if (scroller != null) {
                        while (!scroller.isFinished()) {
                            if (scroller.getFinalY() == mScrollView.getScrollY()
                                    && scroller.isFinished()) {
                                break;
                            }
                            Thread.sleep(100);
                        }
                    }
                    Message msg = new Message();
                    msg.what = 1;
                    msg.arg1 = mScrollView.getScrollY();
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
