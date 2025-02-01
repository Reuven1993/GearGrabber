package ui_elements;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import base.Game;

/*
 * The GameComboBox is a class that handles dashboard ComboBoxs.
 * To have a specific behavior to a ComboBox, you should create a class that extends GameComboBox
 * and overrides it ComboBoxAction method.
 * You can see an example in the class ExampleComboBox in package ComboBoxs.
 */

public class GameComboBox extends UIElement {

	protected JComboBox<String> comboBox;
	protected String[] options;

	// why can we not care for the type safety? we define it in the lambda
	@SuppressWarnings("unchecked")
	public GameComboBox(String id, String name, int posX, int posY, int width, int height, String[] options) {
		super(id, posX, posY, width, height, new JComboBox<String>(options));
		this.comboBox = (JComboBox<String>) getJComponent();
		this.options = options;
		this.comboBox.setFont(new Font("Ariel", Font.BOLD, 14));
		this.comboBox.setBackground(Color.darkGray);
		this.comboBox.setSelectedIndex(0);
		this.comboBox.setForeground(Color.WHITE);
		this.comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				action();
			}
		});

	}

	public String getOption() {
		return options[comboBox.getSelectedIndex()];
	}

	//This function is a placeholder and should be overriden in derived specific buttons
	public void action() {
		System.out.println(id + " set to " + getOption());
		Game.UI().frame().requestFocus();
	}
}
