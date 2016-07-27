package com.example.zenghui.earthdemo;

import android.animation.Animator;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    View after, before;
    ObjectAnimator afterAnimator,beforeAnimator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        before = findViewById(R.id.before);
        after = findViewById(R.id.after);

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (before.getVisibility() == View.VISIBLE) {
                    beforeAnimator = ObjectAnimator.ofObject(before, "alpha", new FloatEvaluator(), 1, 0);
                    afterAnimator = ObjectAnimator.ofObject(after, "alpha", new FloatEvaluator(), 0, 1);
                }else {
                    beforeAnimator = ObjectAnimator.ofObject(before, "alpha", new FloatEvaluator(), 0,1);
                    afterAnimator = ObjectAnimator.ofObject(after, "alpha", new FloatEvaluator(), 1, 0);
                }
                beforeAnimator.setDuration(3000);
                afterAnimator.setDuration(3000);
                beforeAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                afterAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

                if (before.getAlpha() == 0) {
                    before.setVisibility(View.VISIBLE);
                }
                beforeAnimator.start();
                beforeAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (before.getAlpha() == 0) {
                            before.setVisibility(View.GONE);
                        }else {
                            before.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        beforeAnimator.removeAllListeners();
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                if (after.getAlpha() == 0) {
                    after.setVisibility(View.VISIBLE);
                }
                afterAnimator.start();
                afterAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (after.getAlpha() == 0) {
                            after.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        afterAnimator.removeAllListeners();
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
        };

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);

            }
        };
        timer.schedule(timerTask,10,6000);

    }
    Timer timer;
    TimerTask timerTask;
    @Override
    protected void onDestroy() {

        timerTask.cancel();
        timer.cancel();

        if (afterAnimator != null){
            afterAnimator.cancel();
            afterAnimator.removeAllListeners();
        }

        if (beforeAnimator != null){
            beforeAnimator.cancel();
            beforeAnimator.removeAllListeners();
        }

        super.onDestroy();
    }
}
