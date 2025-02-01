package shapes;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class RotatedJLabel extends JLabel {
	
	private double rotation;
	
	public RotatedJLabel(ImageIcon imageIcon) {
		super(imageIcon);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D gx = (Graphics2D) g;
		gx.rotate(Math.toRadians(rotation),getWidth()/2, getHeight()/2);
		super.paintComponent(g);
		
	}

	/**
	 * @return the rotation
	 */
	public double getRotation() {
		return rotation;
	}

	/**
	 * @param rotation the rotation to set
	 */
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

}
