package base;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import shapes.Shape.STATUS;
import ui_elements.UIElement;
/**
 * A 2D screen that displays graphical entities and enables to set their location at runtime, causing an animation effect.
 * 
 * <h1>Properties:</h1>
 * <ul>
 * <li>int width - the screen width, in pixels<br>
 * <li>int height - the screen hight, in pixels<br>
 * <li>String backgroundColor - the screen background color, from a list of given colors [red, black, yellow, gray, white]<br>
 * <li>borderColor - the color of the screen border, from a list of given colors [red, black, yellow, gray, white]<br>
 * <li>borderWidth - the width of the screen border, in pixels (0 if no border)<br>
 * <li>int positionX, positionY - the screen position, in pixels<br>
 * </ul>
 * 
 * <h1>Methods:</h1>
 * <ul>
 * 	<li>void addButton(id, name, width, height, posX, posY) - adds a Button, identified by id, to the screen, where
 * 		name is the text written on the button.<br>
 * 	<li>void delObject(id) - permanently removes a Button identified by id from the screen.<br>
 * 	<li>void flipStatus(id) - changes the status of a Button and shows or hide it accordingly.<br>
 *  <il>void show(id) - shows a Button identified by id.<br>
 *  <il>void hide(id) - hides a Button identified by id.<br>
 *  <il>void showAll() shows all buttons.<br>
 *  <il>void hideAll() - hides all buttons.<br>
 *  <il>void deleteAll() - deletes all buttons from the screen.<br>
 * </ul>
 * 
 */
public class GameDashboard extends JPanel  {
	
	private static final long serialVersionUID = 1L;
	
	private final Map<String, UIElement> uiElements;

	String borderColor;
	int borderWidth;
	
	int width = 100;	
	int height = 100;
	
	int positionX;
	int positionY;
		
	public GameDashboard() {
		super();
		this.uiElements = new HashMap<>();
		this.setLayout(null);
	}

	/*
	 * Add a specific uiElement that is derived from UIElement and is created before.
	 */
	public void addUIElement(UIElement uiElement) {
		uiElements.put(uiElement.getId(), uiElement);
		this.add(uiElement.getJComponent());
		this.updateUI();
	}
	
	public UIElement getUIElement(String id) {
		return uiElements.get(id);
	}
	
	public void deleteUIElement(String id) {
		UIElement uiElement = uiElements.get(id);
		if (uiElement != null) {
			this.remove(uiElement.getJComponent());
			uiElements.remove(id);
		}
		this.updateUI();
	}

	public void hideAll() {
		for (UIElement uiElement : uiElements.values()) {
			uiElement.setStatus(STATUS.HIDE);
			this.remove(uiElement.getJComponent());
		}
		this.updateUI();
	}

	public void showAll() {
		for (UIElement uiElement : uiElements.values()) {
			uiElement.setStatus(STATUS.SHOW);
			this.add(uiElement.getJComponent());
		}
		this.updateUI();
	}

	public void deleteAll() {
		for (String id : uiElements.keySet()) {
			deleteUIElement(id);
		}
		this.updateUI();
	}

	public void flipStatus(String id) {
		UIElement uiElement = uiElements.get(id);
		if (uiElement != null) {
			if (uiElement.getStatus().equals(STATUS.HIDE)) {
				uiElement.setStatus(STATUS.SHOW);
				this.add(uiElement.getJComponent());
			} else if (uiElement.getStatus().equals(STATUS.SHOW)) {
				uiElement.setStatus(STATUS.HIDE);
				this.remove(uiElement.getJComponent());
			}
		}
		this.updateUI();
	}

	public void show(String id) {
		UIElement uiElement = uiElements.get(id);
		if (uiElement != null) {
			uiElement.setStatus(STATUS.SHOW);
				this.add(uiElement.getJComponent());
		}
		this.updateUI();
	}
	
	public void hide(String id) {
		UIElement uiElement = uiElements.get(id);
		if (uiElement != null) {
			uiElement.setStatus(STATUS.HIDE);
				this.remove(uiElement.getJComponent());
		}
		this.updateUI();
	}
}
