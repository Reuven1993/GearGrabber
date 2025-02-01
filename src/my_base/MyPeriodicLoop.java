package my_base;

import base.Game;
import base.GameCanvas;
import base.PeriodicLoop;

public class MyPeriodicLoop extends PeriodicLoop {

	private MyContent content;

	public void setContent(MyContent content) {
		this.content = content;
	}

	@Override
	public void execute() {
		// Let the super class do its work first
		super.execute();

		GameCanvas canvas = Game.UI().canvas();

		content.board().getTimer().refresh();

		canvas.repaint();

	}

}
