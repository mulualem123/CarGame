
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Car extends Main {
	public Car() {
		
		
	}
	
	Image car;
	ImageView carView;
	Pane backgroundLayer = super.backgroundLayer;
	static double SCENE_HEIGHT= 600;
	double SCENE_WIDTH = 800;
	
	
	public static ImageView car(Pane backgroundLayer, String image, double x, double y) {
		Image car = new Image(image);
		ImageView carView = new ImageView(car);
		carView.relocate(x,y);
		backgroundLayer.getChildren().add(carView);
		return carView;
	}
	
	public static void verticalMovBadCar(ImageView backGrImView, ImageView badCar, double speed) {
		double y = badCar.getLayoutY() + speed;
		double y1 = badCar.getLayoutY() + speed;
		
		// check bounds. we scroll upwards, so the y position is negative. once it's > 0 we have reached the end of the map and stop scrolling
		if (y >= SCENE_HEIGHT) {
			//y = badCar.getImage().getHeight() + SCENE_HEIGHT ;
			y = -backGrImView.getImage().getHeight();
		}

		//move the bad car
		badCar.setLayoutY(y);
		//badCar.setLayoutY(y);
	}
	
	protected static void moveCarBy(double SCENE_WIDTH, double SCENE_HEIGHT, int dx, int dy, ImageView car) {
		if (dx == 0 && dy == 0)
			return;

		final double cx = car.getBoundsInLocal().getWidth() / 2;
		final double cy = car.getBoundsInLocal().getHeight() / 2;

		double x = cx + car.getLayoutX() + dx;
		double y = cy + car.getLayoutY() + dy;
		moveCarTo(SCENE_WIDTH, SCENE_HEIGHT,x, y, car);
	}

	protected static void moveCarTo(double SCENE_WIDTH, double SCENE_HEIGHT, double x, double y, ImageView car) {
		final double cx = car.getBoundsInLocal().getWidth() / 2;
		final double cy = car.getBoundsInLocal().getHeight() / 2;
		if (x - cx >= 0 && x + cx <= SCENE_WIDTH && y - cy >= 0 && y + cy <= SCENE_HEIGHT) {			
			car.relocate(x - cx, y - cy);
		}
		
	}
	protected static boolean isClose(ImageView goodCar, ImageView badCar) {
		double threshold = goodCar.getImage().getWidth() / 2 + badCar.getImage().getWidth()/1.7;
		double goodCar2X = goodCar.getLayoutX();
		double goodCar2Y = goodCar.getLayoutY();
		double badCarX = badCar.getLayoutX();
		double badCarY = badCar.getLayoutY();
		
		//calculate the distance between the points of the images center
		double dX = badCarX - goodCar2X;
		double dY = badCarY - goodCar2Y;
		double distance = Math.sqrt((dX*dX) + (dY*dY));
		
		//check if the distance is less than the threshold
		if(distance <= threshold) {
			return true;
		}else {
			return false;
		}
	}
	
	protected static boolean isTooClose(ImageView goodCar, ImageView badCar) {
		double threshold = goodCar.getImage().getWidth() / 3 + badCar.getImage().getWidth() / 3;
		double goodCar2X = goodCar.getLayoutX();
		double goodCar2Y = goodCar.getLayoutY();
		double badCarX = badCar.getLayoutX();
		double badCarY = badCar.getLayoutY();
		
		//calculate the distance between the points of the images center
		double dX = badCarX - goodCar2X;
		double dY = badCarY - goodCar2Y;
		double distance = Math.sqrt((dX*dX) + (dY*dY));
		
		//check if the distance is less than the threshold
		if(distance < threshold) {
			return true;
		}else {
			return false;
		}
	}
}

