package shapes;

import java.awt.BorderLayout;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;


public class Image extends Shape {
	
	private RotatedJLabel img;
	private int rotation;	// In degrees
	private TextLabel textLabel;
	private int width;
	private int height;
	private int posX;
	private int posY;
	private int boundingWidth;
	private int boundingHeight;
	
	
	public Image(String id, String src, int width, int height, int posX, int posY) {
		super(id);
		setImage(src, width, height);
		this.posX = posX;
		this.posY = posY;
		setRotation(0);
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
	public RotatedJLabel getImg() {
		return img;
	}
	
	public void setImage(String src, int width, int height)
	{
		RotatedJLabel label;
		label = new RotatedJLabel(new ImageIcon(src));
		label.setLayout(new BorderLayout());
		label.setRotation(rotation);
		this.img = label;
		this.width = width;
		this.height = height;
		this.boundingWidth = width;
		this.boundingHeight = height;			
		// If there is an associated text, re-add it.
		if (textLabel != null) {
			addTextLabel(textLabel);
		}
	} 
	

	public double getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
		img.setRotation(rotation);
		double rads = Math.toRadians(rotation); // convert rotation to radians for trigonometric functions
		int newBoundingHeight = (int) (getWidth()*Math.abs(Math.sin(rads))+getHeight()*Math.abs(Math.cos(rads)));
		int newBoundingWidth = (int) (getWidth()*Math.abs(Math.cos(rads))+getHeight()*Math.abs(Math.sin(rads)));
		posX -= (newBoundingWidth-boundingWidth)/2;
		posY -= (newBoundingHeight-boundingHeight)/2;
		boundingWidth = newBoundingWidth;
		boundingHeight = newBoundingHeight;
	}

	public void addTextLabel(TextLabel textLabel) {
		this.textLabel = textLabel;
		img.add(textLabel.getLabel());
		draw(null);
	}
	
	public void removeTextLabel() {
		//minimize the old text label
		textLabel.getLabel().setBounds(0, 0, 0, 0);
		img.remove(textLabel.getLabel());
		this.textLabel = null;
		draw(null);
	}

	
	@Override
	public void draw(Graphics2D g) {
		getImg().setBounds(getPosX(), getPosY(), boundingWidth, boundingHeight);
		if (textLabel != null) {
			textLabel.getLabel().setBounds(textLabel.getPosX(), textLabel.getPosY(), textLabel.getWidth(), textLabel.getHeight());
		}
	}
	
	@Override
	public boolean isInArea(int x, int y) {
		return (x >= posX && x<= posX + width && y >= posY && y<= posY + height );
	}
	
	@Override
	public void move(int dx, int dy) {
		this.posX += dx;
		this.posY += dy;
		draw(null);
	}
	
	@Override
	public void moveToLocation(int x, int y) {
		this.posX = x;
		this.posY = y;
		draw(null);
	}
	
	
}
