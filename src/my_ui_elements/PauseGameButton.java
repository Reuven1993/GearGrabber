// // package my_ui_elements;
// // import base.Game;
// // import my_base.MyContent;
// // import ui_elements.GameButton;

// // public class PauseGameButton extends GameButton {

// //     public PauseGameButton(String id, String name, int posX, int posY) {
// //         super(id, name, 100, 40, posX, posY);
// //     }

// //     @Override
// //     public void action() {
// //         // The basic buttonAction prints the id of the button to the console.
// //         // Keep the call to super to preserve this behavior or remove it if you don't
// //         // want the printing.
// //         super.action();

// //         MyContent content = (MyContent) Game.Content();

// //         // Pause or resume the game - GameControl.pauseGame() handles all the checks internally
// //         content.gameControl().pauseGame();
        
// //         // Update button text based on game state
// //         if (content.gameControl().isGamePaused()) {
// //             this.setText("Resume");
// //         } else {
// //             this.setText("Pause");
// //         }
// //     }
// // }

// package my_ui_elements;
// import base.Game;
// import my_base.MyContent;
// import ui_elements.GameButton;

// public class PauseGameButton extends GameButton {

//     public PauseGameButton(String id, String name, int posX, int posY) {
//         super(id, name, 100, 40, posX, posY);
//     }

//     @Override
//     public void action() {
//         // The basic buttonAction prints the id of the button to the console.
//         // Keep the call to super to preserve this behavior
//         super.action();

//         MyContent content = (MyContent) Game.Content();
        
//         // Get the game control
//         GameControl gameControl = content.gameControl();
        
//         // Toggle the pause state
//         if (gameControl.isPaused()) {
//             // If it's paused, resume the game
//             gameControl.startGame();
//             this.setText("Pause");
//         } else {
//             // If it's not paused, pause the game
//             gameControl.pauseGame();
//             this.setText("Resume");
//         }
//     }
// }

package my_ui_elements;

import base.Game;
import my_base.MyContent;
import ui_elements.GameButton;

public class PauseGameButton extends GameButton {

    public PauseGameButton(String id, String name, int posX, int posY) {
        super(id, name, 100, 40, posX, posY);
    }

    @Override
    public void action() {
        // The basic buttonAction prints the id of the button to the console.
        super.action();

        MyContent content = (MyContent) Game.Content();
        
        // Access gameControl through content
        if (content.gameControl().isGamePaused()) {
            content.gameControl().startGame(); // Resume game
            this.setText("Pause");
        } else {
            content.gameControl().pauseGame(); // Pause game
            this.setText("Resume");
        }
    }
}