package shapes;


import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JLabel;



public class TextLabel extends Shape {
	
	private JLabel txtLbl;	
	private String text = "";
	private int posX;
	private int posY;
	private String fontName = "Ariel";
	private int fontSize = 12;

	
	public TextLabel (String id, String txt, int posX, int posY) {
		super(id);
		txtLbl = new JLabel();
		txtLbl.setText(txt);
		text = txt;
		this.posX = posX;
		this.posY = posY;
	}
	
	private void adjustFont() {
		txtLbl.setFont(new java.awt.Font(fontName, Font.PLAIN, fontSize));
//		txtLbl.setOpaque(true);
//		txtLbl.setBackground(Color.WHITE);
		txtLbl.setForeground(this.getColor());

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
	public JLabel getLabel() {
		return txtLbl;
	}
	
	public void setLabel(JLabel lbl)
	{
		txtLbl = lbl;
	} 

	public int getWidth() {
		return text.length() * fontSize;
	}
	
	public int getHeight() {
		return fontSize;
	}
	
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int size) {
		this.fontSize = size;
		adjustFont();
	}
	public String getFontName() {
		return fontName;
	}
	
	public void setFontName(String fontName) {
		this.fontName = fontName;
		adjustFont();
	}

	
	@Override
	public void draw(Graphics2D g) {
		txtLbl.setBounds(getPosX(), getPosY(), getWidth(), getHeight());
	}
	
	@Override
	public boolean isInArea(int x, int y) {
		return (x >= posX && x<= posX + getWidth() && y >= posY && y<= posY + getHeight() );
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
