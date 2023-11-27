package com.cgvsu.protocurvefxapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CubicSplineCurveApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CubicSplineCurveApplication.class.getResource("mainwindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Cubic Spline Beach");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
//        SplineRealization splineRealization = new SplineRealization();
//        double[] ti = {2, 1, 3, 5, 2, 4, 3, 2, 3, 2, 3, 2};
//        double[] fi = {1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4};
//        splineRealization.searchCoeffSplines(ti, fi, ti.length);
//        for (int i = 0; i < ti.length; i++){
//            System.out.println("Cплайн номер # " + (i+1));
//            System.out.print(" X " + splineRealization.splines.get(i).getX() + "  ");
//            System.out.print(" A " + splineRealization.splines.get(i).getA() + " ");
//            System.out.print(" B " + splineRealization.splines.get(i).getB()+ " ");
//            System.out.print(" C " + splineRealization.splines.get(i).getC()+ " ");
//            System.out.println(" D " + splineRealization.splines.get(i).getD()+ " ");
//            System.out.println();
//        }
    }
}