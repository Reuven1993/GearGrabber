package my_ui_elements;
import base.Game;
import my_base.MyContent;
import my_game.GameControl;
import ui_elements.GameComboBox;


public class DifficultyCombo extends GameComboBox {


    public DifficultyCombo(int posX, int posY) {
        super("difficultyCombo", "Difficulty", posX, posY, 160, 30, new String[] { "Easy", "Medium", "Hard" });
        this.comboBox.setSelectedItem("Medium");
    }

    public void setDifficulty(String difficulty) {
        this.comboBox.setSelectedItem(difficulty);
    }

    @Override
    public void action() {
        super.action();
        switch (getOption()) {
            case "Easy":
            ((MyContent) Game.Content()).gameControl().setDifficulty(GameControl.Difficulty.EASY);
                break;
            case "Medium":
                ((MyContent) Game.Content()).gameControl().setDifficulty(GameControl.Difficulty.MEDIUM);
                break;
            case "Hard":
            ((MyContent) Game.Content()).gameControl().setDifficulty(GameControl.Difficulty.HARD);
                break;
        }

    }

}
