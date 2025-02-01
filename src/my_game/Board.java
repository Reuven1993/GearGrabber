package my_game;

import base.Game;
import base.GameCanvas;
import base.IntersectionAlgorithm;
import ui_elements.ScreenPoint;

public class Board {

    int[] boardXLimits = { 0, 500 };
    int[] boardYLimits = { 50, 500 };
    private Obstacle[] obstacles;
    private Gear[] gears;
    private Home home;
    private Robot robot;
    private Timer timer;

    public Board() {

        int numObstacles = 2;
        int numGears = 2;

        this.obstacles = new Obstacle[numObstacles];
        this.gears = new Gear[numGears];
        this.home = new Home();
        this.robot = new Robot();
        this.timer = new Timer();

        for (int i = 0; i < numObstacles; i++) {
            this.obstacles[i] = new Obstacle();
        }

        for (int i = 0; i < numGears; i++) {
            this.gears[i] = new Gear();
        }

    }

    public Board(int numObstacles, int numGears) {

        this.obstacles = new Obstacle[numObstacles];
        this.gears = new Gear[numGears];
        this.home = new Home();
        this.robot = new Robot();
        this.timer = new Timer();

        for (int i = 0; i < numObstacles; i++) {
            this.obstacles[i] = new Obstacle();
        }

        for (int i = 0; i < numGears; i++) {
            this.gears[i] = new Gear();
        }

    }

    public void initBoard() {

        ScreenPoint robotLocation = new ScreenPoint(100, 100);
        this.robot.setLocation(robotLocation);

        ScreenPoint homeLocation = new ScreenPoint(100, 100);
        this.home.setLocation(homeLocation);

        ScreenPoint timerLocation = new ScreenPoint(50, 50);
        this.timer.setLocation(timerLocation);

        ScreenPoint[] obstaclesLocations = new ScreenPoint[this.obstacles.length];
        int[] obstaclesRadii = new int[this.obstacles.length];
        for (int i = 0; i < this.obstacles.length; i++) {

            obstaclesRadii[i] = 50;
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

        this.home.addToCanvas(homeZOrder);
        this.robot.addToCanvas(robotZOrder);
        this.timer.addToCanvas(timerZOrder);

        // Decide if to use this sintax or a more classic one
        for (Obstacle obstacle : this.obstacles) {
            obstacle.addToCanvas(obstacleZOrder);
        }

        for (Gear gear : this.gears) {
            gear.addToCanvas(gearZOrder);
        }

    }

    public void updateBoard() {

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

}
