
package ui_elements;

import javax.swing.JComponent;

import shapes.Shape.STATUS;

/** Add your docs here. */
public abstract class UIElement {
    protected final String id;
	protected int width;
	protected int height;
	protected int posX;
	protected int posY;

    protected JComponent jComponent;

    // setting as private so inheritance must provide it, but can't use it
    protected STATUS status;

    public UIElement(String id, int posX, int posY, int width, int height, JComponent initJComponent) {
        this.jComponent = initJComponent;
        this.id = id;
        this.width = width;
        this.height = height;
        setSize(width, height);
        this.posX = posX;
        this.posY = posY;
        setLocation(posX, posY);
        this.status = STATUS.SHOW;
    }

    
    public STATUS getStatus() {
		return status;
	}
	
    public void setStatus(STATUS status) {
		this.status =status;
	}

    public void setWidth(int width) {
        this.width = width;
        setSize(width, height);
    }

    public int getWidth() {
		return width;
	}

    public void setHeight(int height) {
        this.height = height;
        setSize(width, height);
    }

	public int getHeight() {
		return height;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
        setLocation(posX, posY);
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
        setLocation(posX, posY);
	}

	public String getId() {
		return id;
	}
    public JComponent getJComponent() {
        return this.jComponent;
    }

    public void setSize(int width, int height) {
        this.jComponent.setSize(width,height);
    }

    public void setLocation(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.jComponent.setLocation(posX,posY);
    }

    public void move(int dx, int dy) {
        setLocation(this.posX + dx, this.posY + dy);
    }
    public abstract void action();
}