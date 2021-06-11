package ch.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;


public class Player extends GameObject {

	private final int width = 32, height = 32;

	public Player(ID _id, int _posX, int _posY, Handler _handler) {
		super(_id, _posX, _posY, _handler);
	}

	@Override
	public void tick() {
		posX += velX;
		posY += velY;

		posX = Utils.Clamp(posX, 0, Game.WIDTH - (int) (width + width * .5f));
		posY = Utils.Clamp(posY, 0, Game.HEIGHT - (int) (height * 2.24f));

		//collision(ID.ENNEMY);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(posX, posY, width, height);
	}

	@Override
	public Shape getBounds() {
        return new Rectangle(posX, posY, width, height);
	}



	@Override
	public void onCollision(GameObject other) {
	}
}