package my_ui_elements;

// import java.awt.Color;
import base.Game;
import my_base.MyContent;
import ui_elements.GameButton;

public class PickupButton extends GameButton {

    public PickupButton(String id, String name, int posX, int posY) {
        super(id, name, 125, 40, posX, posY);
        
        // Make the button more visible
        // this.button.setBackground(new Color(0, 128, 0)); // Green
        // this.button.setForeground(Color.WHITE);
        
        // Set the text inside the button
        this.button.setText("W=Pickup");
    }

    @Override
    public void action() {
        // The basic buttonAction prints the id of the button to the console.
        // Keep the call to super to preserve this behavior or remove it if you don't want the printing.
        super.action();

        MyContent content = (MyContent) Game.Content();

        // Check if the game is running
        if (content.gameControl().isGameRunning()) {
            // Activate the orderRobotPickup action in GameControl
            content.gameControl().orderRobotPickup();
        } else {
            System.out.println("Game is not running. Pickup action is disabled.");
        }
    }
}