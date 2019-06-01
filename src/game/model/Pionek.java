package game.model;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Pionek extends Pane {
	
	private double scale;
	private Color color;
	private Circle circle;
	private Polygon triangle;
	private Rectangle rectangle;
	private CubicCurve curve;
	private int stageX;
	private int stageY;
	
	
	
	public Pionek() {
		this(Color.LIME, 1);
	}
	
	public Pionek(Color color, double scale) {
		super();
		this.scale = scale;
		this.color = color;circle = new Circle(15 * scale);
		circle.setFill(color);
		circle.setLayoutX(20 * scale);
		circle.setLayoutY(20 * scale);
		
		triangle = new Polygon();
        triangle.getPoints().addAll(new Double[]{
            20.0 * scale, 10.0 * scale,
            00.0 * scale, 75.0 * scale,
            40.0 * scale, 75.0 * scale });
        triangle.setFill(color);
        
        rectangle = new Rectangle();
        rectangle.setWidth(40 * scale);
        rectangle.setHeight(10 * scale);
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
	
	public void build() {
		
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
	}
	
	public void przesunPoMoscie(double finalX, double finalY) {
		Path path = new Path();
		path.getElements().add(new MoveTo(getLayoutX() - stageX, getLayoutY() - stageY));
		path.getElements().add(new QuadCurveTo(
				Math.max(getStageX(), finalX - stageX), 
				Math.max(getStageY(), finalY - stageY), 
				finalX - stageX, 
				finalY - stageY));
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(4000));
		pathTransition.setPath(path);
		pathTransition.setNode(this);
		pathTransition.play();
		this.setCounterPosition(finalX, finalY);
	}
	
	
	
}
