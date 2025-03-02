package my_game;

import java.awt.Color;

import base.Game;
import base.GameCanvas;
import ui_elements.ScreenPoint;
import shapes.Text;

public class StatusLine {

    private ScreenPoint location = new ScreenPoint(200, 200);
    private final String guid = "statusLine";
    private String startText = "Welcome GearGrabber! \nPress Start to begin!";
    private String gamePlayingText = "";
    private String playerWonText = "Congratulations! You won!";
    private String gameOverText = "Game Over! \nPress Restart to play again!";
    private String pauseText = "Game Paused! \nPress Start to resume!";

    private Color color = Color.GREEN;
    private int fontSize = 30;

    public StatusLine() {

    }

    public void displayStartText() {

        this.color = Color.GREEN;
        updateText(this.getStartText());

    }

    public void displayGamePlayingText() {

        updateText(this.getGamePlayingText());

    }

    public void displayPlayerWonText() {


        this.color = Color.GREEN;
        updateText(this.getPlayerWonText());


    }

    public void displayGameOverText() {

        this.color = Color.RED;
        updateText(this.getGameOverText());

    }

    public void displayPauseText() {

        this.color = Color.BLACK;
        updateText(this.getPauseText());

    }

    public void updateText(String text) {

        GameCanvas canvas = Game.UI().canvas();
        Text statusLineText= (Text) canvas.getShape(this.getGuid());
        statusLineText.setText(text);
        statusLineText.setColor(this.color);
        canvas.repaint();

    }

    public void addToCanvas() {

        GameCanvas canvas = Game.UI().canvas();

        Text text = new Text(guid, startText, location.x, location.y);
        text.setColor(color);
        text.setFontSize(fontSize);

        canvas.addShape(text);

    }

    public void addToCanvas(int zOrder) {

        GameCanvas canvas = Game.UI().canvas();

        Text text = new Text(guid, startText, location.x, location.y);
        text.setColor(color);
        text.setFontSize(fontSize);

        text.setzOrder(zOrder);

        canvas.addShape(text);

    }

    public void setLocation(ScreenPoint location) {
        this.location = location;

        GameCanvas canvas = Game.UI().canvas();
        Text text = (Text) canvas.getShape(this.getGuid());

        text.moveToLocation(location.x, location.y);
        // GameCanvas canvas = Game.UI().canvas();
        // canvas.moveShapeToLocation(guid, location.x, location.y);
    }

    public ScreenPoint getLocation() {
        return location;
    }

    public String getGuid() {
        return guid;
    }

    public String getStartText() {
        return startText;
    }

    public void setStartText(String startText) {
        this.startText = startText;
    }

    public String getPlayerWonText() {
        return playerWonText;
    }

    public void setPlayerWonText(String playerWonText) {
        this.playerWonText = playerWonText;
    }

    public String getGameOverText() {
        return gameOverText;
    }

    public void setGameOverText(String gameOverText) {
        this.gameOverText = gameOverText;
    }

    public String getPauseText() {
        return pauseText;
    }

    public void setPauseText(String pauseText) {
        this.pauseText = pauseText;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getGamePlayingText() {
        return gamePlayingText;
    }

    public void setGamePlayingText(String gamePlayingText) {
        this.gamePlayingText = gamePlayingText;
    }


    
}
