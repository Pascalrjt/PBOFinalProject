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
	private static int linesNo = 0;
	
	
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
							over.setStyle("-fx-font:30 arial;");
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
	// rotating the pieces
	private void MoveTurn(Form form) {
		int f = form.form;
		Rectangle a = form.a;
		Rectangle b = form.b;
		Rectangle c = form.c;
		Rectangle d = form.d;
		switch (from.getName()) {
		case "j":
			if (f == 1 && cB(a, 1, -1) && cB(c, -1, -1) && cB(d, -2, -2)) {
				MoveRight(form.a);
				MoveDown(form.a);
				MoveDown(form.c);
				MoveLeft(form.c);
				MoveDown(form.d);
				MoveDown(form.d);
				MoveLeft(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			if (f == 2 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, -2, 2)) {
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveLeft(form.d);
				MoveLeft(form.d);
				MoveUp(form.d);
				MoveUp(form.d);
				form.changeForm();
				break;
			}
			if (f == 3 && cB(a, -1, 1) && cB(c, 1, 1) && cB(d, 2, 2)) {
				MoveLeft(form.a);
				MoveUp(form.a);
				MoveUp(form.c);
				MoveRight(form.c);
				MoveUp(form.d);
				MoveUp(form.d);
				MoveRight(form.d);
				MoveRight(form.d);
				form.changeForm();
				break;
			}
			if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 2, -2)) {
				MoveUp(form.a);
				MoveRight(form.a);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveRight(form.d);
				MoveRight(form.d);
				MoveDown(form.d);
				MoveDown(form.d);
				form.changeForm();
				break;
			}
			break;
		case "l":
			if (f == 1 && cB(a, 1, -1) && cB(c, 1, 1) && cB(b, 2, 2)) {
				MoveRight(form.a);
				MoveDown(form.a);
				MoveUp(form.c);
				MoveRight(form.c);
				MoveUp(form.b);
				MoveUp(form.b);
				MoveRight(form.b);
				MoveRight(form.b);
				form.changeForm();
				break;
			}
			if (f == 2 && cB(a, -1, -1) && cB(b, 2, -2) && cB(c, 1, -1)) {
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveRight(form.b);
				MoveRight(form.b);
				MoveDown(form.b);
				MoveDown(form.b);
				MoveRight(form.c);
				MoveDown(form.c);
				form.changeForm();
				break;
			}
			if (f == 3 && cB(a, -1, 1) && cB(c, -1, -1) && cB(b, -2, -2)) {
				MoveLeft(form.a);
				MoveUp(form.a);
				MoveDown(form.c);
				MoveLeft(form.c);
				MoveDown(form.b);
				MoveDown(form.b);
				MoveLeft(form.b);
				MoveLeft(form.b);
				form.changeForm();
				break;
			}
			if (f == 4 && cB(a, 1, 1) && cB(b, -2, 2) && cB(c, -1, 1)) {
				MoveUp(form.a);
				MoveRight(form.a);
				MoveLeft(form.b);
				MoveLeft(form.b);
				MoveUp(form.b);
				MoveUp(form.b);
				MoveLeft(form.c);
				MoveUp(form.c);
				form.changeForm();
				break;
			}
			break;
		case "o":
			break;
		case "s":
			if (f == 1 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveUp(form.d);
				MoveUp(form.d);
				form.changeForm();
				break;
			}
			if (f == 2 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {
				MoveUp(form.a);
				MoveRight(form.a);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveDown(form.d);
				MoveDown(form.d);
				form.changeForm();
				break;
			}
			if (f == 3 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveUp(form.d);
				MoveUp(form.d);
				form.changeForm();
				break;
			}
			if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {
				MoveUp(form.a);
				MoveRight(form.a);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveDown(form.d);
				MoveDown(form.d);
				form.changeForm();
				break;
			}
			break;
		case "t":
			if (f == 1 && cB(a, 1, 1) && cB(d, -1, -1) && cB(c, -1, 1)) {
				MoveUp(form.a);
				MoveRight(form.a);
				MoveDown(form.d);
				MoveLeft(form.d);
				MoveLeft(form.c);
				MoveUp(form.c);
				form.changeForm();
				break;
			}
			if (f == 2 && cB(a, 1, -1) && cB(d, -1, 1) && cB(c, 1, 1)) {
				MoveRight(form.a);
				MoveDown(form.a);
				MoveLeft(form.d);
				MoveUp(form.d);
				MoveUp(form.c);
				MoveRight(form.c);
				form.changeForm();
				break;
			}
			if (f == 3 && cB(a, -1, -1) && cB(d, 1, 1) && cB(c, 1, -1)) {
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveUp(form.d);
				MoveRight(form.d);
				MoveRight(form.c);
				MoveDown(form.c);
				form.changeForm();
				break;
			}
			if (f == 4 && cB(a, -1, 1) && cB(d, 1, -1) && cB(c, -1, -1)) {
				MoveLeft(form.a);
				MoveUp(form.a);
				MoveRight(form.d);
				MoveDown(form.d);
				MoveDown(form.c);
				MoveLeft(form.c);
				form.changeForm();
				break;
			}
			break;
		case "z":
			if (f == 1 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) {
				MoveUp(form.b);
				MoveRight(form.b);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveLeft(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			if (f == 2 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) {
				MoveDown(form.b);
				MoveLeft(form.b);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveRight(form.d);
				MoveRight(form.d);
				form.changeForm();
				break;
			}
			if (f == 3 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) {
				MoveUp(form.b);
				MoveRight(form.b);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveLeft(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			if (f == 4 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) {
				MoveDown(form.b);
				MoveLeft(form.b);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveRight(form.d);
				MoveRight(form.d);
				form.changeForm();
				break;
			}
			break;
		case "i":
			if (f == 1 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) {
				MoveUp(form.a);
				MoveUp(form.a);
				MoveRight(form.a);
				MoveRight(form.a);
				MoveUp(form.b);
				MoveRight(form.b);
				MoveDown(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			if (f == 2 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) {
				MoveDown(form.a);
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveLeft(form.a);
				MoveDown(form.b);
				MoveLeft(form.b);
				MoveUp(form.d);
				MoveRight(form.d);
				form.changeForm();
				break;
			}
			if (f == 3 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) {
				MoveUp(form.a);
				MoveUp(form.a);
				MoveRight(form.a);
				MoveRight(form.a);
				MoveUp(form.b);
				MoveRight(form.b);
				MoveDown(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			if (f == 4 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) {
				MoveDown(form.a);
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveLeft(form.a);
				MoveDown(form.b);
				MoveLeft(form.b);
				MoveUp(form.d);
				MoveRight(form.d);
				form.changeForm();
				break;
			}
			break;
		}
	}
	
	private void RemoveRows(Pane pane) {
		ArrayList<Node> rects = new ArrayList<Node>();
		ArrayList<Integer> lines = new ArrayList<Integer>();
		ArrayList<Node> newrects = new ArrayList<Node>();
		int full = 0;
		
		for(int i = 0; i < MESH[0].length; i++) {
			for(int j = 0; j < MESH.length; j++) {
				if(MESH[i][j] == 1) full++;
			}
			if(full == MESH.length) lines.add(i);
			full = 0;
		}
		
	}
}

 

