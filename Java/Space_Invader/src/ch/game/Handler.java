package ch.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class Handler {
	LinkedList<GameObject> gameObjects = new LinkedList<GameObject>();
	LinkedList<GameObject> ennemy = new LinkedList<GameObject>();
	
	public void tick() {
		for (int i = 0; i < gameObjects.size(); i++) {
			gameObjects.get(i).tick();
		}
	}

	public void render(Graphics g) {
		for (int i = 0; i < gameObjects.size(); i++) {
			gameObjects.get(i).render(g);
		}
	}
	
	public void addGameObject (GameObject go) {
		this.gameObjects.add(go);
		if(go.id==ID.ENNEMY) {
			ennemy.add(go);
		}
	}
	
	public void removeGameObject (GameObject go) {
		this.gameObjects.remove(go);
		this.ennemy.remove(go);
		
		
	}
	
	public void level(int j,boolean flag) {
		
		if(ennemy.size()!=0)return;
		if(flag)Game.nextLevel();
		threadDraw draw=new threadDraw();
		draw.sethandler(this);
		draw.start();
		/*
		j++;
		Random r=new Random();
		for(int i=0;i<j;i++) {
			System.out.println("modulo="+i%j);
			this.addGameObject(new Ennemy(ID.ENNEMY, r.nextInt(Game.WIDTH-50), r.nextInt(Game.HEIGHT-50), this, Color.RED, 16, 16, 2));
		}
		*/
		
	}
	private class threadDraw extends Thread{
		private Handler handler;
		public void sethandler(Handler handler) {
			this.handler=handler;
		}
		
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			int j=Game.level;
			System.out.println("level "+j);
			Random r=new Random();	
			for(int i=0;i<j;i++) {
				System.out.println("modulo="+i%j%3);
				if(i%j%3==0) {//si il y a x ennemy sur une ligne alors sleep pour mettre des ennemys sur des lignes différents, içi max 3 par ligne
					try {
						Thread.sleep(1000);//dodo du thread->c'est ça qui donne l'espace
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				handler.addGameObject(new Ennemy(ID.ENNEMY, r.nextInt(Game.WIDTH-50), r.nextInt(Game.HEIGHT-50), handler, Color.RED, 16, 16, 2));
			}
			
			System.out.println("nb ennemi"+ennemy.size());
		}
	}
}



