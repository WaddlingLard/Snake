import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
/**
 * This class handles the GUI, snake movement, and score/time.
 * @author brianwu
 *
 */
public class SnakeGUI extends JComponent implements KeyListener{

	private static final int DEFAULT_WINDOW_WIDTH = 18;
	private static final int DEFAULT_WINDOW_HEIGHT = 24;
	private static final int START_DELAY = 555;

	private SnakeTile[][] tiles;
	private ArrayList<Point> moves;

	private Timer timer;
	private Snake snake;
	private int totalScore;
	private int currentLevel;
	private JLabel scoreLabel;
	private JLabel levelLabel;

	public SnakeGUI() {
		setupGUI();
	}

	private void setupGUI() {
		BorderLayout test = new BorderLayout();
		GridLayout scoreElements = new GridLayout(1,2);
		this.setLayout(test);

		snake = new Snake(4,3); // have to implement some sort of menu to take in starting location, i think
		totalScore = 0;
		currentLevel = 0;
		JButton resetButton = new JButton();
		resetButton.setText("Reset");
		JPanel scoreBoard = new JPanel(scoreElements);
		scoreLabel = new JLabel("Score: " + totalScore);
		levelLabel = new JLabel("Level: " + currentLevel);
		JPanel window = new JPanel();
		window.setLayout(new GridLayout(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT));
		tiles = new SnakeTile[DEFAULT_WINDOW_WIDTH][DEFAULT_WINDOW_HEIGHT];
		moves = new ArrayList<Point>();
		timer = new Timer(START_DELAY, new MyAnimationAction());
		timer.start();

		for(int i = 0; i < DEFAULT_WINDOW_WIDTH; i++)
			for(int j = 0; j < DEFAULT_WINDOW_HEIGHT; j++) {
				SnakeTile button = new SnakeTile();
				tiles[i][j] = button;
				window.add(button);
			}
		scoreBoard.add(scoreLabel);
		scoreBoard.add(levelLabel);
		this.add(scoreBoard, BorderLayout.NORTH);
		this.add(window, BorderLayout.CENTER);
		this.add(resetButton, BorderLayout.EAST);
		resetButton.addActionListener(new ResetButtonListener());
		this.addKeyListener(this);
		setFocusable(true);
		requestFocus();
	}

	
	private class ResetButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			for(int i = 0; i < DEFAULT_WINDOW_WIDTH; i++)
				for(int j = 0; j < DEFAULT_WINDOW_HEIGHT; j++)
					tiles[i][j].hardReset();
			snake.resetSnake();
			snake.lengthReset();
			timer.restart();
			resetScoreBoard();
			requestFocus();
		}
	}

	private void updateSnake() {
		moves.add(snake.move());
			if(snake.isGameOver(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT)) {
			timer.stop();
			
		}else {
		Point[] snakePoints = snake.getSnake();
		tiles[snake.getHead().y][snake.getHead().x].visit();
		for(int i = 1; i < snakePoints.length; i++)
			tiles[snakePoints[i].y][snakePoints[i].x].setBackground(Color.WHITE);
		tiles[snake.getPreviousEnd().y][snake.getPreviousEnd().x].reset();
		}
	}

	private void resetScoreBoard() {
		currentLevel = 0;
		totalScore = 0;
		scoreLabel.setText("Score " + totalScore);
		levelLabel.setText("Level " + currentLevel);
	}

	private void updateScore() {
		totalScore += tiles[snake.getHead().y][snake.getHead().x].tileValue();
		levelUp();
		scoreLabel.setText("Score: " + totalScore);
		levelLabel.setText("Level: " + currentLevel);
	}

	private void levelUp() {
		int temp = currentLevel;
		currentLevel = totalScore / 7500;
		if(temp < currentLevel)
			snake.increaseLength();
	}

	public ArrayList<Point> getCompletePath(){

		return null;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Snake!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SnakeGUI panel = new SnakeGUI();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);

	}

	private void updateTimer() {
		timer.setDelay((int) (START_DELAY * Math.pow(.9, currentLevel + 1)));

	}

	private class MyAnimationAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			updateSnake();
			updateScore();
			updateTimer();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(KeyEvent.VK_UP == e.getKeyCode() && !(SnakeInterface.Direction.Down == snake.getDirection())) { 
			snake.changeDirection(SnakeInterface.Direction.Up);
		}else if(KeyEvent.VK_LEFT == e.getKeyCode() && !(SnakeInterface.Direction.Right == snake.getDirection())) {
			snake.changeDirection(SnakeInterface.Direction.Left);
		}else if(KeyEvent.VK_RIGHT == e.getKeyCode() && !(SnakeInterface.Direction.Left == snake.getDirection())) {
			snake.changeDirection(SnakeInterface.Direction.Right);
		}else if(KeyEvent.VK_DOWN == e.getKeyCode() && !(SnakeInterface.Direction.Up == snake.getDirection())) {
			snake.changeDirection(SnakeInterface.Direction.Down);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {} //These two methods aren't needed
	@Override
	public void keyReleased(KeyEvent e) {}
}
