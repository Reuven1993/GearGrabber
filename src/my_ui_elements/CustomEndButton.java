package my_ui_elements;

import java.awt.Color;
import javax.swing.JOptionPane;
import base.Game;
import ui_elements.GameButton;

public class CustomEndButton extends GameButton {
    
    public CustomEndButton(String id, String name, int width, int height, int posX, int posY) {
        super(id, name, width, height, posX, posY);
        
        // Make the button more visible with a distinct color
        this.button.setBackground(Color.RED);
        this.button.setForeground(Color.WHITE);
        this.button.setBorderPainted(true);
        this.button.setFocusPainted(false);
    }

    @Override
    public void action() {
        // Print debug info
        System.out.println("CustomEndButton clicked - attempting to terminate game");
        
        try {
            // Show confirmation dialog
            int result = JOptionPane.showConfirmDialog(
                Game.UI().frame(),
                "Are you sure you want to exit the game?",
                "Exit Game",
                JOptionPane.YES_NO_OPTION
            );
            
            if (result == JOptionPane.YES_OPTION) {
                // Try multiple termination approaches
                
                // 1. Standard Game.endGame() approach
                System.out.println("Calling Game.endGame()");
                Game.endGame();
                
                // 2. Try closing the frame directly
                System.out.println("Closing game frame");
                Game.UI().frame().setVisible(false);
                Game.UI().frame().dispose();
                
                // 3. Force exit as last resort
                System.out.println("Forcing system exit");
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println("Error in CustomEndButton: " + e.getMessage());
            e.printStackTrace();
            
            // Force exit even if there's an exception
            System.exit(0);
        }
    }
}