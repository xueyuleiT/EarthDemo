package com.example.zenghui.earthdemo;

/**
 * Created by zenghui on 15/11/30.
 */
public class Point {

    private int x, y, r,oX,oY;
    private int angle,speed;
    private boolean p1 = false,p2= false,p3= false,isBoard = false,draw = false;

    public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    public int getoX() {
        return oX;
    }

    public void setoX(int oX) {
        this.oX = oX;
    }

    public int getoY() {
        return oY;
    }

    public void setoY(int oY) {
        this.oY = oY;
    }

    public boolean isP1() {
        return p1;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public void setP1(boolean p1) {
        this.p1 = p1;
    }

    public boolean isP2() {
        return p2;
    }

    public void setP2(boolean p2) {
        this.p2 = p2;
    }

    public boolean isP3() {
        return p3;
    }

    public void setP3(boolean p3) {
        this.p3 = p3;
    }

    public boolean isBoard() {
        return isBoard;
    }

    public void setBoard(boolean board) {
        isBoard = board;
    }

    public Point(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    Point point1,point2,point3;

    public Point getPoint1() {
        return point1;
    }

    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }

    public Point getPoint3() {
        return point3;
    }

    public void setPoint3(Point point3) {
        this.point3 = point3;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Point() {
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }
}
