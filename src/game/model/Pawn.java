package game.model;

import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Pawn extends Pane {

	private double scale;
	private Color color;
	private Circle circle;
	private Polygon triangle;
	private Rectangle rectangle;
	private CubicCurve curve;
	private int stageX;
	private int stageY;
	private double xx;
	private double yy;

	public Pawn() {
		this(Color.LIME, 1);
	}

	public Pawn(Color color, double scale) {
		super();
		this.scale = scale;
		this.color = color;circle = new Circle(15 * scale);
		circle.setFill(color);
		circle.setLayoutX(20 * scale);
		circle.setLayoutY(20 * scale);

		triangle = new Polygon();
        triangle.getPoints().addAll(new Double[]{
            20.0 * scale, 10.0 * scale,
            00.0 * scale, 76.0 * scale,
            40.0 * scale, 76.0 * scale });
        triangle.setFill(color);

        rectangle = new Rectangle();
        rectangle.setWidth(40 * scale);
        rectangle.setHeight(11 * scale);
        rectangle.setFill(color);
        rectangle.setLayoutX(0 * scale);
        rectangle.setLayoutY(75 * scale);

        curve = new CubicCurve();
        curve.setFill(color);
        curve.setStartX(0 * scale);
        curve.setStartY(85 * scale);
        curve.setEndX(40 * scale);
        curve.setEndY(85 * scale);
        curve.setControlX1(10 * scale);
        curve.setControlY1(95 * scale);
        curve.setControlX2(30 * scale);
        curve.setControlY2(95 * scale);

        stageX =(int) (20 * scale);
        stageY =(int) (80 * scale);

        circle.getStyleClass().add("counter_circle");
        Group group = new Group();
        group.getChildren().addAll(triangle, rectangle, curve);
        group.getStyleClass().add("counter_triangle");

		this.getChildren().addAll(group, circle);
	}

	public int getStageX() {
		return stageX;
	}

	public int getStageY() {
		return stageY;
	}

	public void setCounterPosition(double finalX, double finalY) {
		setLayoutX(finalX - stageX);
		setLayoutY(finalY - stageY);
		xx = finalX;
		yy = finalY;
	}

	public void moveOnBridge(double finalX, double finalY) {
		Path path = new Path();
		path.getElements().add(new MoveTo(xx, yy - 35));
		path.getElements().add(new QuadCurveTo(
				Math.max(xx, finalX),
				Math.min(yy, finalY),
				finalX,
				finalY - 35));
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(1000));
		pathTransition.setPath(path);
		pathTransition.setAutoReverse(false);
		pathTransition.setInterpolator(Interpolator.LINEAR);
		pathTransition.setCycleCount(1);
		pathTransition.setNode(this);
		pathTransition.play();

		xx = finalX;
		yy = finalY;
	}

	public void fallIntoWater(double finalX, double finalY) {
		Path path = new Path();
		path.getElements().add(new MoveTo(xx, yy - 35));
		path.getElements().add(new QuadCurveTo(
				Math.max(xx, finalX) ,
				Math.min(yy, finalY) ,
				finalX,
				yy - 35));
		path.getElements().add(new QuadCurveTo(
				Math.max(xx, finalX),
				Math.min(yy, finalY) ,
				finalX,
				finalY - 35));

		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(3000));
		pathTransition.setPath(path);
		pathTransition.setAutoReverse(false);
		pathTransition.setInterpolator(Interpolator.LINEAR);
		pathTransition.setCycleCount(1);

		RotateTransition rotateTransition = new RotateTransition();
		rotateTransition.setDuration(Duration.millis(2000));
		rotateTransition.setByAngle(360);
		rotateTransition.setCycleCount(4);
		rotateTransition.setAutoReverse(false);

		ScaleTransition scaleTransition = new ScaleTransition();
		scaleTransition.setDuration(Duration.millis(2000));
		scaleTransition.setToX(0);
		scaleTransition.setToY(0);
		scaleTransition.setCycleCount(1);
		scaleTransition.setAutoReverse(false);

		ParallelTransition parallelTransition = new ParallelTransition(this, pathTransition, rotateTransition, scaleTransition);
		parallelTransition.play();

		xx = finalX;
		yy = finalY;
	}
}