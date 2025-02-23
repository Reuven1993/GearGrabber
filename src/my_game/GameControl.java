
package my_game;
import base.Game;
public class GameControl {

    public enum Difficulty {
        EASY(1, 1, 60000),
        MEDIUM(2, 3, 30000),
        HARD(4, 5, 30000);

        private final int numGears;
        private final int numObstacles;
        private final long gameDuration;

        private Difficulty(int numGears, int numObstacles, long gameDuration) {
            this.numGears = numGears;
            this.numObstacles = numObstacles;
            this.gameDuration = gameDuration;
        }

        public int getNumGears() {
            return numGears;
        }

        public int getNumObstacles() {
            return numObstacles;
        }

        public long getGameDuration() {
            return gameDuration;
        }
    }

    Difficulty difficulty;
    Board board = new Board();

    boolean isGameStarted = false;
    boolean isGamePaused = false;
    boolean isGameOver = false;
    boolean isGameWon = false;

    public GameControl() {
        difficulty = Difficulty.MEDIUM;
        this.board = new Board(difficulty.getNumObstacles(), difficulty.getNumGears(), difficulty.getGameDuration());
    }

    public GameControl(Board board) {
        difficulty = Difficulty.MEDIUM;
        this.board = board;
        this.board.updateComponents(difficulty.getNumObstacles(), difficulty.getNumGears());
    }

    public void startGame() {
        if (isGameOver) {
            // If game was over, we need to do a full restart
            restartGame();
            isGameStarted = true;
            return;
        }
        
        if (!isGameStarted) {
            // Starting a new game
            isGameStarted = true;
            isGamePaused = false;
            this.board.getStatusLine().displayGamePlayingText();
            this.board.getTimer().start(); // Explicitly start the timer
            System.out.println("Game started");
        } else if (isGamePaused) {
            // Resuming a paused game
            isGamePaused = false;
            this.board.getStatusLine().displayGamePlayingText();
            this.board.getTimer().resume(); // Resume rather than start
            System.out.println("Game resumed");
        }
    }

    public void pauseGame() {
        // Can only pause a game that's started and not already over
        if (isGameStarted && !isGameOver) {
            if (!isGamePaused) {
                // Pause the game
                isGamePaused = true;
                this.board.getStatusLine().displayPauseText();
                this.board.getTimer().pause();
                System.out.println("Game paused");
            } else {
                // Resume the game
                isGamePaused = false;
                this.board.getStatusLine().displayGamePlayingText();
                this.board.getTimer().resume();
                System.out.println("Game resumed");
            }
        }
    }

    public void restartGame() {
        // Stop any playing sounds
        if (Game.audioPlayer().getStatus() == base.AudioPlayer.MusicStatus.PLAYING) {
            Game.audioPlayer().stop();
        }
        
        // Reset game state
        isGameStarted = false;
        isGamePaused = false;
        isGameOver = false;
        isGameWon = false;
        
        // Reset the board with current difficulty settings
        this.updateBoard();
        
        // Make sure to properly reset the timer
        this.board.getTimer().reset();
        
        // Update status line
        this.board.getStatusLine().displayStartText();
        
        // Refresh the canvas
        Game.UI().canvas().repaint();
        
        System.out.println("Game restarted");
    }
    
    public void gameStep() {

        updateGameStatus();
            if (isGameStarted && !isGamePaused && !isGameOver) {
            // Only refresh the timer if the game is actually running
            this.board.getTimer().refresh();
            Game.UI().canvas().repaint();
        }
    }

    public void updateGameStatus() {
        if (isGameStarted && !isGamePaused && !isGameOver) {
            if (this.board.checkAllGearsAtHome()) {
                playerWon();
            } else if (this.board.getTimer().getTimeRemaining() <= 0) {
                playerLost();
            }
        }
    }

    public void playerWon() {
        isGameOver = true;
        isGameWon = true;
        this.board.getStatusLine().displayPlayerWonText();
        
        // Play win sound
        Game.audioPlayer().play("Applause.wav.wav", 1);
    }

    public void playerLost() {
        isGameOver = true;
        this.board.getStatusLine().displayGameOverText();
        
        // Play game over sound
        Game.audioPlayer().play("mixkit-arcade-retro-game-over-213.wav", 1);
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.updateBoard();
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public void moveRobot(Robot.Direction direction) {
        if (isGameStarted && !isGamePaused && !isGameOver) {
            board.getRobot().move(direction);
        }
    }

    public void orderRobotDrill() {
        if (isGameStarted && !isGamePaused && !isGameOver) {
            board.getRobot().drill();
        }
    }

    public void orderRobotPickup() {
        if (isGameStarted && !isGamePaused && !isGameOver) {
            board.getRobot().pickupGears();
        }
    }

    public void orderRobotDrop() {
        if (isGameStarted && !isGamePaused && !isGameOver) {
            board.getRobot().dropGear();
        }
    }

    public void useAestheticObstacles() {
        this.board.setUseAestheticObstacles(true);
        this.updateBoard();
    }

    public void usePolyObstacles() {
        this.board.setUseAestheticObstacles(false);
        this.updateBoard();
    }

    public void updateBoard() {
        this.board.updateComponents(difficulty.getNumObstacles(), difficulty.getNumGears());
        this.board.getTimer().setGameDuration(difficulty.getGameDuration());
    }

    /**
     * Checks if the game is currently paused
     * @return true if game is paused, false otherwise
     */
    public boolean isGamePaused() {
        return isGamePaused;
    }

    /**
     * Checks if the game is currently running
     * @return true if game is running, false otherwise
     */
    public boolean isGameRunning() {
        return isGameStarted && !isGamePaused && !isGameOver;
    }
}