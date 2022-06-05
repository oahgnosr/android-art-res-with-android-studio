package com.example.c3;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class TestActivity extends AppCompatActivity implements View.OnClickListener,
        View.OnLongClickListener {

    private static final String TAG = "TestActivity";

    private static final int MESSAGE_SCROLL_TO = 1;
    private static final int FRAME_COUNT = 30;
    private static final int DELAYED_TIME = 33;

    private Button mButton1;
    private View mButton2;

    private int mCount = 0;

    private final Handler mHandler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        mButton1 = (Button) findViewById(R.id.button1);
        mButton1.setOnClickListener(this);
        mButton2 = (TextView) findViewById(R.id.button2);
        mButton2.setOnLongClickListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(TAG, "button1.left=" + mButton1.getLeft());
        Log.d(TAG, "button1.x=" + mButton1.getX());
    }

    @Override
    public void onClick(View v) {
        if (v == mButton1) {
            mCount = 0;

            // mButton1.setTranslationX(100);

            // 属性动画
//            Log.d(TAG, "button1.left=" + mButton1.getLeft());
//            Log.d(TAG, "button1.x=" + mButton1.getX());
//            ObjectAnimator.ofFloat(mButton1, "translationX", 0, 100).setDuration(1000).start();

            // 改变布局
//            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mButton1.getLayoutParams();
//            params.height += 100;
//            params.topMargin += 100;
//            mButton1.requestLayout();
//            mButton1.setLayoutParams(params);

            final int startX = 0;
            final int deltaX = 100;
            ValueAnimator animator = ValueAnimator.ofInt(0, 1).setDuration(1000);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    float fraction = animator.getAnimatedFraction();
                    mButton1.scrollTo(startX + (int) (deltaX * fraction), 0);
                }
            });
            animator.start();

            // 延时策略
//            mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO, DELAYED_TIME);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(this, "long click", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    private void handleMessage(Message msg) {
        switch (msg.what) {
            case MESSAGE_SCROLL_TO: {
                mCount++;
                if (mCount <= FRAME_COUNT) {
                    float fraction = mCount / (float) FRAME_COUNT;
                    //
                    int scrollX = (int) (fraction * 100);

                    // scrollTo() 只能改变 View 内容的位置，无法改变 View 在布局中的位置
                    mButton1.scrollTo(scrollX, 0);
                    mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO, DELAYED_TIME);
                }
                break;
            }

            default:
                break;
        }
    }

    private static class MyHandler extends Handler {
        private WeakReference<TestActivity> weakReference;

        MyHandler(TestActivity testActivity) {
            weakReference = new WeakReference<>(testActivity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            TestActivity testActivity = weakReference.get();
            if (testActivity != null) {
                testActivity.handleMessage(msg);
            }
        }
    }
}
