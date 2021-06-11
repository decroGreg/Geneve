package ch.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = -5535182304165187161L;
	
	static final int WIDTH=640;

	static final int HEIGHT=WIDTH/12*9;

	private Thread thread;
	private boolean running;
	private Handler handler;
	private HUD hud;

	//private Menu menu;
	//private Random r = new Random();
	//public Spawner spawner;
	public static int score = 0;
	//private static GameStates state;
	public static int level=0;
	public Game() {
		handler = new Handler();
		handler.addGameObject(new Player(ID.PLAYER, WIDTH/2,HEIGHT*4/5 , handler));
		handler.level(level,true);
		//handler.addGameObject(new FatEnnemy(ID.ENNEMY, (int)(WIDTH*.5f-30), (int)(HEIGHT*.5f), handler, Color.BLUE, 50, 50, 2));
		new Window(WIDTH, HEIGHT, "Super Game", this);
		this.addKeyListener(new KeyInput(handler));
		//menu = new Menu();
		//this.addMouseListener(menu);
		hud = new HUD(ID.HUD, 15, 15, handler,this);
		handler.addGameObject(hud);
		//spawner = new Spawner(handler);
		//state = GameStates.Menu;
	}
	public static int nextLevel() {
		level++;
		return level;
	}
	
	public static void main(String[] args) {
		new Game();
	}
	/*
	public static void setGameState(GameStates _state){
		state = _state;
	}*/

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running)
				render();
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
			}
		}
		stop();
	}
	public void die() {
		System.out.println(running);
		running=!running;
		try {
			Thread.sleep(2000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		/*
		if (state == GameStates.Game){*/
			handler.render(g);
			//hud.render(g);
		/*}else
			menu.render(g);
		*/
		g.dispose();
		bs.show();
	}

	private void tick() {
		handler.tick();
		/*
		hud.tick();
		spawner.tick();*/
        score++;
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	private void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}