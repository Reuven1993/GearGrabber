package my_ui_elements;

import java.awt.Color;
import base.Game;
import my_game.ScoreBoard;
import shapes.Text;
import ui_elements.GameButton;

public class HighScoreButton extends GameButton {
    
    private ScoreBoard scoreBoard;
    
    public HighScoreButton(String id, String name, int posX, int posY) {
        super(id, name, 120, 40, posX, posY);
        scoreBoard = new ScoreBoard();
        this.button.setBackground(new Color(0, 102, 204));
        this.button.setForeground(Color.WHITE);
    }

    @Override
    public void action() {
        super.action();
        
        Game.UI().canvas().deleteShape("highScoreTitle");
        for (int i = 0; i < 10; i++) {
            Game.UI().canvas().deleteShape("scoreRow" + i);
        }
        
        String[][] topScores = scoreBoard.getTopScores(10);
        
        Text titleText = new Text("highScoreTitle", "HIGH SCORES", 300, 350);
        titleText.setColor(Color.BLUE);
        titleText.setFontSize(24);
        Game.UI().canvas().addShape(titleText);
        
        if (topScores.length == 0) {
            Text noScoresText = new Text("scoreRow0", "No scores recorded yet!", 300, 370);
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
            Text scoreRow = new Text("scoreRow" + i, scoreText, 300, yPos);
            scoreRow.setColor(Color.BLACK);
            scoreRow.setFontSize(16);
            Game.UI().canvas().addShape(scoreRow);
            yPos += 30;
        }
    }
}