package ch.game;

import java.awt.*;



public class Ennemy extends GameObject {

    private int width, height;
    private Color color;
    private int damage;

    public Ennemy(ID _id, int _posX, int _posY, Handler _handler, Color _color, int _width, int _height, int _damage) {
        super(_id, _posX, _posY, _handler);
        this.color = _color;
        this.width = _width;
        this.height = _height;
        this.setDamage(_damage);
        velY=1;
        posY=0;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public void tick() {
        posY += velY;
        if(posY>Game.HEIGHT) {
        	HUD.setHealth(33);
        	this.getHandler().removeGameObject(this);
        	this.getHandler().level(Game.level,false);
        	System.out.println("remove Enneùy from handler");
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(posX, posY, width, height);
    }

    @Override
    public Shape getBounds() {
        return new Rectangle(posX, posY, width, height);
    }



	@Override
	public void onCollision(GameObject tmp) {
		// TODO Auto-generated method stub
		
	}
    
}