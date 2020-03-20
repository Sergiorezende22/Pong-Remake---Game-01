package pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Score {
	public static int enemyScore = 0;
	public static int playerScore = 0;
	
	public void update() {
	}
	
	public void render(Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.setColor(Color.RED);
		g.drawString(Integer.toString(enemyScore), Game.WIDTH - 15, Game.HEIGHT/2 - 15);
		g.setColor(Color.BLUE);
		g.drawString(Integer.toString(playerScore), Game.WIDTH - 15, Game.HEIGHT/2 + 24);
	}
	
	
	
}
