import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
// import java.util.random;
// import java.util.Date;

// import javax.swing.JButton;
// import javax.swing.JComponent;
// import javax.swing.JFrame;
// import javax.swing.JLabel;
// import javax.swing.JPanel;
// import javax.swing.Timer;
import javax.swing.*;

/**
 * This class handles the GUI, snake movement, and score/time.
 * 
 * @author brianwu
 */
public class SnakeGUI extends JComponent implements KeyListener {

	private static final int DEFAULT_WINDOW_WIDTH = 18;
	// private static final int DEFAULT_WINDOW_WIDTH = 4;
	private static final int DEFAULT_WINDOW_HEIGHT = 24;
	// private static final int DEFAULT_WINDOW_HEIGHT = 4;
	private static final int START_DELAY = 600;
	private static final int BORDER_LAYOUT_GAP = 5;

	private SnakeTile[][] tiles;
	private ArrayList<Point> moves;

	private Timer timer;
	private Snake snake = null;
	private int gameScore;
	private int highScore;
	private int currentLevel;

	private JLabel scoreLabel;
	private JLabel levelLabel;
	private JLabel levelvedUpLabel;
	private JLabel highScoreLabel;

	private JButton resetButton;
	private JButton settingButton;

	private static Random numGenerator = new Random(System.currentTimeMillis());

	private double[] speedSettingSelection;

	public SnakeGUI() {

		setupGUI();
	}

	private void setupGUI() {

		speedSettingSelection = new double[1];

		// Setting up the layout for the GUI (with gaps between elements)
		BorderLayout layout = new BorderLayout(BORDER_LAYOUT_GAP, BORDER_LAYOUT_GAP);
		this.setLayout(layout);

		// Will store the score, level, and levelup!
		GridLayout scoreElementsLayout = new GridLayout(1, 4);
		GridLayout sideButtonsLayout = new GridLayout(2, 1);

		// Score board panel
		JPanel scoreBoard = new JPanel(scoreElementsLayout);
		scoreBoard.setBackground(Color.BLACK);

		gameScore = 0;
		currentLevel = 0;
		highScore = 0;

		scoreLabel = new JLabel("Score: " + gameScore);
		levelLabel = new JLabel("Level: " + currentLevel);
		levelvedUpLabel = new JLabel("");
		highScoreLabel = new JLabel("High Score: " + highScore);
		scoreLabel.setForeground(Color.GREEN);
		levelLabel.setForeground(Color.GREEN);
		levelvedUpLabel.setForeground(Color.YELLOW);
		highScoreLabel.setForeground(Color.RED);

		scoreBoard.add(scoreLabel);
		scoreBoard.add(levelLabel);
		scoreBoard.add(levelvedUpLabel);
		scoreBoard.add(highScoreLabel);

		// Side panel
		JPanel sideButtons = new JPanel(sideButtonsLayout);
		// sideButtons.setBackground(Color.BLACK);

		resetButton = new JButton();
		resetButton.setText("Reset");
		settingButton = new JButton();
		settingButton.setText("Settings");

		sideButtons.add(resetButton);
		sideButtons.add(settingButton);

		// Creating the snake
		// snake = new Snake(4,3); // have to implement some sort of menu to take in
		// starting location, i think

		JPanel window = new JPanel();

		window.setLayout(new GridLayout(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT));
		tiles = new SnakeTile[DEFAULT_WINDOW_WIDTH][DEFAULT_WINDOW_HEIGHT];
		moves = new ArrayList<Point>();
		// timer = new Timer(START_DELAY, new MyAnimationAction());
		// timer.start();

		for (int i = 0; i < DEFAULT_WINDOW_WIDTH; i++)
			for (int j = 0; j < DEFAULT_WINDOW_HEIGHT; j++) {
				SnakeTile button = new SnakeTile();
				tiles[i][j] = button;
				button.addActionListener(new SnakeTileButtonListener(j, i));
				window.add(button);
			}

		// Create a bonus tile
		int randomX = numGenerator.nextInt(DEFAULT_WINDOW_WIDTH);
		int randomY = numGenerator.nextInt(DEFAULT_WINDOW_HEIGHT);

		SnakeTile bonusStartTile = tiles[randomX][randomY];
		bonusStartTile.setBonus();
		bonusStartTile.reset();

		// Adding game elements / features
		this.add(scoreBoard, BorderLayout.NORTH);
		this.add(window, BorderLayout.CENTER);
		this.add(sideButtons, BorderLayout.EAST);

		// Adding listeners to our buttons
		resetButton.addActionListener(new ResetButtonListener());
		settingButton.addActionListener(new SettingButtonListener());

		this.addKeyListener(this);
		setFocusable(true);
		requestFocus();
	}

