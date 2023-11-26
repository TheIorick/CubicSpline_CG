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

    ArrayList<Point2D> points = new ArrayList<Point2D>();

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

        final int POINT_RADIUS = 3;
        graphicsContext.fillOval(
                clickPoint.getX() - POINT_RADIUS, clickPoint.getY() - POINT_RADIUS,
                2 * POINT_RADIUS, 2 * POINT_RADIUS);

        if (points.size() == 1) {
            final Point2D lastPoint = points.get(0);
            graphicsContext.strokeLine(lastPoint.getX(), lastPoint.getY(), clickPoint.getX(), clickPoint.getY());
        }if (points.size() > 1){
            graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            for (Point2D p : points){
                graphicsContext.fillOval(
                        clickPoint.getX() - POINT_RADIUS, clickPoint.getY() - POINT_RADIUS,
                        2 * POINT_RADIUS, 2 * POINT_RADIUS);
            }

        }
        points.add(clickPoint);
    }
}