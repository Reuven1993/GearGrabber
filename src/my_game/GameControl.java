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

        if (!isGameOver) {
            this.board.getStatusLine().displayGamePlayingText();
            if (!isGameStarted) {
                if (!isGamePaused) {
                    isGameStarted = true;
                    this.board.getTimer().start();
                } else {
                    isGamePaused = false;
                }
            } 
        }
    }

    public void gameStep() {

        updateGameStatus();

        if (isGameStarted && !isGamePaused && !isGameOver) {

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
    }

    public void playerLost() {
        isGameOver = true;
        this.board.getStatusLine().displayGameOverText();
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
}
