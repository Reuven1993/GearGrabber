package my_ui_elements;

import base.Game;
import my_base.MyContent;
import ui_elements.GameButton;

public class StartGameButton extends GameButton {

    private DifficultyCombo difficultyCombo;
    private AesteticObstacleCB aestheticObstacleCB;

    public StartGameButton(String id, String name, int posX, int posY) {
        super(id, name, 100, 40, posX, posY);
        difficultyCombo = (DifficultyCombo) Game.UI().dashboard().getUIElement("difficultyCombo");
        aestheticObstacleCB = (AesteticObstacleCB) Game.UI().dashboard().getUIElement("aestheticObstacleCB");

    }

    public StartGameButton(String id, String name, int posX, int posY , DifficultyCombo difficultyCombo, AesteticObstacleCB aestheticObstacleCB) {
        super(id, name, 100, 40, posX, posY);
        this.difficultyCombo = difficultyCombo;
        this.aestheticObstacleCB = aestheticObstacleCB;
    }

    @Override
    public void action() {
        super.action();
    
        MyContent content = (MyContent) Game.Content();
        
        // Use the accessor method instead of directly accessing the field
        if (!content.gameControl().isGameStarted()) {
            String playerName = javax.swing.JOptionPane.showInputDialog(
                Game.UI().frame(),
                "Enter your name:",
                "Player Name",
                javax.swing.JOptionPane.QUESTION_MESSAGE);
                
            if (playerName == null || playerName.trim().isEmpty()) {
                playerName = "Player1";
            }
            
            content.gameControl().setPlayerName(playerName);
        }
        
        content.gameControl().startGame();
        difficultyCombo.disable();
        aestheticObstacleCB.disable();
    }
    
}
