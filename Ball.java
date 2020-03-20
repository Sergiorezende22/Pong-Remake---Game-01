package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball {

	public double x, y;
	public static final int WIDTH = 4, HEIGHT = 4;
	
	public double dx, dy;
	public double speed = 1.8;
	
	
	public Ball(double x, double y) {
		this.x = x;
		this.y = y;

		getNewAngle();
	}
	
	public void update() {
		if(x+dx*speed + WIDTH >= Game.WIDTH || x+dx*speed < 0) {
			dx*=-1;
		}
		
		if(y >= Game.HEIGHT - WIDTH) {
			Score.enemyScore++;
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			new Game();
		}
		else if(y < 0) {
			Score.playerScore++;
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			new Game();
		}
		
		Rectangle bounds = new Rectangle((int) (x+(dx*speed)), (int) (y+(dy*speed)), WIDTH, HEIGHT);
		Rectangle boundsPlayer = new Rectangle(Game.player.x, Game.player.y, Game.player.WIDTH, Game.player.HEIGHT);
		Rectangle boundsEnemy = new Rectangle((int) Game.enemy.x, (int) Game.enemy.y, Game.enemy.WIDTH, Game.enemy.HEIGHT);
		
		if(bounds.intersects(boundsPlayer)) {
			getNewAngle();
			if(y + HEIGHT > Game.player.y) {
				dx *= -1;
			}
			if(dy > 0)
				dy *= -1;
		}
		else if(bounds.intersects(boundsEnemy)) {
			getNewAngle();
			if(y < Game.enemy.y + Game.enemy.WIDTH) {
				dx *= -1;
			}
			if(dy < 0)
				dy *= -1;
		}
		
		x += dx*speed;
		y += dy*speed;
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect((int) x, (int) y, WIDTH, HEIGHT);
	}
	
	
	public void getNewAngle() {
		int angle = new Random().nextInt(120 - 45) + 45;
		dx = Math.cos(Math.toRadians(angle));
		dy = Math.sin(Math.toRadians(angle));
	}
	
}
 