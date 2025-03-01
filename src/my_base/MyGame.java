package my_base;

import java.awt.Color;

import base.Game;
import base.GameCanvas;
import base.GameContent;
import base.GameDashboard;
import my_game.Board;

import my_ui_elements.MusicButton;
import my_ui_elements.StartGameButton;
import my_ui_elements.PauseGameButton;
import my_ui_elements.RestartGameButton;
import my_ui_elements.DifficultyCombo;
import my_ui_elements.AesteticObstacleCB;
import my_ui_elements.CustomEndButton;
import my_ui_elements.HighScoreButton;

public class MyGame extends Game {

	private MyContent content;

	@Override
	protected void initCanvas() {
		GameCanvas canvas = gameUI.canvas();
		canvas.setMouseHandler(Game.MouseHandler());

		canvas.setBackground(Color.WHITE);
		// canvas.setBackgroundImage("resources/background1.jpg");

		Board board = content.board();
		board.addToCanvas();
	}

	@Override
	protected void initDashboard() {
		// IMPORTANT: Don't call super.initDashboard() to avoid duplicate END button
		// Instead, we'll add our own END button in this method
		
		GameDashboard dashboard = gameUI.dashboard();
		dashboard.setBackground(Color.BLACK);

		DifficultyCombo difficultyCombo = new DifficultyCombo(150, 20);
		dashboard.addUIElement(difficultyCombo);

		AesteticObstacleCB aestheticObstacleCB = new AesteticObstacleCB("aestheticObstacleCB", "Aesthetic Obstacles", 150, 60, 160, 30, true);
		dashboard.addUIElement(aestheticObstacleCB);

		dashboard.addUIElement(new StartGameButton("startButton", "Start", 0, 0, difficultyCombo, aestheticObstacleCB));
		dashboard.addUIElement(new PauseGameButton("pauseButton", "Pause", 0, 50));
		dashboard.addUIElement(new RestartGameButton("restartButton", "Restart", 0, 100, difficultyCombo, aestheticObstacleCB));
		
		// Add a single END button
		dashboard.addUIElement(new CustomEndButton("btnEND", "EXIT", 100, 40, 850, 40));

		dashboard.addUIElement(new MusicButton("musicButton", "Play", 700, 40));
		dashboard.addUIElement(new HighScoreButton("highScoreButton", "High Scores", 600, 40));
	}

	@Override
	public void setGameContent(GameContent content) {
		// Call the Game superclass to set its content
		super.setGameContent(content);
		// point to the content with a variable of type MyContent so we have access to
		// all
		// our game specific data
		this.content = (MyContent) content;
	}

	public MyContent getContent() {
		return this.content;
	}

	public static void main(String[] args) {
		MyGame game = new MyGame();
		game.setGameContent(new MyContent());
		MyPeriodicLoop periodicLoop = new MyPeriodicLoop();
		periodicLoop.setContent(game.getContent());
		game.setPeriodicLoop(periodicLoop);
		game.setMouseHandler(new MyMouseHandler());
		game.setKeyboardListener(new MyKeyboardListener());
		game.initGame();
	}
}