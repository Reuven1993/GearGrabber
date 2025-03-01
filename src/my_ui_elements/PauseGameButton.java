package my_ui_elements;

import java.awt.Color;
import base.Game;
import my_base.MyContent;
import ui_elements.GameButton;

public class PauseGameButton extends GameButton {

    public PauseGameButton(String id, String name, int posX, int posY) {
        super(id, name, 100, 40, posX, posY);
        
        // Make the button more visible
        this.button.setBackground(new Color(70, 130, 180)); // Steel blue
        this.button.setForeground(Color.WHITE);
    }

    @Override
    public void action() {
        // Call the basic button action from superclass
        super.action();

        MyContent content = (MyContent) Game.Content();
        
        // Don't allow pause button action if game is not started or already over
        if (!content.gameControl().isGameStarted() || content.gameControl().isGameOver()) {
            return;
        }
        
        // Toggle the pause state
        if (content.gameControl().isGamePaused()) {
            // If it's paused, resume the game
            content.gameControl().startGame();
        } else {
            // If it's not paused, pause the game
            content.gameControl().pauseGame();
        }
        
        // Update button text based on the CURRENT state after the action
        updateButtonText();
        
        // Ensure game canvas is repainted
        Game.UI().canvas().repaint();
    }
    
    /**
     * Updates the button text based on the current game state
     * This is public so it can be called from other classes when game state changes
     */
    public void updateButtonText() {
        MyContent content = (MyContent) Game.Content();
        
        if (content.gameControl().isGamePaused()) {
            this.setText("Resume");
        } else {
            this.setText("Pause");
        }
    }
}