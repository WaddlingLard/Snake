import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class SnakeTile extends JButton{

	private int numVisited;
	private static int[] pointValue = {500, 350, 200, 150, 100, 50, 25, 10}; 
	
	
	public SnakeTile() {
		reset();
	}
	
	public void visit() {
		numVisited += 1;
		setText(numVisited + "");
		setBackground(Color.WHITE);
	}
	
	public int getNumVisited() {
		return numVisited;
	}
	
	public void reset() {
		setBackground(Color.lightGray);
		setPreferredSize(new Dimension(50,40));
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
				value = pointValue[counter - 1];
				valueFound = true;
			}else if(getNumVisited() > pointValue.length) {
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
