package base;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import shapes.Image;
import ui_elements.GameButton;


public class GameUI {
	JFrame frame;
	GameCanvas canvas;
	GameDashboard dashboard;
	
	public GameUI(String gameName, int width, int height) {
		
		frame = new JFrame(gameName);
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		canvas = new GameCanvas();
		dashboard = new GameDashboard();
		split.setTopComponent(canvas);
		split.setBottomComponent(dashboard);

		/* 
		 * -----------------------------------------------------------------------------
		 * Change this number for a different ratio between the canvas and the dashboard
		 * -----------------------------------------------------------------------------
		 */
		split.setDividerLocation(height*78/100);
		
		frame.getContentPane().add(split);
	}
	
	public GameCanvas canvas() {
		return this.canvas;
	}
	
	public GameDashboard dashboard() {
		return this.dashboard;
	}
	
	public JFrame frame() {
		return this.frame;
	}
	
	public void setSize(int width, int height) {
		frame.setSize(width,  height);
	}
	
	public void setVisible(boolean visible) {
		this.frame.setVisible(visible);
	}
	
	public void setFocusable(boolean focusable) {
		this.frame.setFocusable(focusable);
	}
	
	public static void main(String[] args) {
		//Create a new game UI, named "My Game" with a size of 1000 x 1000 pixels
		GameUI gameUI = new GameUI("My Game", 1000, 1000);
		
		//Add two pokimon entities to the game's UI canvas
		gameUI.canvas().addShape(new Image("e1", "resources/Poki.jpg", 220, 220, 10, 10));
		gameUI.canvas().addShape(new Image("e2", "resources/Poki.jpg", 220, 220, 300, 10));
		
		//Add two buttons to the game's UI dashboard
		gameUI.dashboard().addUIElement(new GameButton("e1", "button1", 100, 60, 10, 10));
		gameUI.dashboard().addUIElement(new GameButton("e2", "button2", 100, 60, 200, 10));
		
		//Show the game UI 
		gameUI.setVisible(true);
	}

}


