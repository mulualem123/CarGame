import javafx.scene.paint.ImagePattern;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.lang.Math;

public class Main extends Application {

	protected double SCENE_WIDTH = 800;
	protected double SCENE_HEIGHT = 600;

	/**
	 * Main game loop
	 */
	AnimationTimer gameLoop;
	private static final String bACKGROUNDImage = "https://img.pngio.com/2d-top-down-highway-background-opengameartorg-road-png-top-down-840_650.png";
	private static final String CAR1 = "https://lh3.googleusercontent.com/proxy/XC7_y3ddeiXNtkiKQFLL4tvWBCF29sIkeE0cqtLhSflCPFSSMFXbdZ62cyDXjU-585dMLlC1FukykCmG0IQxYKvOFqUUPZf6vx-NSL3l0dGiBbakJ_i6vKb0K_1C";
	private static final String CAR2 = "https://cdn2.iconfinder.com/data/icons/top-view-cars-1/50/9-128.png";
	private static final String CAR3 = "https://cdn2.iconfinder.com/data/icons/top-view-cars-1/50/65-128.png";
	private static final String GAMEOver = "https://www.freepik.com/free-vector/glitch-game-background_4047741.htm";

	/**
	 * Container for the background image
	 */
	private Image backgroundImage;
	ImageView backgroundImageView;

	private Image backgroundImage2;
	private ImageView backgroundImageView2;

	// Cars
	private Image car;
	private ImageView carView;

	private ImageView goodCar2View;
	private ImageView badCarView;
	private ImageView badCar2View;
	private ImageView badCar3View;
	private ImageView badCar4View;
	private Image GameOver;

	boolean running, goNorth, goSouth, goEast, goWest;

	/**
	 * Scrolling speed of the background
	 */
	double backgroundScrollSpeed = 0.7;

	/**
	 * Layer for the background
	 */
	Pane backgroundLayer = new Pane();;

	@Override
	public void start(Stage primaryStage) {
		try {

			// create root node
			Group root = new Group();

			// create layers


			// add layers to scene root
			root.getChildren().add(backgroundLayer);

			// create scene
			Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

			// show stage
			primaryStage.setScene(scene);
			primaryStage.show();

			// load game assets
			loadGame();

			// start the game
			startGameLoop();

			//moving car with direction keys
			Car.moveCarTo(SCENE_WIDTH, SCENE_HEIGHT, 580, 420, goodCar2View);

			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					switch (event.getCode()) {
					case UP:
						goNorth = true;
						break;
					case DOWN:
						goSouth = true;
						break;
					case LEFT:
						goWest = true;
						break;
					case RIGHT:
						goEast = true;
						break;
					case SHIFT:
						running = true;
						break;
					}
				}
			});

			scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					switch (event.getCode()) {
					case UP:
						goNorth = false;
						break;
					case DOWN:
						goSouth = false;
						break;
					case LEFT:
						goWest = false;
						break;
					case RIGHT:
						goEast = false;
						break;
					case SHIFT:
						running = false;
						break;
					}
				}
			});

			primaryStage.setScene(scene);
			primaryStage.show();

			AnimationTimer timer = new AnimationTimer() {
				@Override
				public void handle(long now) {
					int dx = 0, dy = 0, badCarDX = 0, badCarDY = 0;

					if (goNorth)
						dy -= 1;
					if (goSouth)
						dy += 1;
					if (goEast)
						dx += 1;
					if (goWest)
						dx -= 1;
					if (running) {
						dx *= 3;
						dy *= 3;
					}

					badCarSideMov(goodCar2View, badCarView, false,  dx, dy, badCarDX, badCarDY);
					badCarSideMov( goodCar2View, badCar2View, true, dx, dy, badCarDX, badCarDY);
					badCarSideMov( goodCar2View, badCar3View, true, dx, dy, badCarDX, badCarDY);
					badCarSideMov( goodCar2View, badCar4View, false, dx, dy, badCarDX, badCarDY);
				}
			};
			timer.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//insert only the first three parameters, true for right direction, false for left direction
		private void badCarSideMov(ImageView GCar, ImageView BCar, boolean dir, int dx, int dy,int badCarDx, int badCarDy) {
			Car.moveCarBy(SCENE_WIDTH, SCENE_HEIGHT,dx, dy, GCar);
			//check if the two cars are close to each other then add 1 to bad car dx every time.
			if (Car.isClose(GCar, BCar)) {
				if (Car.isTooClose(GCar, BCar)) {
					gameLoop.stop();
					//primaryStage.setScene(https://www.freepik.com/free-vector/glitch-game-background_4047741.htm);
					//backgroundImageView.setImage(GameOver);
				}
				int badCDX;
				if(dir==true) {
					badCDX = badCarDx +1;
				}else {
					badCDX = badCarDx -1;
				}
				Car.moveCarBy(SCENE_WIDTH, SCENE_HEIGHT,badCDX, 0, BCar);
			}
		}
		
		
	private void loadGame() {

		// background
		// --------------------------------
		GameOver = new Image (GAMEOver);
		backgroundImage = new Image(bACKGROUNDImage);

		backgroundImageView = new ImageView(backgroundImage);

		// reposition the map. it is scrolling from bottom of the background to top of
		// the background
		backgroundImageView.relocate(0, -backgroundImageView.getImage().getHeight() + SCENE_HEIGHT);

		// add background to layer
		backgroundLayer.getChildren().add(backgroundImageView);

		// Second image to scroll

		backgroundImage2 = new Image(bACKGROUNDImage);

		backgroundImageView2 = new ImageView(backgroundImage2);

		// reposition the map. it is scrolling from bottom of the background to top of
		// the background
		backgroundImageView2.relocate(0, -backgroundImageView2.getImage().getHeight());

		// add background to layer
		backgroundLayer.getChildren().add(backgroundImageView2);

		// adding cars


		goodCar2View = Car.car(backgroundLayer,CAR2, 500, 390);
		badCarView = Car.car(backgroundLayer, CAR3, 300, 0); 
		badCar2View = Car.car(backgroundLayer, CAR3, 400, 400);
		badCar3View = Car.car(backgroundLayer, CAR3, 600, 300);
		badCar4View = Car.car(backgroundLayer, CAR3, 200, 150);
		
	}

	
	private void startGameLoop() {

		// game loop
		gameLoop = new AnimationTimer() {

			@Override
			public void handle(long l) {

				// scroll background
				// ---------------------------
				// calculate new position
				double y = backgroundImageView.getLayoutY() + backgroundScrollSpeed;
				double y2 = backgroundImageView2.getLayoutY() + backgroundScrollSpeed;
				//             // check bounds. we scroll upwards, so the y position is negative. once it's > 0 we have reached the end of the map and stop scrolling
				if (y2 > 0) {
					y = -backgroundImageView.getImage().getHeight() + SCENE_HEIGHT;
					y2 = -backgroundImageView.getImage().getHeight();
				}

				// move background/BadCar
				backgroundImageView.setLayoutY(y);
				backgroundImageView2.setLayoutY(y2);
				Car.verticalMovBadCar( backgroundImageView, badCarView,1 );
				Car.verticalMovBadCar( backgroundImageView, badCar2View, 2);
				Car.verticalMovBadCar( backgroundImageView, badCar3View, 1.2);
				Car.verticalMovBadCar( backgroundImageView, badCar4View, .5);

			}
		};

		gameLoop.start();

	}



	public static void main(String[] args) {
		launch(args);
	}
}