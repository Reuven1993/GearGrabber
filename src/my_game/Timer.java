package my_game;

import java.awt.Color;

import base.Game;
import base.GameCanvas;
import base.PeriodicLoop;
import shapes.Text;
import ui_elements.ScreenPoint;

public class Timer {

    private volatile long startGameTime;
    private volatile long gameDuration; // in mili-seconds
    private volatile long endGameTime;
    private volatile long timeRemaining;
    private volatile long pausedAt; // Time when timer was paused
    private volatile boolean isRunning; // Is timer currently running?
    
    private final String guid = "timer";
    private final Object timerLock = new Object(); // For thread synchronization

    private ScreenPoint location = new ScreenPoint(50, 50);

    public Timer() {
        synchronized(timerLock) {
            gameDuration = 1000;
            timeRemaining = gameDuration;
            startGameTime = PeriodicLoop.elapsedTime();
            endGameTime = startGameTime + gameDuration;
            isRunning = false;
        }
    }

    public Timer(long gameTime) {
        synchronized(timerLock) {
            this.gameDuration = gameTime;
            timeRemaining = gameTime;
            startGameTime = PeriodicLoop.elapsedTime();
            endGameTime = startGameTime + gameTime;
            isRunning = false;
        }
    }

    public void addToCanvas() {
        GameCanvas canvas = Game.UI().canvas();

        Text timeText = new Text(this.guid, this.toString(), this.getLocation().x, this.getLocation().y);
        timeText.setColor(Color.BLACK);
        timeText.setFontSize(40);
        timeText.setzOrder(3);
        canvas.addShape(timeText);
    }

    public void addToCanvas(int zOrder) {
        GameCanvas canvas = Game.UI().canvas();

        Text timeText = new Text(this.guid, this.toString(), this.location.x, this.location.y);
        timeText.setColor(Color.BLACK);
        timeText.setzOrder(zOrder);
        canvas.addShape(timeText);
    }

    // Reset and start timer
    public void start() {
        synchronized(timerLock) {
            startGameTime = PeriodicLoop.elapsedTime();
            endGameTime = startGameTime + gameDuration;
            timeRemaining = gameDuration;
            isRunning = true;
            pausedAt = 0;
            
            // Update the display
            updateText();
            
            System.out.println("Timer started: " + isRunning + ", duration: " + gameDuration + "ms");
        }
    }

    // Pause the timer
    public void pause() {
        synchronized(timerLock) {
            if (isRunning) {
                pausedAt = PeriodicLoop.elapsedTime();
                isRunning = false;
                System.out.println("Timer paused at: " + pausedAt);
                updateText();
            }
        }
    }

    // Resume the timer
    public void resume() {
        synchronized(timerLock) {
            if (!isRunning && pausedAt > 0) {
                // Calculate how long the timer was paused
                long pauseDuration = PeriodicLoop.elapsedTime() - pausedAt;
                // Adjust the end time by adding the pause duration
                endGameTime += pauseDuration;
                isRunning = true;
                pausedAt = 0;
                System.out.println("Timer resumed, end time adjusted by: " + pauseDuration + "ms");
                updateText();
            }
        }
    }

    // Completely reset the timer
    public void reset() {
        synchronized(timerLock) {
            isRunning = false;
            pausedAt = 0;
            timeRemaining = gameDuration;
            startGameTime = PeriodicLoop.elapsedTime();
            endGameTime = startGameTime + gameDuration;
            System.out.println("Timer reset, timeRemaining: " + timeRemaining + "ms");
            updateText();
        }
    }

    public void refresh() {
        synchronized(timerLock) {
            if (!isRunning) {
                // If not running, don't update the time but still update display
                updateText();
                return;
            }

            GameCanvas canvas = Game.UI().canvas();
            Text timerText = (Text) canvas.getShape(this.getGuid());
            
            if (timerText == null) {
                return;
            }

            long currentTime = PeriodicLoop.elapsedTime();
            if (currentTime < endGameTime) {
                timeRemaining = endGameTime - currentTime;
            } else {
                timeRemaining = 0;
                timerText.setColor(Color.RED);
                // Auto-stop the timer when it reaches 0
                isRunning = false;
            }

            timerText.setText(this.toString());
        }
    }

    public void updateText() {
        try {
            GameCanvas canvas = Game.UI().canvas();
            if (canvas == null) return;
            
            Text timerText = (Text) canvas.getShape(this.getGuid());
            if (timerText != null) {
                timerText.setText(this.toString());
                canvas.repaint();
            }
        } catch (Exception e) {
            System.err.println("Error updating timer text: " + e.getMessage());
        }
    }

    public ScreenPoint getLocation() {
        return location;
    }

    public void setLocation(ScreenPoint location) {
        this.location = location;
    }

    public long getGameDuration() {
        synchronized(timerLock) {
            return gameDuration;
        }
    }

    public void setGameDuration(long gameDuration) {
        synchronized(timerLock) {
            this.gameDuration = gameDuration;
            this.setTimeRemaining(gameDuration);
        }
    }

    public long getTimeRemaining() {
        synchronized(timerLock) {
            return timeRemaining;
        }
    }

    public void setTimeRemaining(long timeRemaining) {
        synchronized(timerLock) {
            this.timeRemaining = timeRemaining;
            this.updateText();
        }
    }

    public String getGuid() {
        return guid;
    }
    
    public boolean isRunning() {
        synchronized(timerLock) {
            return isRunning;
        }
    }

    @Override
    public String toString() {
        // Format time as mm:ss
        long seconds = timeRemaining / 1000;
        return "[TimeRemaining = " + (seconds / 60) + ":" + String.format("%02d", seconds % 60) + "]";
    }
}
