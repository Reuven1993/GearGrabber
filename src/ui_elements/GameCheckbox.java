package ui_elements;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import base.Game;

import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

/*
 * The Gamecheckbox is a class that handles dashboard checkboxs.
 * To have a specific behavior to a checkbox, you should create a class that extends Gamecheckbox
 * and overrides it checkboxAction method.
 * You can see an example in the class Examplecheckbox in package checkboxs.
 */

public class GameCheckbox extends UIElement{

	protected JCheckBox checkbox;
	protected Font font;
	protected Color textColor = Color.WHITE;

	public GameCheckbox(String id, String name, int posX, int posY, int width, int height, boolean isSelected) {
		super(id, posX, posY, width, height, new JCheckBox());
		this.checkbox = (JCheckBox) getJComponent();
		setText(name);
		setTextColor(textColor);
		this.font = new Font("Ariel", Font.BOLD, 16);
		this.checkbox.setFont(this.font);
		this.checkbox.setBackground(null);
		this.checkbox.setForeground(Color.WHITE);
		this.checkbox.setBorderPainted(false);
		this.checkbox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				action();
			}
		});
		this.checkbox.setSelected(isSelected);
	}
	
	public boolean isSelected() {
		return checkbox.isSelected();
	}
	
	public void setSelected(boolean value) {
		checkbox.setSelected(value);
	}
	

	public void setText(String name) {
		this.checkbox.setText(name);
	}

	public String getText() {
		return this.checkbox.getText();
	}

	public void setFont(Font font) {
		this.font = font;
		this.checkbox.setFont(this.font);
	}
	
	/**
	 * @param textColor the textColor to set
	 */
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
		this.checkbox.setForeground(textColor);
	}

	public int getTextHeight() {
		FontRenderContext render = new FontRenderContext(new AffineTransform(), true, true);
		return (int) (font.getStringBounds(getText(), render).getHeight());
	}

	public int getTextWidth() {
		FontRenderContext render = new FontRenderContext(new AffineTransform(), true, true);
		return (int) (font.getStringBounds(getText(), render).getWidth());
	}

	//This function is a placeholder and should be overriden in derived specific buttons
	public void action() {
		System.out.println(id + " set to " + isSelected());
		Game.UI().frame().requestFocus();
	}
}
