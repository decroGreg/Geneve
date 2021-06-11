package ch.game;

import java.awt.*;


public class HUD extends GameObject {
    public static Game game = new Game();
    public HUD(ID _id, int _posX, int _posY, Handler _handler,Game game) {
        super(_id, _posX, _posY, _handler);
        this.game=game;
    }

    public static int HEALTH = 100;

    private static int BAR_WIDTH = 200;

    public void render(Graphics g){
        // barre de vie
        // fond de la barre de vie
        g.setColor(Color.GRAY);
        g.fillRect(posX, posY, BAR_WIDTH, 20);

        // la vie (qui diminue)
        g.setColor(Color.GREEN);
        g.fillRect(posX, posY, HEALTH*2, 20);

        // bordure (pour faire joli)
        g.setColor(Color.WHITE);
        g.drawRect(posX, posY, BAR_WIDTH, 20);
    }

    public void tick(){

    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public void onCollision(GameObject other) {
    }

    public static void setHealth(int _health){
        HEALTH -= _health;
        if(HEALTH<=0) {
        	game.die();
        }
        HEALTH = Utils.Clamp(HEALTH, 0, BAR_WIDTH);
    }
}