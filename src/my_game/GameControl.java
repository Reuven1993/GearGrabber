package my_game;

public class GameControl {

    public enum Difficulty {
        EASY(1, 1, 0), 
        MEDIUM(2, 3, 50), 
        HARD(4, 5, 75) ;
        
        private final int numGears;
        private final int numObstacles;
        private final int percentageCoveredGears;

        private Difficulty(int numGears, int numObstacles,int percentageCoveredGears) {
            this.numGears = numGears;
            this.numObstacles = numObstacles;
            this.percentageCoveredGears = percentageCoveredGears;
        }

        public int getNumGears() {
            return numGears;
        }

        public int getNumObstacles() {
            return numObstacles;
        }

        public int getPercentageCoveredGears() {
            return percentageCoveredGears;
        }
        

    }

    Difficulty difficulty;
    Board board = new Board();

    boolean isGameStarted = true;
    boolean isGamePaused = false;
    boolean isGameOver = false;

    public GameControl() {
        difficulty = Difficulty.MEDIUM;
        this.board = new Board();
    }

    public GameControl(Board board) {
        difficulty = Difficulty.MEDIUM;
        this.board = board;
    }
   
    public void setDifficulty(Difficulty difficulty) {

        this.difficulty = difficulty;
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

        if (isGameStarted && !isGamePaused && !isGameOver){
            board.getRobot().move(direction);
        }

    }

    public void orderRobotDrill() {
        if (isGameStarted && !isGamePaused && !isGameOver){
            board.getRobot().drill();
        }
    }

}
