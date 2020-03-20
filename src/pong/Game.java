package pong;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener{
	private boolean isRunning;
	private boolean isPaused;
	private static boolean isReady;
	private int frames;
	private int[] fontSizes;
	private int currentFont;
	
	public static final int WIDTH = 160;
	public static final int HEIGHT = 120;
	public static final int SCALE = 5;
	
	public static Thread thread;
	
	public BufferedImage layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	public static Player player;
	public static Enemy enemy;
	public static Ball ball;
	public Pause pause;
	public static Score score;
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		this.addKeyListener(this);
		player = new Player(WIDTH/2 - Player.WIDTH/2, HEIGHT - Player.HEIGHT);
		enemy = new Enemy(WIDTH/2 - Enemy.WIDTH/2, 0);
		ball = new Ball(WIDTH/2 - Ball.WIDTH/2 , HEIGHT/2 - Ball.HEIGHT/2);
		pause = new Pause();
		score = new Score();
		isReady = false;
		fontSizes = new int[2];
		fontSizes[0] = 11;
		fontSizes[1] = 12;
		currentFont = 0;
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.initFrame();
		game.start();
	}
	
	public void update() {
		if(!isPaused && isReady) {
			player.update();
			enemy.update();
			ball.update();
		}
		else if(isPaused) {
			pause.update();
		}
		else if(!isReady) {
			if(frames % 30 == 0) {
				currentFont++;
				if(currentFont > 1)
					currentFont = 0;
			}
		}

			
			
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;	
		}
		Graphics g = layer.getGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, HEIGHT/2 , WIDTH, 1);
		
		if(!isReady) {
			g.setFont(new Font("Arial", Font.BOLD, fontSizes[currentFont]));
			g.setColor(Color.YELLOW);
			g.drawString("Ready?", 63, HEIGHT/2 - 9);			
		}

		player.render(g);
		ball.render(g);
		enemy.render(g);
		score.render(g);	
		
		if(isPaused)
			pause.render(g);

		g = bs.getDrawGraphics();
		g.drawImage(layer, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
	
		bs.show();
	}
	
	
	public synchronized void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000/ amountOfTicks;
		double delta = 0;
		double timer = System.currentTimeMillis();
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				update();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}
		stop();
	}

	
	public void initFrame() {
		JFrame frame = new JFrame("Pong");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_ESCAPE && isPaused) {
			isPaused = false;
		}
		else if(e.getKeyChar() == KeyEvent.VK_ESCAPE && !isPaused) {
			isPaused = true;
			isReady = false;
		}
		else if(e.getKeyChar() == KeyEvent.VK_ENTER && !isReady) {
			isReady = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
	}

	
	public void keyTyped(KeyEvent e) {		
	}	
}
