package my_base;

import my_game.Robot;
import java.awt.event.KeyEvent;

import base.Game;
import base.KeyboardListener;

public class MyKeyboardListener extends KeyboardListener {

	private MyContent myContent;

	public MyKeyboardListener() {
		super();
		myContent = (MyContent) this.content;

	}

	@Override
	public void directionalKeyPressed(Direction direction) {

		switch (direction) {
			case UP:
				myContent.gameControl().moveRobot(Robot.Direction.UP);
				break;
			case DOWN:
				myContent.gameControl().moveRobot(Robot.Direction.DOWN);
				break;
			case LEFT:
				myContent.gameControl().moveRobot(Robot.Direction.LEFT);
				break;
			case RIGHT:
				myContent.gameControl().moveRobot(Robot.Direction.RIGHT);
				break;
		}

	}

	@Override
	public void characterTyped(char c) {
		switch (c) {
			case 'd':
				myContent.gameControl().orderRobotDrill();
				break;
		}

		System.out.println("key character = '" + c + "'" + " pressed.");
	}

	@Override
	public void enterKeyPressed() {
		System.out.println("enter key pressed.");
	}

	@Override
	public void backSpaceKeyPressed() {
		System.out.println("backSpace key pressed.");
	}

	@Override
	public void spaceKeyPressed() {
		System.out.println("space key pressed.");
	}

	public void otherKeyPressed(KeyEvent e) {
		System.out.println("other key pressed. type:" + e);
	}
}
