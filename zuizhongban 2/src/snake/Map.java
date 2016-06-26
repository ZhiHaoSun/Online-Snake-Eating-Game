package snake;

import java.awt.Color;

public class Map {
	static int height, width;
	static double[] x = { -width, -width, width, width };
	static double[] y = { -height, height, height, -height };

	public static void draw(boolean b, Snake[] snake) {
		StdDraw.clear();
		StdDraw.setPenColor(Color.lightGray);
		StdDraw.filledSquare(0, 0, 50);
		Game.drawFood();
		if (b)
			StdDraw.setPenColor(Color.ORANGE);
		else
			StdDraw.setPenColor(Color.BLACK);
		
		StdDraw.setPenRadius(0.02);
		StdDraw.polygon(x, y);
		StdDraw.setPenRadius();
		StdDraw.setPenColor(Color.red);
		StdDraw.textLeft((double) -width, (double) height + 1, "score:"
				+ Game.result);
		StdDraw.text(0.0, (double) height + 1, "Grade:"
				+ (Game.result / 10 > 25 ? 25 : Game.result / 10));
		if (Game.special.p != null)
			StdDraw.textRight((double) width, (double) height + 1, "time:"
					+ Game.special.score);
		// if(Game.grass.p!=null)
		// StdDraw.text(10.0, (double)height+1, "grass:"+Game.grass.score);
		
		StdDraw.setPenColor(Game.general1.c);
		StdDraw.filledSquare(-width, height + 2.3, 0.5);
		StdDraw.textLeft(0.5 - width, height + 2.3, "general");
		StdDraw.setPenColor(Game.general2.c);
	
		
		StdDraw.setPenColor(Game.special.c);
		StdDraw.filledSquare(-width / 2, height + 2.3, 0.5);
		StdDraw.textLeft(0.5 - width / 2, height + 2.3, "special");
		StdDraw.setPenColor(Game.grass.c);
		StdDraw.filledSquare(0, height + 2.3, 0.5);
		StdDraw.textLeft(0.5, height + 2.3, "grass");
		
		StdDraw.setPenColor(Game.rock1.c);
		
		StdDraw.filledSquare(width / 2, height + 2.3, 0.5);
		StdDraw.textLeft(0.5 + width / 2, height + 2.3, "rock");
		StdDraw.setPenColor(Game.rock2.c);
		
		StdDraw.setPenColor(Game.rock3.c);
		
		snake[0].drawSnake();
		snake[1].drawSnake();
	}

	
	public static void drawmap(int wt, int ht) {
		width = wt / 2;
		height = ht / 2;
		x[0] = -width;
		x[1] = -width;
		x[2] = width;
		x[3] = width;
		y[0] = -height;
		y[1] = height;
		y[2] = height;
		y[3] = -height;
		int w = width * 600 / (height + 1);
		int h = 600;
		StdDraw.setCanvasSize(w, h);
		StdDraw.setXscale(-width, width);
		StdDraw.setYscale(-height, height + 2);
	}
}
	