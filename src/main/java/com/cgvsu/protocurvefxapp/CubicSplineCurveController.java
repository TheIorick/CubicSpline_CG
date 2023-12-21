package com.cgvsu.protocurvefxapp;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Objects;

public class CubicSplineCurveController {

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    ArrayList<Point2D> points = new ArrayList<>();
    SplineRealization splineForX = new SplineRealization();
    SplineRealization splineForY = new SplineRealization();
    SplineRealization spline = new SplineRealization();
    final int POINT_RADIUS = 3;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));
        canvas.setOnMouseClicked(event -> {
            if (Objects.requireNonNull(event.getButton()) == MouseButton.PRIMARY) {
                handlePrimaryClick(canvas.getGraphicsContext2D(), event);
            }
        });
    }

    private void handlePrimaryClick(GraphicsContext graphicsContext, MouseEvent event) {
        final Point2D clickPoint = new Point2D(event.getX(), event.getY());

        graphicsContext.fillOval(
                clickPoint.getX() - POINT_RADIUS, clickPoint.getY() - POINT_RADIUS,
                2 * POINT_RADIUS, 2 * POINT_RADIUS);

        if (points.size() == 1) {
            graphicsContext.strokeLine(points.get(0).getX(), points.get(0).getY(), clickPoint.getX(), clickPoint.getY());
        }
        points.add(clickPoint);
        double[] ti = new double[points.size()];
        double[] xi = new double[points.size()];
        double[] yi = new double[points.size()];

        if (points.size() > 2) {
            ti[0] = 0;
            xi[0] = points.get(0).getX();
            yi[0] = points.get(0).getY();
            graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            for (int i = 1; i < points.size(); i++) {
                graphicsContext.fillOval(
                        points.get(i).getX() - POINT_RADIUS, points.get(i).getY() - POINT_RADIUS,
                        2 * POINT_RADIUS, 2 * POINT_RADIUS);
                ti[i] = calculateTi(points.get(i - 1).getX(), points.get(i).getX(), points.get(i - 1).getY(), points.get(i).getY()) + ti[i - 1];
                xi[i] = points.get(i).getX();
                yi[i] = points.get(i).getY();
            }
            splineForX.searchCoeffSplines(ti, xi, xi.length);
            splineForY.searchCoeffSplines(ti, yi, yi.length);
            spline.searchCoeffSplines(xi, yi, points.size());
            for (int i = 0; i < points.size() - 1; i++) {
                for (double t = ti[i]; t < ti[i + 1] - 0.1; t += 0.1) {
                    //final, потому что мы никак не будем изменять объект в дальнейшем
                    final double interpolationX1 = splineForX.realisationSpline(t, i);
                    final double interpolationY1 = splineForY.realisationSpline(t, i);
                    graphicsContext.fillOval(interpolationX1, interpolationY1,  POINT_RADIUS,  POINT_RADIUS);
                }
            }
        }
    }

    private double calculateTi(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}