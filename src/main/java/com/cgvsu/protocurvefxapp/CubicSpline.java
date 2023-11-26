package com.cgvsu.protocurvefxapp;

public class CubicSpline {
    private double x;
    private double a;
    private double b;
    private double c;
    private double d;

    public CubicSpline(double x, double a, double b, double c, double d) {
        this.x = x;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public CubicSpline(double x, double a) {
        this.x = x;
        this.a = a;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }
}
