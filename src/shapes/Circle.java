package shapes;

import java.awt.Graphics2D;

import ui_elements.ScreenPoint;

public class Circle extends FilledShape {
	private int radius;
	private int posX;
	private int posY;

	public Circle(String id, int posX, int posY, int radius) {
		super(id);
		this.posX = posX;
		this.posY = posY;
		this.radius = radius;
	}

	public Circle(String id, ScreenPoint center, int radius) {
		this(id,center.x,center.y,radius);
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		g.drawArc(posX - radius, posY - radius, 2 * radius, 2 * radius, 0, 360);
		if (isFilled()) {
			g.setColor(getFillColor());
			g.fillOval(posX - radius, posY - radius, 2 * radius, 2 * radius);
		}
	}

	@Override
	public boolean isInArea(int x, int y) {
		return (Math.sqrt(Math.pow(x - posX, 2) + Math.pow(y - posY, 2)) < radius);
	}

	@Override
	public void move(int dx, int dy) {
		this.posX += dx;
		this.posY += dy;
	}

	@Override
	public void moveToLocation(int x, int y) {
		this.posX = x;
		this.posY = y;
	}

}
