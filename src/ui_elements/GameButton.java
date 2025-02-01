package ui_elements;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import base.Game;
import base.GameContent;

/*
 * The GameButton is a class that handles dashboard buttons.
 * To have a specific behavior to a button, you should create a class that extends GameButton
 * and overrides it buttonAction method.
 * You can see an example in the class ExampleButton in package buttons.
 */

public class GameButton extends UIElement {
	
	protected JButton button;
	protected GameContent content;
	
	public GameButton(String id, String name, int width, int height, int posX, int posY) {
		super(id, posX, posY, width, height, new JButton());
		this.button = (JButton) this.getJComponent();
		setText(name);
		this.button.setFont(new Font("Ariel", Font.BOLD, 16));
		this.content = Game.Content();
		this.button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				action();
			}
		});

	}
	
	public void setText(String name) {
		this.button.setText(name);
	}
	public String getText() {
		return this.button.getText();
	}

	//This function is a placeholder and should be overriden in derived specific buttons
	public void action() {
		System.out.println(id + " clicked");
		Game.UI().frame().requestFocus();
	}
	
}

