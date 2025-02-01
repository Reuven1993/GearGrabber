package my_base;

import base.Game;
import base.MouseHandler;
import shapes.Shape;

public class MyMouseHandler extends MouseHandler {

	// set content to point to myContent so we can get to all characters in the game
	MyContent content = (MyContent) Game.Content();

	@Override
	public void screenClicked(int x, int y) {
		super.screenClicked(x, y);
		// Enter your specific code here
	}

	@Override
	public void screenRightClicked(int x, int y) {
		super.screenRightClicked(x, y);
		// Enter your specific code here
	}
	
	@Override
	public void shapeClicked(Shape shape, int x, int y) {
		super.shapeClicked(shape, x, y);
		// Enter your specific code here
	}
	
	@Override
	public void shapeRightClicked(Shape shape, int x, int y) {
		super.shapeRightClicked(shape, x, y);
		// Enter your specific code here
	}

	@Override
    public void mouseMovedOverShape(Shape shape, int x, int y) {
		super.mouseMovedOverShape(shape, x, y);
		// Enter your specific code here
    }
	
	@Override
    public void mouseDraggedOverShape(Shape shape, int x, int y) {
		super.mouseDraggedOverShape(shape, x, y);
		// Enter your specific code here
	}
	
	@Override 
	public void mouseMovedOverScreen(int x, int y) {
		super.mouseMovedOverScreen(x, y);
	}
	
	@Override 
	public void mouseDraggedOverScreen(int x, int y) {
		super.mouseDraggedOverScreen(x, y);
	}

	@Override 
	public void shapePressed(Shape shape, int x, int y) {
		super.shapePressed(shape, x, y);
	}    
	
	@Override 
	public void shapeReleased(Shape shape, int x, int y) {
		super.shapeReleased(shape, x, y);
	}

	@Override 
	public void screenPressed(int x, int y) {
		super.screenPressed(x,y);
	}

	@Override 
	public void screenReleased(int x, int y) {
		super.screenReleased(x,y);
	}


}
