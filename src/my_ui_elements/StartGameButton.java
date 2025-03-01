package my_ui_elements;

import java.awt.Color;
import javax.swing.JOptionPane;
import base.Game;
import my_base.MyContent;
import ui_elements.GameButton;

public class StartGameButton extends GameButton {

    private DifficultyCombo difficultyCombo;
    private AesteticObstacleCB aestheticObstacleCB;

    public StartGameButton(String id, String name, int posX, int posY) {
        super(id, name, 100, 40, posX, posY);
        
        // Make the button more visible
        this.button.setBackground(new Color(0, 128, 0)); // Green
        this.button.setForeground(Color.WHITE);
        
        // Find UI components by ID
        try {
            this.difficultyCombo = (DifficultyCombo) Game.UI().dashboard().getUIElement("difficultyCombo");
            this.aestheticObstacleCB = (AesteticObstacleCB) Game.UI().dashboard().getUIElement("aestheticObstacleCB");
        } catch (Exception e) {
            System.err.println("Error finding UI elements: " + e.getMessage());
        }
    }

    public StartGameButton(String id, String name, int posX, int posY, DifficultyCombo difficultyCombo, AesteticObstacleCB aestheticObstacleCB) {
        super(id, name, 100, 40, posX, posY);
        
        // Make the button more visible
        this.button.setBackground(new Color(0, 128, 0)); // Green
        this.button.setForeground(Color.WHITE);
        
        this.difficultyCombo = difficultyCombo;
        this.aestheticObstacleCB = aestheticObstacleCB;
    }

    @Override
    public void action() {
        super.action();
    
        try {
            MyContent content = (MyContent) Game.Content();
            
            // Clear any high scores being displayed
            HighScoreButton highScoreButton = (HighScoreButton) Game.UI().dashboard().getUIElement("highScoreButton");
            if (highScoreButton != null) {
                highScoreButton.clearHighScores();
            }
            
            // Use the accessor method instead of directly accessing the field
            if (!content.gameControl().isGameStarted()) {
                String playerName = JOptionPane.showInputDialog(
                    Game.UI().frame(),
                    "Enter your name:",
                    "Player Name",
                    JOptionPane.QUESTION_MESSAGE);
                    
                if (playerName == null || playerName.trim().isEmpty()) {
                    playerName = "Player1";
                }
                
                content.gameControl().setPlayerName(playerName);
            }
            
            // Start the game
            content.gameControl().startGame();
            
            // Disable settings controls when game is active
            if (difficultyCombo != null) {
                difficultyCombo.disable();
            }
            
            if (aestheticObstacleCB != null) {
                aestheticObstacleCB.disable();
            }
            
            // Make sure the pause button shows the correct state
            PauseGameButton pauseButton = (PauseGameButton) Game.UI().dashboard().getUIElement("pauseButton");
            if (pauseButton != null) {
                pauseButton.updateButtonText();
            }
            
            // Ensure the frame has focus for keyboard events
            Game.UI().frame().requestFocus();
            
            // Refresh all UI elements
            Game.UI().dashboard().updateUI();
            Game.UI().canvas().repaint();
            
        } catch (Exception e) {
            System.err.println("Error in StartGameButton.action(): " + e.getMessage());
        }
    }
}