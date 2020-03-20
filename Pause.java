package pong;

import java.awt.Color;
import java.awt.Graphics;

public class Pause {	
	public void render(Graphics g) {
		g.setColor(new Color(19, 19, 19, 200));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
	}
	
	public void update() {
		
	}
}
