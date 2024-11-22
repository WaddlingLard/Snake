import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;

public class SnakeTile extends JButton{

	private int numVisited;
	private Random random;
	private boolean isBonus;

	// Constants
	private static final int[] POINT_VALUE = {500, 350, 200, 150, 100, 50, 25, 10}; 
	private static final double BONUS_TILE_CHANCE = 0.05;
	private static final int BONUS_MULTIPLIER = 5;
	private static final int TILE_SIZE = 50;
	
	public SnakeTile() {
		// A randomized seed for Bonus Tile Generation
		random = new Random(System.currentTimeMillis());
		reset();
	}
	
	public void visit() {
		setText(++numVisited + "");
		setBackground(Color.WHITE);
	}
	
	public int getNumVisited() {
		return numVisited;
	}
	
	public void reset() {
		// double chance = random.nextDouble();
		double chance = random.nextDouble(1.0);
		System.out.println(chance);
		if (BONUS_TILE_CHANCE >= chance) {
			isBonus = true;
			setBackground(Color.RED);
		} else {
			isBonus = false;
			setBackground(Color.BLACK);
		}
		setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
	}
	
	public void hardReset() {
		numVisited = 0;
		setBackground(Color.lightGray);
		setText("");
	}
	
	public int tileValue() {
		boolean valueFound = false;
		int value = 0;
		int counter = 0;
		
		while(valueFound == false) {
			if(getNumVisited() == counter) {
				value = POINT_VALUE[counter - 1];
				valueFound = true;
			}else if(getNumVisited() > POINT_VALUE.length) {
				value = 5;
				valueFound = true;
			}
			counter++;
		}

		return value;
	}
	
	public void onTile() { // Don't know if I need this, i just realized this
		
	}
}
