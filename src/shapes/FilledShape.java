package shapes;

import java.awt.Color;

public abstract class FilledShape extends Shape {

	public FilledShape(String id) {
		super(id);
	}

	private Color fillColor;
	private boolean isFilled = false;

	public void setFillColor (Color color) {
		this.fillColor = color;
	}
	
	public Color getFillColor() {
		return this.fillColor;
	}

	public void setIsFilled(boolean isFilled) {
		this.isFilled = isFilled;
	}
	
	public boolean isFilled() {
		return isFilled;
	}
	
	
}
