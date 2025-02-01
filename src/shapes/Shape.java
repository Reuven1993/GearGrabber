package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import base.ShapeListener;

public abstract class Shape {
	protected boolean isDashed = false;

	public enum STATUS {
		HIDE, SHOW
	}

	private final String id;
	private STATUS status;
	protected Color color = Color.red;
	protected int weight = 1;
	private int zOrder = 1;
	private boolean isDraggable = true;

	private ShapeListener shapeListener = null;

	public Shape(String id) {
		this.id = id;
		this.status = STATUS.SHOW;
	}

	public String getId() {
		return id;
	}

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void setzOrder(int zOrder) {
		this.zOrder = zOrder;
	}

	public int getzOrder() {
		return zOrder;
	}

	public void setShapeListener(ShapeListener listener) {
		this.shapeListener = listener;
	}

	public ShapeListener getshapeListener() {
		return shapeListener;
	}

	/**
	 * @param isDashed the isDashed to set (Dashed line = "mekuvkav")
	 */
	public void setDashed(boolean isDashed) {
		this.isDashed = isDashed;
	}

	public void setDraggable(boolean isDraggable) {
		this.isDraggable = isDraggable;
	}

	public boolean isDraggable() {
		return isDraggable;
	}

	/*
	 * The following methods must be implemented by the classes (specific shapes)
	 * that
	 * extend this class
	 */
	public void draw(Graphics2D g) {
		g.setColor(this.color);
		g.setStroke(new BasicStroke(weight));
	}

	public abstract void move(int dx, int dy);

	public abstract void moveToLocation(int x, int y);

	public abstract boolean isInArea(int x, int y);

}
