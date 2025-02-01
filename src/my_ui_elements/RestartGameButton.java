package my_ui_elements;
import base.Game;
import my_base.MyContent;
import ui_elements.GameButton;

public class RestartGameButton extends GameButton {

    private DifficultyCombo difficultyCombo;
    private AesteticObstacleCB aestheticObstacleCB;

    public RestartGameButton(String id, String name, int posX, int posY) {
        super(id, name, 100, 40, posX, posY);
        difficultyCombo = (DifficultyCombo) Game.UI().dashboard().getUIElement("difficultyCombo");
        aestheticObstacleCB = (AesteticObstacleCB) Game.UI().dashboard().getUIElement("aestheticObstacleCB");
    }

    public RestartGameButton(String id, String name, int posX, int posY, DifficultyCombo difficultyCombo, AesteticObstacleCB aestheticObstacleCB) {
        super(id, name, 100, 40, posX, posY);
        this.difficultyCombo = difficultyCombo;
        this.aestheticObstacleCB = aestheticObstacleCB;

    }

    @Override
    public void action() {
        // The basic buttonAction prints the id of the button to the console.
        // Keep the call to super to preserve this behavior or remove it if you don't want the printing.
        super.action();

        MyContent content = (MyContent) Game.Content();
        
        //TODO
        //Restart the game

        //Enable the difficulty combo box
        difficultyCombo.enable();

        //Enable the Aesthetic Obstacle checkbox
        aestheticObstacleCB.enable();

    }

}
