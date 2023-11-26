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
        searchCi(ti, fi, n);
        double hi, ci1, ci;
        for (int i = 0; i < n - 1; i++){
            hi = ti[i + 1] - ti[i];
            ci1 = splines.get(i + 1).getC();
            ci = splines.get(i).getC();
            splines.get(i).setB(((fi[i+1] - fi[i])/hi) - (hi/3)*(ci1 + 2*ci));
            splines.get(i).setD((ci1 - ci)/ 3*hi);
        }
        splines.get(n).setB(0.0);
        splines.get(n).setD(0.0);
    }

    private void searchCi(double[] ti, double[] fi, int n){
        double[] Vi = new double[n];
        double[] Ui = new double[n];
        Vi[0] = 0.0;
        Ui[0] = 0.0;
        double A, B, C, D, hi, hi1, del;

        for (int i = 1; i < n - 1; i++){
            //todo: проверить потом, что тут не так было у Нелли
            hi = ti[i] - ti[i - 1];
            hi1 = ti[i + 1] - ti[i];
            A = 2*(hi + hi1);
            B = hi1;
            C = hi;
            D = 3*(((fi[i + 1] - fi[i]) / hi1) - ((fi[i] - fi[i - 1])/ hi));
            del = C * Vi[i - 1] + A;
            Vi[i] = - (B / del);
            Ui[i] =  (D - C*Ui[i-1]) / del;
        }

        //todo: тут тоже чек нужен
        for (int i = n - 1; i > 0; i--){
            C = splines.get(i + 1).getC();
            splines.get(i).setC(Vi[i]*C + Ui[i]);
        }
    }

    public double realisationSpline(double ti, int i){
        CubicSpline s = splines.get(i);
        return s.getA() + s.getB()*(ti - s.getX()) + s.getC()*Math.pow(ti - s.getX(), 2) + s.getD()*Math.pow(ti - s.getX(), 3);
    }
}
