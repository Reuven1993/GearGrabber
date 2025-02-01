package shapes;

import java.awt.Graphics2D;

import ui_elements.ScreenPoint;

public class Arc extends FilledShape {
	private int radius;
	private int posX;
	private int posY;
	private int startAngle;
	private int angleToAdd;

	/**
	 * 
	 * @param id
	 * @param posX       The center of the circle
	 * @param posY       The center of the circle
	 * @param radius     The radius of the circle
	 * @param startAngle The angle you want to start drawing the arc from (Angles
	 *                   work Just like in the Unit circle)
	 * @param angleToAdd How many degrees you want to add to the starting angle (if
	 *                   startAngle is 90 and angleToTdd is 180 circle will look
	 *                   like this -> (| ))
	 */
	public Arc(String id, int posX, int posY, int radius, int startAngle, int angleToAdd) {
		super(id);
		this.posX = posX;
		this.posY = posY;
		this.radius = radius;
		this.startAngle = startAngle;
		this.angleToAdd = angleToAdd;
	}

	public Arc(String id, ScreenPoint center, int radius, int startAngle, int angleToAdd) {
		super(id);
		this.posX = center.x;
		this.posY = center.y;
		this.radius = radius;
		this.startAngle = startAngle;
		this.angleToAdd = angleToAdd;
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

	public int getStartAngle() {
		return startAngle;
	}

	public void setStartAngle(int startAngle) {
		this.startAngle = startAngle;
	}

	public int getAngleToAdd() {
		return angleToAdd;
	}

	public void setAngleToAdd(int angleToAdd) {
		this.angleToAdd = angleToAdd;
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		g.drawArc(posX - radius, posY - radius, 2 * radius, 2 * radius, startAngle, angleToAdd);

		g.drawLine(posX, posY, posX + (int) (radius * Math.cos(Math.toRadians(startAngle))),
				posY - (int) (radius * Math.sin(Math.toRadians(startAngle))));
		g.drawLine(posX, posY, posX + (int) (radius * Math.cos(Math.toRadians(startAngle + angleToAdd))),
				posY - (int) (radius * Math.sin(Math.toRadians(startAngle + angleToAdd))));

		if (isFilled()) {
			g.setColor(getFillColor());
			g.fillArc(posX - radius, posY - radius, 2 * radius, 2 * radius, startAngle, angleToAdd);
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
