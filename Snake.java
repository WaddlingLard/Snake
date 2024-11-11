import java.awt.Point;
import java.util.ArrayList;
/**
 * This class controls the snake and handles all of the data and methods to process movement and correctness.
 * @author brianwu
 *
 */

public class Snake implements SnakeInterface {

	//Declaration of fields
	private int tailLength;
	private ArrayList<Point> path;
	private Point pointOfOrigin;
	private Direction movement;

	/**
	 * This is the main constructor that the program uses, I haven't implemented the others,  but they work similarly
	 * @param startingX X location of starting point
	 * @param startingY Y location of starting point
	 */
	public Snake(int startingX, int startingY) { 
		tailLength = DEFAULT_START_TAIL_LENGTH;
		pointOfOrigin = new Point(startingX, startingY);
		path = new ArrayList<Point>();
		path.add(new Point(startingX - 3, startingY));
		path.add(new Point(startingX - 2, startingY));
		path.add(new Point(startingX - 1, startingY));
		path.add(new Point(startingX, startingY));
		movement = Direction.Right;
	}

	public Snake(Point startingPoint) {
		tailLength = DEFAULT_START_TAIL_LENGTH;
		path = new ArrayList<Point>();
		path.add(new Point(startingPoint.x - 1 , startingPoint.y));
		path.add(new Point(startingPoint.x - 1, startingPoint.y));
		path.add(new Point(startingPoint.x - 1, startingPoint.y));
		path.add(startingPoint);
		movement = Direction.Right;
	}

	public Snake(Point startingPoint, int tailLength) { //This constructor is flawed and I'm unsure how to make it work
		this.tailLength = tailLength;
		path = new ArrayList<Point>();
		path.add(startingPoint);
		movement = Direction.Right;
	}
	
	/**
	 * This method resets the snake back to its original position
	 */
	public void resetSnake() {
		path.clear();
		path.add(new Point(pointOfOrigin.x - 3, pointOfOrigin.y));
		path.add(new Point(pointOfOrigin.x - 2, pointOfOrigin.y));
		path.add(new Point(pointOfOrigin.x - 1, pointOfOrigin.y));
		path.add(new Point(pointOfOrigin.x, pointOfOrigin.y));
	}
	
	/**
	 * This method returns the end of the array which should be the head of the snake
	 * @return Point it returns the head of the snake
	 */
	@Override
	public Point getHead() {
		return path.get(path.size() - 1); 
	}

	/**
	 * This method gets the point before all of the entire body of the snake
	 * @return Point this the point of the snake after the whole body
	 */
	@Override
	public Point getPreviousEnd() { //I think this works
		return path.get(path.size() - (tailLength + 1));
	}

	/**
	 * Returns an array of all the points that the snake resides in
	 * @return Point[] the array of all the points
	 */
	@Override
	public Point[] getSnake() {
		int j;
		Point[] snake = new Point[tailLength]; //This method should work
		for(j = 0; j < tailLength; j++)
			snake[j] = path.get((path.size() - 1) - j);

		return snake;
	}

	/**
	 * This method is a getter for the tailLength
	 * @return int tailLength value
	 */
	public int getTailLength() {
		return this.tailLength;
	}

	/**
	 * This method is a getter for the NumLocationsVisited, not really sure for what its purpose is
	 * @return int all the locations
	 */
	@Override
	public int getNumLocationsVisited() {
		return path.size(); //Seems pretty simple, not sure if this is how to do it
	}

	/**
	 * This method sets the direction of the snake
	 */
	@Override
	public void changeDirection(SnakeInterface.Direction direction) {
		movement = direction;		
	}

	/**
	 * This method is a getter of the direction
	 * @return Direction Up, Down, Left, Right
	 */
	public SnakeInterface.Direction getDirection(){ //This method could allow the GUI to let the snake change direction with the changeDirection method
		return this.movement;
	}

	/**
	 * This method moves the snake by adding on points to the array 
	 * @return Point
	 */
	@Override
	public Point move() {
		Point newPoint = new Point();
		if(movement == Direction.Up) {
			newPoint = new Point(getHead().x, getHead().y - 1);
		}else if(movement == Direction.Down) {
			newPoint = new Point(getHead().x, getHead().y + 1);
		}else if(movement == Direction.Left) {
			newPoint = new Point(getHead().x - 1, getHead().y);
		}else if(movement == Direction.Right) {
			newPoint =new Point(getHead().x + 1, getHead().y);
		}
			path.add(newPoint);
			return newPoint;
	}

	/**
	 * This method returns whether true or false if it hit itself
	 */
	@Override
	public boolean collisionOccurred() {
		int counter = 0;
		Point headOfSnake = getHead();
		Point[] snakeLocation = getSnake();
		for(int i = 0; i < snakeLocation.length; i++)
			if(i != 0) { 
				if(headOfSnake.x == snakeLocation[i].x && headOfSnake.y == snakeLocation[i].y){
					return true;
				}		
			}
		
		return false;
	}

	/**
	 * This method uses the collisionOccured method and bounds to check whether the game is still going or not
	 */
	@Override
	public boolean isGameOver(int width, int height) {
		Point headPoint = getHead();
		int pointx = headPoint.x;
		int pointy = headPoint.y;
		
		if (pointx < 0 || pointx > height || pointy < 0 || pointy > width  || collisionOccurred()) {
			System.out.println("GAME OVER");
			return true;
		}
		
		return false;
	}

	/**
	 * This method resets the length of the tail
	 */
	public void lengthReset() {
		tailLength = DEFAULT_START_TAIL_LENGTH;
	}
	
	/**
	 * This method increases the length of the tail by one
	 */
	@Override
	public void increaseLength() {
		tailLength++; // This might need more to it. Never mind, it was that easy
	}
	
	
	@Override
	public Point move(SnakeInterface.Direction direction) { //This method isn't needed from the interface
		return null;
	}

}
