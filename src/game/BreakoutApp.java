package game;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class BreakoutApp extends Application{
	
	private final int LENGTH = 480;
	private final int WIDTH = 640;
	private ArrayList<Ball> balls;
	private GraphicsContext g2d;
	private GameTimer timer;
	private double panelX;
	private double ballX, ballY, ballXV, ballYV;
	private Brick[][]blocks;
	private boolean right, left;
	static double panelXV;
	private int score;
	private int level;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	private void initVariables()
	{
		level = 1;
		panelX = LENGTH/2-30;
		ballX = LENGTH/2;
		ballY = WIDTH/2;
		ballXV = 0;
		ballYV = 5;
		right = false;
		left = false;
		loadLevel();
		score = 0;
		balls = new ArrayList<Ball>();
		balls.add(new Ball(ballX, ballY, ballXV, ballYV));
	}
	
	private void loadLevel()
	{
		if (level == 1)
		{
			blocks = new Brick[][] {
				{
					new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(1), new Brick(1), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(false)
				},
				{
					new Brick(1), new Brick(false), new Brick(false), new Brick(1), new Brick(1), new Brick(2), new Brick(2), new Brick(1), new Brick(1), new Brick(false), new Brick(false), new Brick(1)
				},
				{
					new Brick(false), new Brick(1), new Brick(1), new Brick(false), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(false), new Brick(1), new Brick(1), new Brick(false)
				},
				{
					new Brick(false), new Brick(false), new Brick(false), new Brick(1), new Brick(1), new Brick(false), new Brick(false), new Brick(1), new Brick(1), new Brick(false), new Brick(false), new Brick(false)
				},
				{
					new Brick(false), new Brick(1), new Brick(1), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(1), new Brick(1), new Brick(false)
				},
				{
					new Brick(1), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(1)
				}
			};
		}
		else if (level == 2)
		{
			blocks = new Brick[][] {
				{
					new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2)
				},
				{
					new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3)
				},
				{
					new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2)
				},
				{
					new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3)
				},
				{
					new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2)
				},
				{
					new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3)
				}
			};
		}
		else if (level == 3)
		{
			blocks = new Brick[][] {
				{
					new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3)
				},
				{
					new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3)
				},
				{
					new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1)
				},
				{
					new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1)
				},
				{
					new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2)
				},
				{
					new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2)
				}
			};
		}
		//Reset Ball location
		panelX = LENGTH/2-30;
		ballX = LENGTH/2;
		ballY = WIDTH/2;
		ballXV = 0;
		ballYV = 5;
		balls = new ArrayList<Ball>();
		balls.add(new Ball(ballX, ballY, ballXV, ballYV));
	}
	
	@Override
	public void start(Stage mainStage) {
		HBox root = new HBox();
		root.setAlignment(Pos.CENTER);
		Scene theScene = new Scene(root, LENGTH, WIDTH);
		Canvas canvas = new Canvas(LENGTH, WIDTH);
		g2d = canvas.getGraphicsContext2D();
		g2d.setFont(new Font("Times new roman", 28));
		initVariables();
		timer = new GameTimer();
		root.getChildren().add(canvas);
		//KeyListeners start
		theScene.setOnKeyPressed(e -> {
			switch (e.getCode())
			{
			case A:
				right = false;
				left = true;
				break;
			case D: 
				left = false;
				right = true;
				break;
			default: break;
			}
		});
		
		theScene.setOnKeyReleased(e -> {
			switch (e.getCode())
			{
			case A: left = false;
			break;
			case D: right = false;
			break;
			case R: initVariables();
			default: break;
			}
		});
		//KeyListeners end
		
		mainStage.setScene(theScene);
		mainStage.setResizable(false);
		mainStage.setTitle("Breakout v1.4");
		mainStage.setOnCloseRequest(e -> {
			e.consume();
			System.exit(0);
		});
		mainStage.show();
		timer.start();
	}
	
	private class GameTimer extends AnimationTimer
	{
		public GameTimer()
		{
			//Empty
		}
		
		@Override
		public void handle(long arg0) {
			handleIt();
		}
		
		private synchronized void handleIt()
		{
			handlePanel();
			handleBall();
			g2d.clearRect(0, 0, LENGTH, WIDTH);
			drawBlocks();
			drawBoard();
			g2d.setFill(Color.BLACK);
			g2d.fillRect(panelX, 500, 100, 10);
			for (Ball e: balls)
			{
				g2d.setFill(e.getColor());
				g2d.fillOval(e.getX(), e.getY(), 10, 10);
			}
			checkIfAllBroken();
			notifyAll();
		}
		
		private void checkIfAllBroken()
		{
			boolean nextLevel = true;
			for (Brick[] e : blocks)
			{
				for (Brick f : e)
				{
					if (f.isAlive())
					{
						nextLevel = false;
					}
				}
			}
			if (nextLevel)
			{
				level++;
				loadLevel();
			}
		}
		
		private void drawBoard()
		{
			g2d.setFill(Color.GRAY);
			g2d.fillRect(0, WIDTH-95, WIDTH, 100);
			g2d.setFill(Color.GREEN);
			g2d.fillText("Score: "+score, 200, 600);
			g2d.setFill(Color.RED);
			g2d.fillText(""+panelXV, 154, 630);
		}
		
		private void drawBlocks()
		{
			for (int i = 0; i < blocks.length; i++)
			{
				for (int j = 0; j < blocks[0].length; j++)
				{
					if (blocks[i][j].isAlive()==true)
					{
						g2d.setFill(Color.BLACK);
						g2d.fillRect(j*40, i*20, 40, 20);
						g2d.setFill(blocks[i][j].getColor());
						g2d.fillRect(j*40+5, i*20+5, 30, 10);
					}
				}
			}
		}
		
		private void handleBall()
		{
			ArrayList<Integer> indexes = new ArrayList<Integer>();
			ArrayList<Integer> ballsToDupe = new ArrayList<Integer>();
			for (int i = 0; i < balls.size(); i++)
			{
				if (balls.get(i).getX() <= 5 || balls.get(i).getX() >= LENGTH-5)
					{
						if (balls.get(i).getX() <= 5)
						{
							balls.get(i).setX(6);
						}
						else
						{
							balls.get(i).setX(LENGTH-6);
						}
						balls.get(i).reverseX();
					}
				if (balls.get(i).getY() <= 5 || balls.get(i).getY() >= WIDTH-105)
				{
					if (balls.get(i).getY() >= WIDTH-105 && i != 0)
					{
						balls.remove(i);
						i--;
					}
					else {
						balls.get(i).reverseY();
						if (balls.get(i).getY() >= WIDTH-105)
						{
							score-=100;
							if (score < 0)
							{
								score = 0;
							}
						}
					}
				}
			if (balls.get(i).getX() >= panelX-balls.get(i).getXV() && balls.get(i).getX() <= panelX+100+balls.get(i).getXV() && (balls.get(i).getY() > 495 && balls.get(i).getY() < 515))
			{
				if ((balls.get(i).getY() > 495 && balls.get(i).getYV() > 0) || (balls.get(i).getY() < 515 && balls.get(i).getYV() < 0))
				{
					balls.get(i).reverseY();
					balls.get(i).changeX();
				}
				else 
				{
					balls.get(i).reverseX();
				}
			}
			boolean a = detectCollisionWithBricks(balls.get(i));
			if (a)
			{
				ballsToDupe.add(i);
				indexes.add(i);
			}
				balls.get(i).update();
			}
			
			for (Integer a : ballsToDupe)
			{
				balls.add(new Ball(balls.get(a).getX(), balls.get(a).getY()+10, balls.get(indexes.get(0)).getXV()*((int) (Math.random()*2))-1, balls.get(a).getYV(), Color.CRIMSON));
				indexes.remove(0);
			}
		}
		
		private boolean detectCollisionWithBricks(Ball e)
		{
			boolean done = false;
			boolean addBalls = false;
			for (int i = 0; i < blocks.length && !done; i++)
			{
				for (int j = 0; j < blocks[1].length && !done; j++)
				{
					if ((e.getX() >= j*40-5 && e.getX() <= j*40+45) && (e.getY() >= i*20-5 && e.getY() <= i*20+25) && 
							blocks[i][j].isAlive()==true)
					{
						blocks[i][j].update();
						score+=100;
						if ((e.getY() > i*20+e.getYV() && !(e.getY() < i*20+20 -e.getYV()) && e.getYV() < 0) || (!(e.getY() > i*20+e.getYV()) && e.getY() <= i*20+15 && e.getYV() > 0))
						{
							e.reverseY();
							done = true;
						}
						else if (!done && (e.getX() > j*40+e.getXV() && !(e.getX() < j*40+40-e.getXV())) || (!(e.getX() > j*40+e.getXV()) && e.getX() <= j*40+40-e.getXV()))
						{
							e.reverseX();
							done = true;
						}
						if (done)
						{
							if (blocks[i][j].getType() == 3)
							{
								addBalls = true;
							}
						}
					}
				}
			}
			return addBalls;
		}
		
		
		private void handlePanel()
		{
			if (left)
			{
				if(panelXV > -10)
				{
					panelXV -= .5;
				}
			}
			else if (right)
			{
				if (panelXV < 10)
				{
					panelXV += .5;
				}
			}
			else if (panelXV > .5)
			{
				panelXV-= .5;
			}
			else if (panelXV <= .5 && panelXV > 0)
			{
				panelXV=0;
			}
			else if (panelXV < -.5)
			{
				panelXV+= .5;
			}
			else if (panelXV >= -.5 && panelXV < 0)
			{
				panelXV = 0;
			}
			
			if (panelX < 0)
			{
				panelX = 0;
			}
			if (panelX > 380)
			{
				panelX = 380;
			}
			panelX+=panelXV;
		}
	}

}
