package com.example.zenghui.earthdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * Created by zenghui on 2016/7/22.
 */
public class EarthView extends View{

    List<Point> points = new ArrayList<>();
    int rotate = 0;
    Timer timer = new Timer();
    Paint paint = new Paint();
    Paint mPaint = new Paint();
    Paint bgPaint = new Paint();
    Paint linePaint = new Paint();
    Paint lgPaint = new Paint();
    boolean isDraw = false;//防止两个线程操作points
    void mRun(){
        if (isDraw){
            return;
        }
        int size = points.size()-1;
        for (int i = 0; i < size;i++){
            Point point = points.get(i);
            if (point.isBoard()){
                point.setPoint3(null);
            }else {
                point.setPoint1(null);
                point.setPoint2(null);
                point.setPoint3(null);
            }
                point.setDraw(false);
        }

        for (int i = 0; i < size;i++){
            movePoint(points.get(i));
        }
        for (int i = 0; i < size;i++){
            findRelativePoint(points.get(i));
        }
        isDraw = true;
        handler.sendEmptyMessage(0);
    }

    public EarthView(Context context) {
        super(context);
        init();
    }

    public EarthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EarthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init(){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mRun();
            }
        };
        timer.schedule(timerTask,10,50);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        linePaint.setStrokeWidth(0.3f);
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.BLUE);

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(0.3f);
        mPaint.setColor(Color.BLUE);

        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.BLACK);
        bgPaint.setAlpha(200);

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            rotate+=2;
            if (rotate >= 360){
                rotate = rotate%360;
            }
            postInvalidate();
        }
    };
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (lgPaint != null)
            canvas.drawCircle(w/2,w/2,r,lgPaint);
        canvas.rotate(rotate,w/2,w/2);
        int size = points.size()-1;
        for (int i = 0; i < size;i++){
            Point mPoint = points.get(i);
            if (mPoint.getPoint1() != null &&  !mPoint.getPoint1().isDraw() ) {
                canvas.drawLine(mPoint.getX(), mPoint.getY(), mPoint.getPoint1().getX(), mPoint.getPoint1().getY(), linePaint);
            }
            if (mPoint.getPoint2() != null &&  !mPoint.getPoint2().isDraw()) {
                canvas.drawLine(mPoint.getX(), mPoint.getY(), mPoint.getPoint2().getX(), mPoint.getPoint2().getY(), linePaint);
            }
            if (mPoint.getPoint3() != null &&  !mPoint.getPoint3().isDraw()) {
                canvas.drawLine(mPoint.getX(), mPoint.getY(), mPoint.getPoint3().getX(), mPoint.getPoint3().getY(), linePaint);
            }
            mPoint.setDraw(true);
            if (!mPoint.isBoard())
                canvas.drawCircle(mPoint.getX(),mPoint.getY(),mPoint.getR(),paint);
        }
        canvas.drawCircle(w/2,w/2,r,mPaint);
        isDraw = false;

    }

    int w,h,r;
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (bottom - top > 0 && points.size() == 0){
            w = getWidth();
            h = getHeight();
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.height = w;
            setLayoutParams(layoutParams);
            h = w;
            r = w/2-cr;
            mr = r-cr;
            int x = 0,y = 0;
            RadialGradient lg = new RadialGradient(w/2,w/2,w/2,Color.argb(100,0,0,0),Color.argb(150,0,0,0), Shader.TileMode.MIRROR);
            lgPaint = new Paint();
            lgPaint.setShader(lg);
            for (int i = 0; i < 100;i++){
                x = random.nextInt(w);
                y = random.nextInt(h);
                while (Math.pow(x-r,2)+Math.pow(y-r,2) >= r*r){
                    x = random.nextInt(w);
                    y = random.nextInt(h);
                }
                Point point = new Point();
                point.setX(x);
                point.setY(y);
                point.setoX(x);
                point.setoY(y);
                point.setAngle(random.nextInt(360));
                point.setR(random.nextInt(cr)+2);
                point.setSpeed(cr/2);
                points.add(point);
            }

            for (int i = 0; i <11 ;i++){
                Point point = new Point();
                int angle = i*36+5;
                int mx = 0,my = 0;
//                if (angle>=90 & angle < 180) {// 第二区
//                    mx += r * Math.cos(Math.toRadians(angle));
//                    my -= r * Math.sin(Math.toRadians(angle));
//                } else if (angle>=180 & angle < 270) {// 第三区
//                    mx -= r * Math.cos(Math.toRadians(angle));
//                    my += r * Math.sin(Math.toRadians(angle));
//
//                } else if (angle>=270) {// 第四区
//                    mx += r * Math.cos(Math.toRadians(angle));
//                    my -= r * Math.sin(Math.toRadians(angle));
//
//                } else if (angle>=0 & angle < 90) {// 第一区
//                    mx -= r * Math.cos(Math.toRadians(angle));
//                    my += r * Math.sin(Math.toRadians(angle));
//                }
                if (angle>=90 & angle < 180) {// 第二区
                    mx += r * Math.cos(Math.toRadians(angle));
                    my -= r * Math.sin(Math.toRadians(angle));
                } else if (angle>=180 & angle < 270) {// 第三区
                    mx += r * Math.cos(Math.toRadians(angle));
                    my -= r * Math.sin(Math.toRadians(angle));

                } else if (angle>=270) {// 第四区
                    mx += r * Math.cos(Math.toRadians(angle));
                    my -= r * Math.sin(Math.toRadians(angle));

                } else if (angle>=0 & angle < 90) {// 第一区
                    mx += r * Math.cos(Math.toRadians(angle));
                    my -= r * Math.sin(Math.toRadians(angle));
                }

                point.setBoard(true);
                point.setX(w/2+mx);
                point.setY(w/2+my);
                points.add(point);
            }
            for (int i = 100; i < 110; i++){
                Point point = points.get(i);

                point.setPoint1(points.get(i+1));
                points.get(i+1).setPoint2(point);
            }
            int size = points.size() -1;
            for (int i = 0; i < size;i++){
                findRelativePoint(points.get(i));
            }
        }
    }

    Random random = new Random();
    int cr = getResources().getDimensionPixelSize(R.dimen.public_space_value_2);
    int mr;
    void movePoint(Point point){

        if (point.isBoard()){
            return;
        }
        int angle = point.getAngle();
        int x = 0;
        int y = 0;
        int mcr = point.getSpeed();
//        if (angle>=90 & angle < 180) {// 第二区
//            x -= mcr * Math.cos(Math.toRadians(angle));
//            y -= mcr * Math.sin(Math.toRadians(angle));
//        } else if (angle>=180 & angle < 270) {// 第三区
//            x += mcr * Math.cos(Math.toRadians(angle));
//            y -= mcr * Math.sin(Math.toRadians(angle));
//
//        } else if (angle>=270) {// 第四区
//            x += mcr * Math.cos(Math.toRadians(angle));
//            y += mcr * Math.sin(Math.toRadians(angle));
//
//        } else if (angle>=0 & angle < 90) {// 第一区
//            x -= mcr * Math.cos(Math.toRadians(angle));
//            y += mcr * Math.sin(Math.toRadians(angle));
//        }

        if (angle>=90 & angle < 180) {// 第二区
            x += mcr * Math.cos(Math.toRadians(angle));
            y -= mcr * Math.sin(Math.toRadians(angle));
        } else if (angle>=180 & angle < 270) {// 第三区
            x += mcr * Math.cos(Math.toRadians(angle));
            y -= mcr * Math.sin(Math.toRadians(angle));

        } else if (angle>=270) {// 第四区
            x += mcr * Math.cos(Math.toRadians(angle));
            y -= mcr * Math.sin(Math.toRadians(angle));

        } else if (angle>=0 & angle < 90) {// 第一区
            x += mcr * Math.cos(Math.toRadians(angle));
            y -= mcr * Math.sin(Math.toRadians(angle));
        }

        while (Math.pow(point.getX()+x-point.getoX(),2)+Math.pow(point.getY()+y-point.getoY(),2) >= (r/2)*(r/2) || Math.pow(point.getX()+x-r,2)+Math.pow(point.getY()+y-r,2) >= mr*mr){
             angle = random.nextInt(360);
            if (angle>=90 & angle < 180) {// 第二区
                x -= mcr * Math.cos(Math.toRadians(angle));
                y -= mcr * Math.sin(Math.toRadians(angle));
            } else if (angle>=180 & angle < 270) {// 第三区
                x += mcr * Math.cos(Math.toRadians(angle));
                y -= mcr * Math.sin(Math.toRadians(angle));

            } else if (angle>=270) {// 第四区
                x += mcr * Math.cos(Math.toRadians(angle));
                y += mcr * Math.sin(Math.toRadians(angle));

            } else if (angle>=0 & angle < 90) {// 第一区
                x -= mcr * Math.cos(Math.toRadians(angle));
                y += mcr * Math.sin(Math.toRadians(angle));
            }
        }
        point.setAngle(angle);
        point.setX(point.getX()+x);
        point.setY(point.getY()+y);
    }

    void findRelativePoint(Point point){
        if (point.getPoint1() == null){
            int size = points.size();
            int min = Integer.MAX_VALUE;
            int index = -1;
            for (int i = 0; i <size ; i++){
                Point mPoint = points.get(i);
                      if (mPoint == point || mPoint == point.getPoint2()|| mPoint == point.getPoint3()){
                          continue;
                      }else if (mPoint.getPoint1() != null && mPoint.getPoint2() != null && mPoint.getPoint3() != null){
                          continue;
                      }else {
                          int margin = (int) (Math.pow(mPoint.getX() - point.getX(),2) + Math.pow(mPoint.getY() - point.getY(),2));
                          if (margin < min && margin < r*r){
                              index = i;
                              min = margin;
                          }
                      }
            }
            if (index == -1){
                return;
            }
            point.setPoint1(points.get(index));
            if (points.get(index).getPoint1() == null){
                points.get(index).setPoint1(point);
            }else if (points.get(index).getPoint2() == null){
                points.get(index).setPoint2(point);
            }else if (points.get(index).getPoint3() == null){
                points.get(index).setPoint3(point);
            }
            findRelativePoint(point);
        }else if(point.getPoint2() == null){
            int size = points.size();
            int min = Integer.MAX_VALUE;
            int index = -1;
            for (int i = 0; i <size ; i++){
                Point mPoint = points.get(i);
                if (mPoint == point || mPoint == point.getPoint1() || mPoint == point.getPoint3()){
                    continue;
                }else if (mPoint.getPoint1() != null && mPoint.getPoint2() != null && mPoint.getPoint3() != null){
                    continue;
                }else {
                    int margin = (int) (Math.pow(mPoint.getX() - point.getX(),2) + Math.pow(mPoint.getY() - point.getY(),2));
                    if (margin < min && margin < r*r){
                        index = i;
                        min = margin;
                    }
                }
            }
            if (index == -1){
                return;
            }
            point.setPoint2(points.get(index));
            if (points.get(index).getPoint1() == null){
                points.get(index).setPoint1(point);
            }else if (points.get(index).getPoint2() == null){
                points.get(index).setPoint2(point);
            }else if (points.get(index).getPoint3() == null){
                points.get(index).setPoint3(point);
            }
            findRelativePoint(point);
        }else if(point.getPoint3() == null){
            int size = points.size();
            int min = Integer.MAX_VALUE;
            int index = -1;
            for (int i = 0; i <size ; i++){
                Point mPoint = points.get(i);
                if (mPoint == point || mPoint == point.getPoint1() || mPoint == point.getPoint2()){
                    continue;
                }else if (mPoint.getPoint1() != null && mPoint.getPoint2() != null && mPoint.getPoint3() != null){
                    continue;
                }else {
                    int margin = (int) (Math.pow(mPoint.getX() - point.getX(),2) + Math.pow(mPoint.getY() - point.getY(),2));
                    if (margin < min && margin < r*r){
                        index = i;
                        min = margin;
                    }
                }
            }
            if (index == -1){
                return;
            }
            point.setPoint3(points.get(index));
            if (points.get(index).getPoint1() == null){
                points.get(index).setPoint1(point);
            }else if (points.get(index).getPoint2() == null){
                points.get(index).setPoint2(point);
            }else if (points.get(index).getPoint3() == null){
                points.get(index).setPoint3(point);
            }
        }
    }
}