	public void createSnake(int x, int y) {
		this.snake = new Snake(x, y);

		timer = new Timer(START_DELAY, new MyAnimationAction());
		timer.start();

		return;
	}

	private class ResetButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < DEFAULT_WINDOW_WIDTH; i++)
				for (int j = 0; j < DEFAULT_WINDOW_HEIGHT; j++)
					tiles[i][j].hardReset();
			// snake.resetSnake();
			snake = null;
			// snake.lengthReset();
			// timer.restart();
			timer.stop();
			timer = null;
			resetScoreBoard();
			requestFocus();

		}
	}

	private class SettingButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("PRESSED SETTING BUTTON");

			// JFrame frame = new JFrame();
			// JPanel settings = new JPanel();
			SnakeSettingsPanel panel = new SnakeSettingsPanel(speedSettingSelection);
			// panel.setDefaultCloseOperation(JPanel.EXIT_ON_CLOSE);

			// frame.add(settings);
			// frame.setSize(200,200);
			// settings.setVisible(true);
			// System.out.println("SCREEN SHOULD EXIST");
		}

	}

	private class SnakeTileButtonListener implements ActionListener {

		private int x, y;

		public SnakeTileButtonListener(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Button Pressed! Location: " + this.x + ", " + this.y);
			// Construct snake on the tile
			Snake snake = getSnake();
			if (snake == null) {
				createSnake(x, y);
				return;
			}
			System.out.println("Snake is not null");
		}

	}

	private Snake getSnake() {
		return snake;
	}

	private void updateSnake() {

		// setFocusable(true);
		// requestFocus();

		moves.add(snake.move());
		if (snake.isGameOver(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT)) {
			timer.stop();

		} else {
			Point[] snakePoints = snake.getSnake();

			// Throw ArrayOutOfBoundsException
			tiles[snake.getHead().y][snake.getHead().x].visit();

			for (int i = 1; i < snakePoints.length; i++)
				tiles[snakePoints[i].y][snakePoints[i].x].setBackground(Color.WHITE);
			tiles[snake.getPreviousEnd().y][snake.getPreviousEnd().x].reset();
		}
	}

	private void resetScoreBoard() {
		currentLevel = 0;
		gameScore = 0;
		scoreLabel.setText("Score " + gameScore);
		levelLabel.setText("Level " + currentLevel);
	}

	private Point generateRandomPoint() {
		int randomX = numGenerator.nextInt(DEFAULT_WINDOW_WIDTH);
		int randomY = numGenerator.nextInt(DEFAULT_WINDOW_HEIGHT);

		// In this case row represents the y and col represents the x
		Point point = new Point(randomX, randomY);
		return point;
	}

	private boolean verifyBonusSpawn(Snake snake, Point spawnPoint) {
		// Points are stored row, col [so y, x]
		Point[] snakeLocation = snake.getSnake();
		// System.out.printf("There are %d points in the snake\n",
		// snakeLocation.length);
		for (Point point : snakeLocation) {
			// System.out.printf("Comparing %d == %d, %d == %d\n", point.y, spawnPoint.x,
			// point.x, spawnPoint.y);

			if (point.y == spawnPoint.x && point.x == spawnPoint.y) {
				// System.out.printf("Point x: %d, Point y: %d\n", point.y, point.x);
				// System.out.println("ATTEMPTED TO SPAWN ON SNAKE");

				return false;
			}
		}

		return true;
	}

	private void updateScore() {
		Point spawnPoint = null;
		boolean validSpawn = false;
		if (tiles[snake.getHead().y][snake.getHead().x].isBonus()) {
			// Create a bonus tile
			do {
				// System.out.println("Generating a new spawn point!");
				spawnPoint = generateRandomPoint();
				// System.out.printf("Current spawn point x: %d, y: %d\n", spawnPoint.x,
				// spawnPoint.y);
				validSpawn = verifyBonusSpawn(snake, spawnPoint);
			} while (!validSpawn);

			SnakeTile bonusStartTile = tiles[spawnPoint.x][spawnPoint.y];
			bonusStartTile.setBonus();
			bonusStartTile.reset();
		}

		gameScore += tiles[snake.getHead().y][snake.getHead().x].visitTile();
		if (gameScore > highScore) {
			highScore = gameScore;
		}
		levelUp();
		scoreLabel.setText("Score: " + gameScore);
		levelLabel.setText("Level: " + currentLevel);
		highScoreLabel.setText("High Score: " + highScore);
	}

	private void levelUp() {
		int temp = currentLevel;
		currentLevel = gameScore / 7500;
		if (temp < currentLevel) {
			snake.increaseLength();
			levelvedUpLabel.setText("LEVEL UP!");
		} else {
			levelvedUpLabel.setText("");
		}

	}

	// public Point[] getCompletePath(){
	// Point[] path = (Point[]) moves.toArray();
	// return path;
	// }

	public static void main(String[] args) {
		JFrame frame = new JFrame("Snake!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SnakeGUI panel = new SnakeGUI();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}

	private void updateTimer() {
		if (speedSettingSelection[0] == 0) {
			timer.setDelay((int) (START_DELAY * Math.pow(.9, currentLevel)));
		} else {
			timer.setDelay((int) (START_DELAY * Math.pow(speedSettingSelection[0], currentLevel)));
		}
	}

	private class MyAnimationAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			setFocusable(true);
			requestFocus();

			// Must generate snake first
			if (snake == null) {
				return;
			}

			try {
				updateSnake();
				updateScore();
				// updateSnake();
				updateTimer();
			} catch (ArrayIndexOutOfBoundsException exception) {
				System.out.println("Snake is out of bounds");

			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (snake == null) {
			return;
		}

		Point[] snakePath = snake.getSnake();
		Point head, neck;
		if (snakePath.length == 1) { // When the snake is a single nub at the start
			neck = snakePath[0];
		} else {
			neck = snakePath[1];
		}
		head = snakePath[0];

		switch (e.getKeyCode()) {
			case (KeyEvent.VK_W):
				// Going up
				if (!collideWithNeck(head, neck, SnakeInterface.Direction.Up)) {
					snake.changeDirection(SnakeInterface.Direction.Up);
				}
				break;
			case (KeyEvent.VK_A):
				// Going left
				if (!collideWithNeck(head, neck, SnakeInterface.Direction.Left)) {
					snake.changeDirection(SnakeInterface.Direction.Left);
				}
				break;
			case (KeyEvent.VK_S):
				// Going down
				if (!collideWithNeck(head, neck, SnakeInterface.Direction.Down)) {
					snake.changeDirection(SnakeInterface.Direction.Down);
				}
				break;
			case (KeyEvent.VK_D):
				// Going right
				if (!collideWithNeck(head, neck, SnakeInterface.Direction.Right)) {
					snake.changeDirection(SnakeInterface.Direction.Right);
				}

				break;
			default:
				System.out.println("INVALID MOVEMENT");
		}

		// if(KeyEvent.VK_UP == e.getKeyCode() && !(SnakeInterface.Direction.Down ==
		// snake.getDirection())) {
		// snake.changeDirection(SnakeInterface.Direction.Up);
		// }else if(KeyEvent.VK_LEFT == e.getKeyCode() &&
		// !(SnakeInterface.Direction.Right == snake.getDirection())) {
		// snake.changeDirection(SnakeInterface.Direction.Left);
		// }else if(KeyEvent.VK_RIGHT == e.getKeyCode() &&
		// !(SnakeInterface.Direction.Left == snake.getDirection())) {
		// snake.changeDirection(SnakeInterface.Direction.Right);
		// }else if(KeyEvent.VK_DOWN == e.getKeyCode() && !(SnakeInterface.Direction.Up
		// == snake.getDirection())) {
		// snake.changeDirection(SnakeInterface.Direction.Down);
		// }
	}

	private boolean collideWithNeck(Point head, Point neck, SnakeInterface.Direction direction) {

		if (direction == SnakeInterface.Direction.Up) {
			return neck.x == head.x && neck.y == head.y - 1;
		}

		if (direction == SnakeInterface.Direction.Left) {
			return neck.x == head.x - 1 && neck.y == head.y;
		}

		if (direction == SnakeInterface.Direction.Down) {
			return neck.x == head.x && neck.y == head.y + 1;
		}

		if (direction == SnakeInterface.Direction.Right) {
			return neck.x == head.x + 1 && neck.y == head.y;
		}

		return false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	} // These two methods aren't needed

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
