//James O'Meara
//13715519

package StateManager;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

public abstract class GameState {
	//abstract class to create new states for the game
	//instance of gsm for the classes that extend this
	protected GameStateManager gsm;
	//dimensions for grid
	protected static final int gridSize = 40;
	protected static boolean grid[][] = new boolean[gridSize][gridSize];
	protected static int box = 800 / gridSize;		 //box size
	
	//each game state must have these methods
	public abstract void update();
	public abstract void draw(Graphics g);
	public abstract void mousePressed(MouseEvent m);
	
}
