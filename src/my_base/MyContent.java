package my_base;
import base.GameContent;
import my_game.Board;
import my_game.GameControl;
import my_game.Robot;

public class MyContent extends GameContent {

	private Board board;
	private GameControl gameControl;

	@Override
	public void initContent() {

		board = new Board();
		gameControl = new GameControl(this.board);

	}

	public void moveRobot(Robot.Direction direction) {
		
	}

	public GameControl gameControl() {
		return gameControl;
	}

	public Board board() {
		return board;
	}	


}
