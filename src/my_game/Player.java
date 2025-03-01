package my_game;

/**
 * Represents a player in the game.
 */
public class Player {
    private String name;
    private int currentScore;
    private int gearsCollected;
    
    public Player(String name) {
        this.name = name;
        this.currentScore = 0;
        this.gearsCollected = 0;
    }
    
    public String getName() {
        return name;
    }
    
    public int getCurrentScore() {
        return currentScore;
    }
    
    public void setCurrentScore(int score) {
        this.currentScore = score;
    }
    
    public int getGearsCollected() {
        return gearsCollected;
    }
    
    public void incrementGearsCollected() {
        this.gearsCollected++;
    }
}