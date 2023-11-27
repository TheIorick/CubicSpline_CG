package com.cgvsu.protocurvefxapp;

import java.util.ArrayList;
import java.util.List;

public class SplineRealization {
    List<CubicSpline> splines = new ArrayList<>();

    public void searchCoeffSplines(double[] ti, double[] fi, int n) {
        splines.clear();
        for (int i = 0; i < n; i++) {
            CubicSpline spline = new CubicSpline(ti[i], fi[i]);
            splines.add(spline);
        }
        splines.get(0).setC(0.0);
        splines.get(n - 2).setC(0.0);
        searchCi(ti, fi, n);
        double hi, ci1, ci;
        for (int i = 0; i < n - 1; i++) {
            hi = ti[i + 1] - ti[i];
            ci1 = splines.get(i + 1).getC();
            ci = splines.get(i).getC();
            splines.get(i).setB(((fi[i + 1] - fi[i]) / hi) - (hi / 3) * (ci1 + 2 * ci));
            splines.get(i).setD((ci1 - ci) / (3 * hi));
        }
        splines.get(n - 2).setB(0.0);
        splines.get(n - 2).setD(0.0);
    }

    public void searchCi(double[] ti, double[] fi, int n) {
        double[] Vi = new double[n+1];
        double[] Ui = new double[n+1];
        Vi[0] = 0.0;
        Ui[0] = 0.0;
        double A, B, C, D, hi, hi1, del;

        for (int i = 1; i < n; i++) {
            hi = ti[i] - ti[i - 1];
            C = hi;
            if (i + 1 == ti.length) {
                hi1 = 0;
                D = 3.0 *  fi[i] / hi;
            } else {
                hi1 = ti[i + 1] - ti[i];
                D = 3.0 * ((fi[i + 1] - fi[i]) / hi1 - (fi[i] - fi[i - 1]) / hi);
            }
            A = 2 * (hi + hi1);
            B = hi1;
            del =  C * Vi[i - 1] + A;
            Vi[i] = -(B / del);
            Ui[i] = (D - C * Ui[i - 1]) / del;
        }
        splines.get(n - 1).setC(Ui[n - 1]);
        for (int i = n - 2; i > 0; i--) {
            C = splines.get(i + 1).getC();
            splines.get(i).setC(Vi[i] * C + Ui[i]);
        }
    }

    public double realisationSpline(double ti, int i) {
        CubicSpline s = splines.get(i);
        return s.getA() + s.getB() * (ti - s.getX()) + s.getC() * Math.pow(ti - s.getX(), 2) + s.getD() * Math.pow(ti - s.getX(), 3);
    }

    public Double interpolate(double x) {
        if (splines.isEmpty()) {
            return null;
        }

        int n = splines.size();
        CubicSpline s;

        if (x <= splines.get(0).getX() || x >= splines.get(n - 1).getX()) {
            return Double.NaN;
        }
        int i = 0;
        int j = n - 1;
        while (i < j) {
            int k = (i + j) / 2;
            if (x <= splines.get(k).getX()) {
                j = k;
            } else {
                i = k + 1;
            }
        }
        s = splines.get(i - 1);
        double dx = (x - s.getX());
        return s.getA() + s.getB() * dx + s.getC() * Math.pow(dx, 2) + s.getD() * Math.pow(dx, 3);
    }
}
