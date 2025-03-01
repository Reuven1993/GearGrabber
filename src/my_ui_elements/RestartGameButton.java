package my_ui_elements;

import java.awt.Color;
import base.Game;
import my_base.MyContent;
import ui_elements.GameButton;

public class RestartGameButton extends GameButton {

    private DifficultyCombo difficultyCombo;
    private AesteticObstacleCB aestheticObstacleCB;

    public RestartGameButton(String id, String name, int posX, int posY) {
        super(id, name, 100, 40, posX, posY);
        
        // Make the button more visible
        this.button.setBackground(new Color(220, 20, 60)); // Crimson
        this.button.setForeground(Color.WHITE);
        
        // Find UI components by ID
        try {
            this.difficultyCombo = (DifficultyCombo) Game.UI().dashboard().getUIElement("difficultyCombo");
            this.aestheticObstacleCB = (AesteticObstacleCB) Game.UI().dashboard().getUIElement("aestheticObstacleCB");
        } catch (Exception e) {
            System.err.println("Error finding UI elements: " + e.getMessage());
        }
    }

    public RestartGameButton(String id, String name, int posX, int posY, DifficultyCombo difficultyCombo, AesteticObstacleCB aestheticObstacleCB) {
        super(id, name, 100, 40, posX, posY);
        
        // Make the button more visible
        this.button.setBackground(new Color(220, 20, 60)); // Crimson
        this.button.setForeground(Color.WHITE);
        
        this.difficultyCombo = difficultyCombo;
        this.aestheticObstacleCB = aestheticObstacleCB;
    }

    @Override
    public void action() {
        // Basic button action from superclass
        super.action();

        try {
            MyContent content = (MyContent) Game.Content();
            
            // Reset game through content
            content.gameControl().restartGame();
    
            // Enable the difficulty combo box
            if (difficultyCombo != null) {
                difficultyCombo.enable();
            }
    
            // Enable the Aesthetic Obstacle checkbox
            if (aestheticObstacleCB != null) {
                aestheticObstacleCB.enable();
            }
            
            // Clear any high scores being displayed
            HighScoreButton highScoreButton = (HighScoreButton) Game.UI().dashboard().getUIElement("highScoreButton");
            if (highScoreButton != null) {
                highScoreButton.clearHighScores();
            }
            
            // Reset pause button text
            PauseGameButton pauseButton = (PauseGameButton) Game.UI().dashboard().getUIElement("pauseButton");
            if (pauseButton != null) {
                pauseButton.updateButtonText();
            }
            
            // Ensure the dashboard and canvas are refreshed
            Game.UI().dashboard().updateUI();
            Game.UI().canvas().repaint();
            
        } catch (Exception e) {
            System.err.println("Error in RestartGameButton.action(): " + e.getMessage());
        }
    }
}