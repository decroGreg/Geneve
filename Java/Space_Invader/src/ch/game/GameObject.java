package ch.game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class GameObject {
	
	public ID id;
	public int posX, posY;
	public int velX, velY;
	
	private Handler handler;
	
	public GameObject(ID _id, int _posX, int _posY, Handler _handler) {
		this.id = _id;
		this.posX = _posX;
		this.posY = _posY;
		this.handler = _handler;
	}
	public Handler getHandler() {
		return this.handler;
	}

	public abstract Shape getBounds();
	
	public abstract void tick();
	
	public abstract void render(Graphics g);

	public void collision(ID _collideWithID){
		// tester pour tous les GameObject du jeu si y'a une collision avec nous
		for (int i = 0; i < handler.gameObjects.size(); i++) {
			GameObject tmp = handler.gameObjects.get(i);
			
			if (tmp.id == _collideWithID){
				if (tmp.getBounds().intersects(posX, posY, 10, 10)){
					onCollision(tmp);
				}
			}
		}
		
	}

	public abstract void onCollision(GameObject tmp);
}