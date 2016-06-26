package snake;


import java.awt.Color;
import java.awt.Point;
import javax.swing.JOptionPane;

import snake.Snake.Body;

public class Food extends Thread{
	
		Color c;
		Point p;
		int score;
		static boolean speed;
		public Food(int i) {
			if (i == 1) {
				this.c = Color.YELLOW;
				this.p = null;
				this.score = 1;
			}
			
			
			if (i == 2) {
				this.c = Color.RED;
				this.p = null;
				this.score = 5;
			}
			if (i == 3) {
				this.c = Color.GREEN;
				this.p = null;
				this.score = (int) (100 * Math.random() - 50);
			}
			if (i == 4) {
				this.c = Color.BLACK;
				this.p = null;
				this.score = 0;
			}
			
	}

	
	
	

}