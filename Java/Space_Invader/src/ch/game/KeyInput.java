package ch.game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

	private static final int Speed = 4;
	private Handler handler;
	private GameObject currentGamObject;
	private boolean[] directionsFlag = new boolean[3];

	public KeyInput(Handler _handler) {
		this.handler = _handler;
		// RIGHT
		directionsFlag[0] = false;
		// LEFT
		directionsFlag[1] = false;
	}

	public void keyPressed(KeyEvent e) {

		for (int i = 0; i < handler.gameObjects.size(); i++) {

			currentGamObject = handler.gameObjects.get(i);
			if (currentGamObject.id == ID.PLAYER) {
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					currentGamObject.velX = Speed;
					directionsFlag[0] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					currentGamObject.velX = -Speed;
					directionsFlag[1] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					Bullet Bullet=new Bullet(ID.BULLET,currentGamObject.posX,currentGamObject.posY,handler);
					Bullet.velY=-Speed;
					handler.addGameObject(Bullet);
				}
			}
		}

	}

	public void keyReleased(KeyEvent e) {
		for (int i = 0; i < handler.gameObjects.size(); i++) {

			currentGamObject = handler.gameObjects.get(i);
			if (currentGamObject.id == ID.PLAYER) {

				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					directionsFlag[0] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					directionsFlag[1] = false;
				}
				if (!directionsFlag[0] && !directionsFlag[1]){
					currentGamObject.velX = 0;
				}

			}
		}
	}

}