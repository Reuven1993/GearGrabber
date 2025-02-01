package base;

public interface ShapeListener {
    public void shapeMoved (String shapeID, int dx, int dy);
    public void shapeStartDrag(String shapeID);
    public void shapeEndDrag(String shapeID);
    public void shapeClicked(String shapeID, int x, int y);
    public void shapeRightClicked(String shapeID, int x, int y);
    public void mouseEnterShape(String shapeID, int x, int y);
    public void mouseExitShape(String shapeID, int x, int y);
}