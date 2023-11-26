package com.cgvsu.protocurvefxapp;

import java.util.ArrayList;
import java.util.List;

public class SplineRealization {
    List<CubicSpline> splines = new ArrayList<>();
    public void searchCoeffSplines(double[] ti, double[] fi, int n){
        splines.clear();
        for (int i = 0; i < n - 1; i++){
            CubicSpline spline = new CubicSpline(ti[i], fi[i]);
            splines.add(spline);
        }
        splines.get(0).setC(0.0);
        splines.get(n).setC(0.0);

    }

    private void searchCi(double[] ti, double[] fi, int n){
        for
    }
}
