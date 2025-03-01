package my_ui_elements;

import java.awt.Color;
import base.Game;
import my_game.ScoreBoard;
import shapes.Text;
import ui_elements.GameButton;

public class HighScoreButton extends GameButton {
    
    private ScoreBoard scoreBoard;
    private boolean scoresDisplayed = false;
    private static final String HIGH_SCORE_TITLE_ID = "highScoreTitle";
    private static final int MAX_SCORE_ROWS = 10;
    private static final String SCORE_ROW_PREFIX = "scoreRow";
    
    public HighScoreButton(String id, String name, int posX, int posY) {
        super(id, name, 120, 40, posX, posY);
        scoreBoard = new ScoreBoard();
        this.button.setBackground(new Color(0, 102, 204));
        this.button.setForeground(Color.WHITE);
    }

    @Override
    public void action() {
        super.action();
        
        if (scoresDisplayed) {
            clearHighScores();
            scoresDisplayed = false;
        } else {
            displayHighScores();
            scoresDisplayed = true;
        }
        
        // Ensure the canvas is repainted after changes
        Game.UI().canvas().repaint();
    }
    
    /**
     * Clears any high score elements from the canvas
     * Can be called from other classes to clean up the UI
     */
    public void clearHighScores() {
        // Clear title
        if (Game.UI().canvas().getShape(HIGH_SCORE_TITLE_ID) != null) {
            Game.UI().canvas().deleteShape(HIGH_SCORE_TITLE_ID);
        }
        
        // Clear all score rows by checking each one first
        for (int i = 0; i < MAX_SCORE_ROWS; i++) {
            String rowId = SCORE_ROW_PREFIX + i;
            if (Game.UI().canvas().getShape(rowId) != null) {
                Game.UI().canvas().deleteShape(rowId);
            }
        }
        
        scoresDisplayed = false;
    }
    
    /**
     * Displays the high scores on the canvas
     */
    private void displayHighScores() {
        // Clear any existing scores first
        clearHighScores();
        
        String[][] topScores = scoreBoard.getTopScores(MAX_SCORE_ROWS);
        
        Text titleText = new Text(HIGH_SCORE_TITLE_ID, "HIGH SCORES", 300, 350);
        titleText.setColor(Color.BLUE);
        titleText.setFontSize(24);
        Game.UI().canvas().addShape(titleText);
        
        if (topScores.length == 0) {
            Text noScoresText = new Text(SCORE_ROW_PREFIX + "0", "No scores recorded yet!", 300, 370);
            noScoresText.setColor(Color.BLACK);
            noScoresText.setFontSize(16);
            Game.UI().canvas().addShape(noScoresText);
            return;
        }
        
        int yPos = 370;
        for (int i = 0; i < topScores.length; i++) {
            // Assuming columns are: ID, PlayerName, Score, Difficulty, Date, GearsCollected, TimeRemaining
            String playerName = topScores[i][1]; // Index 1 is PlayerName
            String score = topScores[i][2];      // Index 2 is Score
            String difficulty = topScores[i][3]; // Index 3 is Difficulty
            
            String scoreText = (i+1) + ". " + playerName + ": " + score + 
                               " (Difficulty: " + difficulty + ")";
            Text scoreRow = new Text(SCORE_ROW_PREFIX + i, scoreText, 300, yPos);
            scoreRow.setColor(Color.BLACK);
            scoreRow.setFontSize(16);
            Game.UI().canvas().addShape(scoreRow);
            yPos += 30;
        }
    }
}