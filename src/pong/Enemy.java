package pong;

import java.awt.Color;
import java.awt.Graphics;

public class Enemy {

	public double x, y; 
	public static final int WIDTH = 40, HEIGHT = 5;
	
	public Enemy(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	
	public void update() {
		if(Game.ball.dy < 0)
			x += (Game.ball.x - x) * 0.1;
		
		if(x + WIDTH > Game.WIDTH) {
			x = Game.WIDTH - WIDTH;
		}
		else if(x < 0) {
			x = 0;
		}
			
	}
	
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int) x, (int) y, WIDTH, HEIGHT);
	}
	
	
}
 