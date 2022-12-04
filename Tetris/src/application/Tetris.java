package application;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Tetris extends Application{
	
// variables 
	public static final int MOVE = 25;
	public static final int SIZE = 25;
	public static int XMAX = SIZE * 12;
	public static int YMAX = SIZE * 24;
	public static int [][] MESH = new int [XMAX/SIZE][YMAX/SIZE];
	private static Pane group = new Pane();
	private static Form object;
	private static Scene scene = new Scene(group, XMAX + 150, YMAX);
	public static int score = 0;
	private static int top = 0;
	private static boolean game = true;
	private static Form nextObj = Controller.makeRect();
	private static int LinesNo = 0;
	
	
// starting the game
	
	public void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		for(int[] a: MESH) {
			Arrays.fill(a, 0);
		}
		
		// Scoring and level text
		Line line = new Line(XMAX, 0, XMAX, YMAX);
		Text scoretext = new Text("Score: ");
		scoretext.setStyle("-fx-font: 20 arial;");
		scoretext.setY(50);
		scoretext.setX(XMAX + 5);
		Text level = new Text("Lines: ");
		level.setStyle("-fx-font: 20 arial;");
		level.setY(100);
		level.setX(XMAX + 5);
		level.setFill(Color.GREEN);
		group.getChildren().addAll(scoretext, line, level);
		
		// Blocks and stage
		Form a = nextObj;
		group.getChildren().addAll(a.a, a.b, a.c, a.d);
		moveOnKeyPress(a);
		object = a;
		nextObj = Controller.makeRect();
		stage.setScene(scene);
		stage.setTitle("TETRIS made by Pascal | NRP: 5025211072");
		stage.show();

		// Timer
		Timer fall = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				Platform.runLater(new Runnable(){
					public void run() {
						if(object.a.getY() == 0 || object.b.getY() == 0 || object.c.getY() == 0 || object.d.getY() == 0) top++;
						else top = 0;
						
						if(top == 2) {
							//GAMEOVER
							Text over = new Text("GAME OVER: SKILL ISSUE");
							over.setFill(Color.RED);
							over.setStyle("-fx-font: 70 arial;");
							over.setY(250);
							over.setX(10);
							group.getChildren().add(over);	
							game = false;
						}
						//EXIT
						if(top == 15) {
						System.exit(0);
						}
					
						if(game) {
							MoveDown(object);
							scoretext.setText("Score: " + Integer.toString(score));
							level.setText("Lines: " + Integer.toString(linesNo));
						}
					}
				});
			}
		};
		fall.schedule(task, 0, 300);
	}
	
	private void moveOnKeyPress(Form form) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case RIGHT:
					Controller.MoveRight(form);
					break;
				case DOWN:
					MoveDown(form);
					score++;
					break;
				case LEFT:
					Controller.MoveLeft(form);
					break;
				case UP:
					MoveTurn(form);
					break;
				}
			}
		});
	}
}

 

