package my_ui_elements;

import base.Game;
import my_base.MyContent;
import ui_elements.GameCheckbox;

public class AesteticObstacleCB extends  GameCheckbox {

    private MyContent content;

    public AesteticObstacleCB(String id, String name, int posX, int posY, int width, int height, boolean isSelected) {
        super(id, name, posX, posY, width, height, isSelected);
        this.content = (MyContent) Game.Content();
    }

    @Override
    public void action() {
        super.action();
        if (isSelected()) {
            System.out.println("Aestetic Obstacle is selected");
            content.gameControl().useAestheticObstacles();
        } else {
            System.out.println("Aestetic Obstacle is not selected");
            content.gameControl().usePolyObstacles();
        }
    }

    public void enable() {
        this.checkbox.setEnabled(true);
    }

    public void disable() {
        this.checkbox.setEnabled(false);
    }

}
