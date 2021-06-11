package ch.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;

public class Bullet extends GameObject{

	private Shape cercle;
	
	public Bullet(ID _id, int _posX, int _posY, Handler _handler) {
		super(_id, _posX, _posY, _handler);
	}

	
	public void tick() {
		// TODO Auto-generated method stub
		posY+=velY;
		if(posY<0) {
			this.getHandler().removeGameObject(this);
		}
		collision(ID.ENNEMY);
	}


	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.YELLOW);
		g.fillOval(posX, posY, 10, 10);
	}


	@Override
	public Shape getBounds() {
		// TODO Auto-generated method stub
		return new Ellipse2D.Double(posX,posY,10,10);
	}


	@Override
	public void onCollision(GameObject tmp) {
		// TODO Auto-generated method stub
		this.getHandler().removeGameObject(this);
		this.getHandler().removeGameObject(tmp);
		this.getHandler().level(Game.level,true);
	}
	
}
