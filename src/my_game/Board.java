package my_game;

import base.Game;
import base.GameCanvas;
import base.IntersectionAlgorithm;
import ui_elements.ScreenPoint;

public class Board {

    int[] boardXLimits = { 50, 500 };
    int[] boardYLimits = { 50, 500 };
    private boolean useAestheticObstacles = true;
    private Obstacle[] obstacles;
    private Gear[] gears;
    private Home home;
    private Robot robot;
    private Timer timer;
    private StatusLine statusLine;

    public Board() {

        int numObstacles = 2;
        int numGears = 2;
        long gameDuration = 30000;

        this.home = new Home();
        this.robot = new Robot();
        this.timer = new Timer(gameDuration);
        this.statusLine = new StatusLine();

        createNewObstacles(numObstacles);
        createNewGears(numGears);

        this.initBoard();

    }

    public Board(int numObstacles, int numGears) {

        long gameDuration = 30000;
        
        this.home = new Home();
        this.robot = new Robot();
        this.timer = new Timer(gameDuration);

        createNewObstacles(numObstacles);
        createNewGears(numGears);

        initBoard();
    }

    public Board(int numObstacles, int numGears, long gameDuration) {

        this.home = new Home();
        this.robot = new Robot();
        this.timer = new Timer(gameDuration);

        createNewObstacles(numObstacles);
        createNewGears(numGears);

        initBoard();
    }

    public void updateComponents(int numObstacles, int numGears) {

        createNewObstacles(numObstacles);
        createNewGears(numGears);

        this.initBoard();

        if (Game.UI() != null) {
            this.updateBoardOnCanvas();
        }

    }

    public void createNewObstacles(int numObstacles) {

        this.obstacles = new Obstacle[numObstacles];

        for (int i = 0; i < numObstacles; i++) {
            this.obstacles[i] = new Obstacle("obstacle" + i , useAestheticObstacles);
        }

    }

    public void createNewGears(int numGears) {

        this.gears = new Gear[numGears];

        for (int i = 0; i < numGears; i++) {
            this.gears[i] = new Gear("gear" + i);
        }

    }

    public void initBoard() {

        ScreenPoint robotLocation = new ScreenPoint(boardXLimits[0], boardYLimits[0]);
        this.robot.setLocation(robotLocation);

        ScreenPoint homeLocation = new ScreenPoint(boardXLimits[0], boardYLimits[0]);
        this.home.setLocation(homeLocation);

        ScreenPoint timerLocation = new ScreenPoint(50, 50);
        this.timer.setLocation(timerLocation);

        ScreenPoint[] obstaclesLocations = new ScreenPoint[this.obstacles.length];
        for (int i = 0; i < this.obstacles.length; i++) {

            obstaclesLocations[i] = randomLocation(boardXLimits[0], boardYLimits[0], boardXLimits[1], boardYLimits[1]);
            this.obstacles[i].setLocation(obstaclesLocations[i]);

        }

        ScreenPoint[] gearsLocations = new ScreenPoint[this.gears.length];
        for (int i = 0; i < this.gears.length; i++) {

            gearsLocations[i] = randomLocation(boardXLimits[0], boardYLimits[0], boardXLimits[1], boardYLimits[1]);
            this.gears[i].setLocation(gearsLocations[i]);

        }

    }

    public ScreenPoint randomLocation(int minX, int minY, int maxX, int maxY) {

        int x = (int) (Math.random() * (maxX - minX + 1) + minX);
        int y = (int) (Math.random() * (maxY - minY + 1) + minY);

        return new ScreenPoint(x, y);

    }

    public void addToCanvas() {

        int homeZOrder = 1;
        int robotZOrder = 2;
        int timerZOrder = 3;
        int obstacleZOrder = 4;
        int gearZOrder = 5;
        int statusLineZOrder = 10;

        this.home.addToCanvas(homeZOrder);
        this.robot.addToCanvas(robotZOrder);
        this.timer.addToCanvas(timerZOrder);
        this.statusLine.addToCanvas(statusLineZOrder);

        // Decide if to use this sintax or a more classic one
        for (Obstacle obstacle : this.obstacles) {
            obstacle.addToCanvas(obstacleZOrder);
        }

        for (Gear gear : this.gears) {
            gear.addToCanvas(gearZOrder);
        }

    }

    public void updateBoardOnCanvas() {

        GameCanvas canvas = Game.UI().canvas();
        canvas.deleteAllShapes();

        this.addToCanvas();

    }

    public boolean isLegalRobotLocation() {

        for (Obstacle obstacle : this.obstacles) {
            if (IntersectionAlgorithm.areIntersecting(this.robot, obstacle)) {
                return false;
            }
        }
        return true;

    }

    public Gear[] gearsNearRobot() {

        Gear[] gearsNearRobot = new Gear[this.gears.length];
        int count = 0;

        for (Gear gear : this.gears) {
            if (IntersectionAlgorithm.areIntersecting(this.robot, gear)) {
                gearsNearRobot[count] = gear;
                count++;
            }
        }

        return gearsNearRobot;

    }

    public Obstacle[] getObstacles() {
        return obstacles;
    }

    public Gear[] getGears() {
        return gears;
    }

    public Home getHome() {
        return home;
    }

    public Robot getRobot() {
        return robot;
    }

    public Timer getTimer() {
        return timer;
    }

    public boolean checkAllGearsAtHome() {
        
        for (Gear gear : this.gears) {
            if (!IntersectionAlgorithm.areIntersecting(this.home, gear)) {
                return false;
            }
        }

        return true;
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public int[] getBoardXLimits() {
        return boardXLimits;
    }

    public void setBoardXLimits(int[] boardXLimits) {
        this.boardXLimits = boardXLimits;
    }

    public int[] getBoardYLimits() {
        return boardYLimits;
    }

    public void setBoardYLimits(int[] boardYLimits) {
        this.boardYLimits = boardYLimits;
    }

    public boolean isUseAestheticObstacles() {
        return useAestheticObstacles;
    }

    public void setUseAestheticObstacles(boolean useAestheticObstacles) {
        this.useAestheticObstacles = useAestheticObstacles;

    }
    
    
}
