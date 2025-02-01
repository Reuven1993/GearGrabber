package base;

import shapes.Shape;
import ui_elements.ScreenPoint;

public class MouseHandler {

	protected Shape draggedShape;
	protected boolean onShape = false;
	protected Shape shapeUnderMouse = null;
	private ScreenPoint dragPoint;

	public void screenClicked(int x, int y) {
		System.out.println("Mouse clicked: " + x + ", "+ y);
	}

	public void screenRightClicked(int x, int y) {
		System.out.println("Mouse right-clicked: " + x + ", "+ y);
	}

	public void mouseMovedOverScreen(int x, int y) {
		//System.out.println("Mouse moved: " + x + ", "+ y);
		if (onShape) {
			if (shapeUnderMouse.getshapeListener() != null) {
				shapeUnderMouse.getshapeListener().mouseExitShape(shapeUnderMouse.getId(), x, y);
			}
			onShape = false;
			shapeUnderMouse = null;
		}

	}

	public void mouseDraggedOverScreen(int x, int y) {
		//System.out.println("Mouse dragged: " + x + ", "+ y);
		if (draggedShape != null) {
			int dx = x - dragPoint.x;
			int dy = y - dragPoint.y;
			dragPoint.x += dx;
			dragPoint.y += dy;
			Game.UI().canvas().moveShape(draggedShape.getId(), dx, dy);
		}
	}

	public void shapeClicked(Shape shape, int x, int y) {
		System.out.println("Shape clicked: " + shape.getId());
		if (shape.getshapeListener() != null) {
			shape.getshapeListener().shapeClicked(shape.getId(), x, y);
		}
	}
	
	public void shapeRightClicked(Shape shape, int x, int y) {
		System.out.println("Shape right-clicked: " + shape.getId());
		if (shape.getshapeListener() != null) {
			shape.getshapeListener().shapeRightClicked(shape.getId(), x, y);
		}
	}
	
    public void mouseMovedOverShape(Shape shape, int x, int y) {
		//System.out.println("Mouse moved over Shape: " + shape.getId());
		if (!onShape) {
			shapeUnderMouse = shape;
			onShape = true;
			if (shapeUnderMouse.getshapeListener() != null) {
				shapeUnderMouse.getshapeListener().mouseEnterShape(shape.getId(), x, y);
			}
		}		
    }
    	
    public void mouseDraggedOverShape(Shape shape, int x, int y) {
		//System.out.println("Mouse Dragged over Shape: " + shape.getId());
		if (draggedShape != null) {
			int dx = x - dragPoint.x;
			int dy = y - dragPoint.y;
			dragPoint.x += dx;
			dragPoint.y += dy;
			Game.UI().canvas().moveShape(draggedShape.getId(), dx, dy);
		}
    }    

	public void shapePressed(Shape shape, int x, int y) {
		System.out.println("Shape pressed: " + shape.getId());
		if (shape.isDraggable()) {
			draggedShape = shape;
			dragPoint = new ScreenPoint(x,y);
			if (shape.getshapeListener() != null) {
				shape.getshapeListener().shapeStartDrag(shape.getId());
			}
		}
    }    

	public void shapeReleased(Shape shape, int x, int y) {
		System.out.println("Shape Released: " + shape.getId());
		if (draggedShape != null) {
			if (draggedShape.getshapeListener() != null) {
				draggedShape.getshapeListener().shapeEndDrag(draggedShape.getId());
			}	
			draggedShape = null;
		}
	} 
	
	public void screenPressed(int x, int y) {
		System.out.println("Mouse pressed: " + x + ", "+ y);
	}

	public void screenReleased(int x, int y) {
		System.out.println("Mouse released: " + x + ", "+ y);
		if (draggedShape != null) {
			if (draggedShape.getshapeListener() != null) {
				draggedShape.getshapeListener().shapeEndDrag(draggedShape.getId());
			}	
			draggedShape = null;
		}
	}

}
