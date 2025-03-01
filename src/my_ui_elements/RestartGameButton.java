// // package my_ui_elements;
// // import base.Game;
// // import my_base.MyContent;
// // import ui_elements.GameButton;

// // public class RestartGameButton extends GameButton {

// //     private DifficultyCombo difficultyCombo;
// //     private AesteticObstacleCB aestheticObstacleCB;

// //     public RestartGameButton(String id, String name, int posX, int posY) {
// //         super(id, name, 100, 40, posX, posY);
// //         difficultyCombo = (DifficultyCombo) Game.UI().dashboard().getUIElement("difficultyCombo");
// //         aestheticObstacleCB = (AesteticObstacleCB) Game.UI().dashboard().getUIElement("aestheticObstacleCB");
// //     }

// //     public RestartGameButton(String id, String name, int posX, int posY, DifficultyCombo difficultyCombo, AesteticObstacleCB aestheticObstacleCB) {
// //         super(id, name, 100, 40, posX, posY);
// //         this.difficultyCombo = difficultyCombo;
// //         this.aestheticObstacleCB = aestheticObstacleCB;
// //     }

// //     @Override
// //     public void action() {
// //         // The basic buttonAction prints the id of the button to the console.
// //         // Keep the call to super to preserve this behavior or remove it if you don't want the printing.
// //         super.action();

// //         MyContent content = (MyContent) Game.Content();
        
// //         // Restart the game
// //         content.gameControl().restartGame();

// //         // Enable the difficulty combo box
// //         difficultyCombo.enable();

// //         // Enable the Aesthetic Obstacle checkbox
// //         aestheticObstacleCB.enable();
        
// //         // Reset pause button text if it was changed
// //         GameButton pauseButton = (GameButton) Game.UI().dashboard().getUIElement("pauseButton");
// //         if (pauseButton != null && !pauseButton.getText().equals("Pause")) {
// //             pauseButton.setText("Pause");
// //         }
// //     }
// // }
// package my_ui_elements;
// import base.Game;
// import my_base.MyContent;
// import ui_elements.GameButton;

// public class RestartGameButton extends GameButton {

//     private DifficultyCombo difficultyCombo;
//     private AesteticObstacleCB aestheticObstacleCB;

//     public RestartGameButton(String id, String name, int posX, int posY) {
//         super(id, name, 100, 40, posX, posY);
//         difficultyCombo = (DifficultyCombo) Game.UI().dashboard().getUIElement("difficultyCombo");
//         aestheticObstacleCB = (AesteticObstacleCB) Game.UI().dashboard().getUIElement("aestheticObstacleCB");
//     }

//     public RestartGameButton(String id, String name, int posX, int posY, DifficultyCombo difficultyCombo, AesteticObstacleCB aestheticObstacleCB) {
//         super(id, name, 100, 40, posX, posY);
//         this.difficultyCombo = difficultyCombo;
//         this.aestheticObstacleCB = aestheticObstacleCB;
//     }

//     @Override
//     public void action() {
//         // The basic buttonAction prints the id of the button to the console.
//         super.action();

//         MyContent content = (MyContent) Game.Content();
        
//         // Get the game control
//         GameControl gameControl = content.gameControl();
        
//         // Reset game state using appropriate methods
//         gameControl.resetGame();
        
//         // Reset the board with current difficulty settings
//         gameControl.updateBoard();
        
//         // Reset the timer
//         content.board().getTimer().setTimeRemaining(content.gameControl().getDifficulty().getGameDuration());
        
//         // Display start message
//         content.board().getStatusLine().displayStartText();
        
//         // Update the canvas
//         content.board().updateBoardOnCanvas();
        
//         // Reset button states
//         PauseGameButton pauseButton = (PauseGameButton) Game.UI().dashboard().getUIElement("pauseButton");
//         if (pauseButton != null) {
//             pauseButton.setText("Pause");
//         }
        
//         // Enable the difficulty combo box
//         difficultyCombo.enable();

//         // Enable the Aesthetic Obstacle checkbox
//         aestheticObstacleCB.enable();
//     }
// }

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
        super.action();

        MyContent content = (MyContent) Game.Content();
        
        // Reset game through content
        content.gameControl().restartGame();

        // Enable the difficulty combo box
        difficultyCombo.enable();

        // Enable the Aesthetic Obstacle checkbox
        aestheticObstacleCB.enable();
    }
}