package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import ui_elements.ScreenPoint;


public class Rectangle extends FilledShape {
	private int posX, posY, width, height;
	private Color leftSideColor = super.color, rightSideColor = super.color, upperSideColor = super.color,
			lowerSideColor = super.color;

	public Rectangle(String id, int x, int y, int width, int height) {
		super(id);
		this.posX = x;
		this.posY = y;
		this.width = width;
		this.height = height;
	}

	public Rectangle(String id, ScreenPoint leftTop, int width, int height) {
		this(id, leftTop.x, leftTop.y, width, height);
	}

	public void setCustomSideColors(Color leftSideColor, Color rightSideColor, Color upperSideColor,
			Color lowerSideColor) {
		this.leftSideColor = leftSideColor;
		this.rightSideColor = rightSideColor;
		this.upperSideColor = upperSideColor;
		this.lowerSideColor = lowerSideColor;
	}

	public void setColor(Color color) {
		super.setColor(color);
		this.leftSideColor = color;
		this.rightSideColor = color;
		this.upperSideColor = color;
		this.lowerSideColor = color;
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public void draw(Graphics2D g) {
		if (isFilled()) {
			g.setColor(getFillColor());
			g.fillRect(posX, posY, width, height);
		}

		g.setColor(upperSideColor);
		g.setStroke(new BasicStroke(super.weight));
		g.drawLine(posX, posY, posX + width, posY);

		g.setColor(lowerSideColor);
		g.setStroke(new BasicStroke(super.weight));
		g.drawLine(posX, posY + height, posX + width, posY + height);

		g.setColor(leftSideColor);
		g.setStroke(new BasicStroke(super.weight));
		g.drawLine(posX, posY, posX, posY + height);

		g.setColor(rightSideColor);
		g.setStroke(new BasicStroke(super.weight));
		g.drawLine(posX + width, posY, posX + width, posY + height);

	}

	@Override
	public boolean isInArea(int x, int y) {
		return (x >= posX && x <= posX + width && y > posY && y < posY + height);
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
