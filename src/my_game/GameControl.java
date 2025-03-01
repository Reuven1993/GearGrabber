package my_game;

import base.AudioPlayer;
import base.Game;
import my_ui_elements.PauseGameButton;
import my_ui_elements.HighScoreButton;
import ui_elements.GameButton;

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

    private Difficulty difficulty;
    private Board board;
    private ScoreBoard scoreBoard;
    private Player player;

    // Game state variables - using volatile for thread safety
    private volatile boolean isGameStarted = false;
    private volatile boolean isGamePaused = false;
    private volatile boolean isGameOver = false;
    private volatile boolean isGameWon = false;

    public synchronized boolean isGamePaused() {
        return isGamePaused;
    }

    public synchronized boolean isGameOver() {
        return isGameOver;
    }

    public synchronized boolean isGameStarted() {
        return isGameStarted;
    }

    public synchronized boolean isGameWon() {
        return isGameWon;
    }

    public synchronized boolean isGameRunning() {
        return isGameStarted && !isGamePaused && !isGameOver;
    }

    public synchronized void resetGame() {
        isGameStarted = false;
        isGamePaused = false;
        isGameOver = false;
        isGameWon = false;
        board.getStatusLine().displayStartText();
        
        // Hide high scores if they're displayed
        hideHighScores();
        
        // Update button states
        updateUIElements();
    }

    public GameControl() {
        difficulty = Difficulty.MEDIUM;
        this.board = new Board(difficulty.getNumObstacles(), difficulty.getNumGears(), difficulty.getGameDuration());
        this.scoreBoard = new ScoreBoard();
        this.player = new Player("Player1");
    }

    public GameControl(Board board) {
        difficulty = Difficulty.MEDIUM;
        this.board = board;
        this.board.updateComponents(difficulty.getNumObstacles(), difficulty.getNumGears());
        this.scoreBoard = new ScoreBoard();
        this.player = new Player("Player1");
    }

    public synchronized void startGame() {
        if (isGameOver) {
            // If game was over, we need to do a full restart
            restartGame();
            isGameStarted = true;
            updateUIElements();
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
        
        // Hide high scores if they're displayed
        hideHighScores();
        
        // Update button states
        updateUIElements();
    }

    public synchronized void pauseGame() {
        // Can only pause a game that's started and not already over
        if (isGameStarted && !isGameOver) {
            // Toggle pause state
            isGamePaused = !isGamePaused;
            
            if (isGamePaused) {
                // Pause the game
                this.board.getStatusLine().displayPauseText();
                this.board.getTimer().pause();
                System.out.println("Game paused");
            } else {
                // Resume the game
                this.board.getStatusLine().displayGamePlayingText();
                this.board.getTimer().resume();
                System.out.println("Game resumed");
            }
            
            // Update button states
            updateUIElements();
        }
    }

    public synchronized void restartGame() {
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
        
        // Hide high scores if they're displayed
        hideHighScores();
        
        // Update button states
        updateUIElements();
        
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

    private synchronized void updateGameStatus() {
        if (isGameStarted && !isGamePaused && !isGameOver) {
            if (this.board.checkAllGearsAtHome()) {
                playerWon();
            } else if (this.board.getTimer().getTimeRemaining() <= 0) {
                playerLost();
            }
        }
    }

    private synchronized void playerWon() {
        isGameOver = true;
        isGameWon = true;
        this.board.getStatusLine().displayPlayerWonText();
        
        // Update score board
        calculateFinalScore();

        // Play win sound
        Game.audioPlayer().play("resources/audio/Applause.wav.wav", 1);
        
        // Update button states
        updateUIElements();
    }

    private synchronized void playerLost() {
        isGameOver = true;
        this.board.getStatusLine().displayGameOverText();
        
        // Stop any currently playing music
        if (Game.audioPlayer().getStatus() == AudioPlayer.MusicStatus.PLAYING) {
            Game.audioPlayer().stop();
        }
        
        // Play the losing sound effect
        Game.audioPlayer().play("resources/audio/loosing_sound.wav.wav", 1);
        
        // Update button states
        updateUIElements();
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
            // Just call the method without trying to get a return value
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
     * Called when the robot successfully picks up a gear.
     * Updates the player's gear collection count.
     */
    public void notifyGearCollected() {
        if (player != null) {
            player.incrementGearsCollected();
        }
    }
    
    public void setPlayerName(String name) {
        this.player = new Player(name);
    }
    
    private void calculateFinalScore() {
        if (!isGameOver) return;
        
        long timeRemaining = board.getTimer().getTimeRemaining() / 1000;
        int gearsCollected = player.getGearsCollected();
        
        int finalScore = (int)(timeRemaining * 10) + (gearsCollected * 100);
        
        switch (difficulty) {
            case EASY:
                finalScore = (int)(finalScore * 0.8);
                break;
            case MEDIUM:
                break;
            case HARD:
                finalScore = (int)(finalScore * 1.5);
                break;
        }
        
        player.setCurrentScore(finalScore);
        
        System.out.println("Saving score for player: " + player.getName() + ", score: " + finalScore);
        
        scoreBoard.addScore(
            player.getName(),
            player.getCurrentScore(),
            difficulty.toString(),
            player.getGearsCollected(),
            board.getTimer().getTimeRemaining()
        );
    }
    
    /**
     * Hide high scores if they're displayed
     */
    private void hideHighScores() {
        try {
            // Find and hide high scores
            HighScoreButton highScoreButton = (HighScoreButton) Game.UI().dashboard().getUIElement("highScoreButton");
            if (highScoreButton != null) {
                highScoreButton.clearHighScores();
            }
        } catch (Exception e) {
            System.err.println("Error hiding high scores: " + e.getMessage());
        }
    }
    
    /**
     * Update all UI elements to reflect current game state
     */
    private void updateUIElements() {
        try {
            // Update pause button text
            PauseGameButton pauseButton = (PauseGameButton) Game.UI().dashboard().getUIElement("pauseButton");
            if (pauseButton != null) {
                pauseButton.updateButtonText();
            }
            
            // Refresh dashboard
            Game.UI().dashboard().updateUI();
        } catch (Exception e) {
            System.err.println("Error updating UI elements: " + e.getMessage());
        }
    }
}

