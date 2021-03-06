package com.example.c3.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;

public class TestButton extends AppCompatTextView {
    private static final String TAG = "TestButton";

    // TouchSlop 是系统能识别出的滑动的最小距离。滑动的距离小于这个值，则不认为是滑动。
    private int mScaledTouchSlop;
    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;

    public TestButton(Context context) {
        this(context, null);
    }

    public TestButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mScaledTouchSlop = ViewConfiguration.get(getContext())
                .getScaledTouchSlop();
        Log.d(TAG, "sts:" + mScaledTouchSlop);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取手指当前的坐标
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                Log.d(TAG, "move, deltaX:" + deltaX + " deltaY:" + deltaY);

                // 通过动画的方式改变 view 的位置
//                int translationX = (int) getTranslationX() + deltaX;
//                int translationY = (int) getTranslationY() + deltaY;
//                setTranslationX(translationX);
//                setTranslationY(translationY);

                // 通过改变布局方参数的方式
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
                layoutParams.leftMargin += deltaX;
                layoutParams.topMargin += deltaY;
                setLayoutParams(layoutParams);

                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
            default:
                break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }

}
